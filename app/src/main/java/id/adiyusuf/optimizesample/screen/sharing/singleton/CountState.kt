package id.adiyusuf.optimizesample.screen.sharing.singleton

import androidx.compose.runtime.mutableIntStateOf


object CountState {
    val sharedCount = mutableIntStateOf(0)

    fun increment() {
        sharedCount.intValue++
    }
}