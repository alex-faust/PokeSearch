package com.example.pokesearch.utils

class BallUtils {

    private var spinning: Boolean = false

    fun startSpin(start: Boolean): Boolean {
        spinning = true
        return spinning
    }

    fun stopSpin(stop: Boolean): Boolean {
        spinning = false

        return spinning
    }

    //fun getSpinningState
}