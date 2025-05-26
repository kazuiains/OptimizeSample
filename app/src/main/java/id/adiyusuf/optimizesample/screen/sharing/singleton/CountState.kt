package id.adiyusuf.optimizesample.screen.sharing.singleton

import androidx.compose.runtime.mutableStateOf


object CountState {
    val sharedCount = mutableStateOf(0)

    fun increment() {
        sharedCount.value++
    }
}