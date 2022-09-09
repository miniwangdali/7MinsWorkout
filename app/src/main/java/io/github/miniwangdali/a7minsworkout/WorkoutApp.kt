package io.github.miniwangdali.a7minsworkout

import android.app.Application

class WorkoutApp: Application() {
    val db by lazy {
        HistoryDB.getInstance(this)
    }
}