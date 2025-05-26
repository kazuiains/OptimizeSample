package id.adiyusuf.optimizesample.component

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object BoardEventBus {
    private val _events = MutableSharedFlow<Any>()
    val events = _events.asSharedFlow()

    suspend fun publishEvent(event: Any) {
        _events.emit(event)
    }
}