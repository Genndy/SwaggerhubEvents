package com.genndy.matches.backdrop

import android.view.View

/**
 * An interface to communicate between fragment and activity
 */
interface OnBottomSheetCallbacks {
    fun onStateChanged(bottomSheet: View, newState: Int)
}