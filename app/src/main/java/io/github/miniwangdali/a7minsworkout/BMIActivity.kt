package io.github.miniwangdali.a7minsworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.github.miniwangdali.a7minsworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiactivityBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calcuate BMI"
        }
        binding?.bmiToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.calculateButton?.setOnClickListener {
            if (validateInput()) {
                if (currentVisibleView === US_UNITS_VIEW) {
                    val feet = binding?.usFeetEditText?.text.toString().toFloat()
                    val inch = binding?.usInchEditText?.text.toString().toFloat()
                    val weight = binding?.weightEditText?.text.toString().toFloat()
                    val height = inch + feet * 12f
                    val bmi = 703 * weight / (height * height)
                    showBIMResult(bmi)
                } else {
                    val height = binding?.heightEditText?.text.toString().toFloat() / 100
                    val weight = binding?.weightEditText?.text.toString().toFloat()
                    val bmi = weight / (height * height)
                    showBIMResult(bmi)
                }
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding?.unitsRadioGroup?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.usUnitRadioButton) {
                showUSUnitsView()
            } else {
                showMetricUnitsView()
            }
        }

        showMetricUnitsView()
    }

    private fun showMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.heightTextInput?.visibility = View.VISIBLE
        binding?.usUnitLinearLayout?.visibility = View.INVISIBLE
        binding?.weightTextInput?.hint = "Weight (kg)"
        binding?.weightEditText?.text?.clear()
        binding?.heightEditText?.text?.clear()
        binding?.bmiResultLinearLayout?.visibility = View.INVISIBLE
    }

    private fun showUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.heightTextInput?.visibility = View.INVISIBLE
        binding?.usUnitLinearLayout?.visibility = View.VISIBLE
        binding?.weightTextInput?.hint = "Weight (pd)"
        binding?.usInchEditText?.text?.clear()
        binding?.usFeetEditText?.text?.clear()
        binding?.bmiResultLinearLayout?.visibility = View.INVISIBLE
    }

    private fun showBIMResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        binding?.bmiValueTextView?.text =
            BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.bmiTypeTextView?.text = bmiLabel
        binding?.bmiDescriptionTextView?.text = bmiDescription
        binding?.bmiResultLinearLayout?.visibility = View.VISIBLE
    }

    private fun validateInput(): Boolean {
        var isValid = true
        if (binding?.weightEditText?.text.toString().isEmpty()
            || (currentVisibleView == METRIC_UNITS_VIEW && binding?.heightEditText?.text.toString()
                .isEmpty())
            || (currentVisibleView == US_UNITS_VIEW
                    && (binding?.usFeetEditText?.text.toString().isEmpty()
                    || binding?.usInchEditText?.text.toString().isEmpty()))
        ) {
            isValid = false
        }
        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}