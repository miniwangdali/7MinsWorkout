package io.github.miniwangdali.a7minsworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import io.github.miniwangdali.a7minsworkout.databinding.ActivityExerciseBinding
import io.github.miniwangdali.a7minsworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private val TOTAL_REST_TIME = 1
    private val TOTAL_EXERCISE_TIME = 2
    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var countDownProgress = 0

    private val exerciseList = Constants.defaultExerciseList()
    private var currentExerciseIndex = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)
        val soundURI =
            Uri.parse("android.resource://io.github.miniwangdali.a7minsworkout/" + R.raw.app_src_main_res_raw_press_start)
        player = MediaPlayer.create(applicationContext, soundURI)
        player?.isLooping = false

        setSupportActionBar(binding?.toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener {
            onCustomBackPressed()
        }
        binding?.progressBar?.max = TOTAL_REST_TIME
        setupCountDownTimer()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        onCustomBackPressed()
    }

    private fun onCustomBackPressed () {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.yesButton.setOnClickListener {
            customDialog.dismiss()
            this@ExerciseActivity.finish()
        }
        dialogBinding.noButton.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
//        binding?.exerciseStatusRecyclerView?.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList)
        binding?.exerciseStatusRecyclerView?.adapter = exerciseStatusAdapter
    }

    private fun resetCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        countDownProgress = 0
    }

    private fun setupCountDownTimer(type: CountDownType = CountDownType.REST) {

        try {
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        resetCountDownTimer()
        binding?.exerciseTextView?.text = if (type == CountDownType.EXERCISE) {
            exerciseList[currentExerciseIndex].name
        } else {
            "${getString(R.string.get_ready_for)}\n${exerciseList[currentExerciseIndex + 1].name}"
        }
        speakOut(binding?.exerciseTextView?.text.toString())
        binding?.exerciseImageView?.visibility = if (type == CountDownType.EXERCISE) {
            View.VISIBLE
        } else {
            View.GONE
        }
        if (type == CountDownType.EXERCISE) {
            binding?.exerciseImageView?.setImageResource(exerciseList[currentExerciseIndex].image)
        }
        setCountDownProgressBar(type)
    }

    private fun setCountDownProgressBar(type: CountDownType) {
        val total = if (type == CountDownType.EXERCISE) {
            TOTAL_EXERCISE_TIME
        } else {
            TOTAL_REST_TIME
        }
        binding?.progressBar?.max = total
        binding?.progressBar?.progress = countDownProgress
        countDownTimer = object : CountDownTimer((total * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                countDownProgress++
                val progress = total - countDownProgress
                binding?.progressBar?.progress = progress
                binding?.timerTextView?.text = progress.toString()
            }

            override fun onFinish() {
                if (currentExerciseIndex >= exerciseList.size - 1) {
                    Toast.makeText(this@ExerciseActivity, "Congratulations!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val next = if (type == CountDownType.EXERCISE) {
                        CountDownType.REST
                    } else {
                        CountDownType.EXERCISE
                    }
                    if (type == CountDownType.REST) {
                        currentExerciseIndex++
                        exerciseList[currentExerciseIndex].isSelected = true
                    } else {
                        exerciseList[currentExerciseIndex].isSelected = false
                        exerciseList[currentExerciseIndex].isCompleted = true
                    }
                    exerciseStatusAdapter?.notifyDataSetChanged()
                    setupCountDownTimer(next)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetCountDownTimer()
        binding = null
        tts?.stop()
        tts?.shutdown()
        player?.stop()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}