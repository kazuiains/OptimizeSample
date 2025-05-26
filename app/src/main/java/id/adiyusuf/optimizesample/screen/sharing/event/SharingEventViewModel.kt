package id.adiyusuf.optimizesample.screen.sharing.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.adiyusuf.optimizesample.component.BoardEventBus
import id.adiyusuf.optimizesample.component.PipeEventBus
import id.adiyusuf.optimizesample.component.TestEvent
import id.adiyusuf.optimizesample.component.publishEvent
import id.adiyusuf.optimizesample.component.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharingEventViewModel @Inject constructor() : ViewModel() {
    private val _type = MutableStateFlow("")
    val type: StateFlow<String> = _type

    private val _from = MutableStateFlow("")
    val from: StateFlow<String> = _from

    private val _value = MutableStateFlow("")
    val value: StateFlow<String> = _value


    init {
        viewModelScope.launch {
            BoardEventBus.events.collect { event ->
                when (event) {
                    is TestEvent.Data -> {
                        _type.value = event.type
                        _from.value = event.from
                        _value.value = event.value
                    }
                }
            }
        }

        viewModelScope.launch {
            PipeEventBus.events.collect { event ->
                when (event) {
                    is TestEvent.Data -> {
                        _type.value = event.type
                        _from.value = event.from
                        _value.value = event.value
                    }
                }
            }
        }
    }

    fun sendPipeEvent() {
        sendEvent(TestEvent.Data("pipe", "viewmodel", "uu uaa ding ding"))
    }

    fun publishBoardEvent() {
        publishEvent(TestEvent.Data("board", "viewmodel", "wi wok de tok"))
    }
}