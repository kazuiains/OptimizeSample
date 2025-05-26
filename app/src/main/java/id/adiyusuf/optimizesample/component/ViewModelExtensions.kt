package id.adiyusuf.optimizesample.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        PipeEventBus.sendEvent(event)
    }
}

fun ViewModel.publishEvent(event: Any) {
    viewModelScope.launch {
        BoardEventBus.publishEvent(event)
    }
}