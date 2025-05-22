package id.adiyusuf.optimizesample.screen.chart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor() : ViewModel() {
    private val _header = MutableStateFlow("Header")
    val header: StateFlow<String> = _header

    private val _description = MutableStateFlow("Description")
    val description: StateFlow<String> = _description

    fun changeHeader(value: String) {
        _header.value = value
    }

    fun changeDescription(value: String) {
        _description.value = value
    }
}