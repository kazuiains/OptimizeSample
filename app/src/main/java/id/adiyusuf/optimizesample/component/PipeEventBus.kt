package id.adiyusuf.optimizesample.component

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object PipeEventBus {
    private val _events = Channel<Any>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: Any) {
        _events.send(event)
    }
}