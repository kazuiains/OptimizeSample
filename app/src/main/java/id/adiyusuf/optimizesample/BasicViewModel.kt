package id.adiyusuf.optimizesample

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor() : ViewModel() {
    private val _appBar = MutableStateFlow("Basic")
    val appBar: StateFlow<String> = _appBar

    private val _sharingActivity = MutableStateFlow("ActivitySharing")
    val sharingActivity: StateFlow<String> = _sharingActivity

    fun changeTitle(value: String) {
        _appBar.value = value
    }

    fun changeActivity(value: String) {
        _sharingActivity.value = value
    }
}