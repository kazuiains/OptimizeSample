package id.adiyusuf.optimizesample

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BasicSharingViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableStateFlow("Text")
    val text: StateFlow<String> = _text

    fun changeText(value: String) {
        _text.value = value
    }
}