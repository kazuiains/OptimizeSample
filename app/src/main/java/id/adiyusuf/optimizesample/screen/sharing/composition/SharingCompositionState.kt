package id.adiyusuf.optimizesample.screen.sharing.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class SharingCompositionState {
    var value: Boolean by mutableStateOf(false)
        private set

    fun show(){
        value = true
    }

    fun hide(){
        value = false
    }
}

@Composable
fun rememberSharingCompositionState(): SharingCompositionState {
    return remember { SharingCompositionState() }
}