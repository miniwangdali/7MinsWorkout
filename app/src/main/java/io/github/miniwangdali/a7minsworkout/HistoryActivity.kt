package io.github.miniwangdali.a7minsworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.miniwangdali.a7minsworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.historyToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDAO = (application as WorkoutApp).db.historyDAO()
        getHistoryData(historyDAO)
    }

    private fun getHistoryData(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            historyDAO.fetchHistory().collect{
                historyList ->
                if (historyList.isNotEmpty()) {
                    binding?.noDataTextView?.visibility = View.GONE
                    binding?.completedTextView?.visibility = View.VISIBLE
                    binding?.historyListView?.visibility = View.VISIBLE
                    binding?.historyListView?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    binding?.historyListView?.adapter = HistoryItemAdapter(ArrayList(historyList))
                } else {
                    binding?.noDataTextView?.visibility = View.VISIBLE
                    binding?.completedTextView?.visibility = View.GONE
                    binding?.historyListView?.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}