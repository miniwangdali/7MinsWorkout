package io.github.miniwangdali.a7minsworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import io.github.miniwangdali.a7minsworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var bindings: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(bindings?.root)

        bindings?.finishButton?.setOnClickListener {
            finish()
        }

        val historyDAO = (application as WorkoutApp).db.historyDAO()
        addWorkoutHistoryItem(historyDAO)
    }

    private fun addWorkoutHistoryItem(historyDAO: HistoryDAO) {
        val date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd MMM yyy HH:mm:ss", Locale.getDefault())

        lifecycleScope.launch {
            historyDAO.insert(HistoryEntity(date = simpleDateFormat.format(date)))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bindings != null) {
            bindings = null
        }
    }
}