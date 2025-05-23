package id.adiyusuf.optimizesample.screen.sharing.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SharingVmViewModel @Inject constructor() : ViewModel() {
    private val _sharingCurrentRoute = MutableStateFlow("CurrentRouteSharing")
    val sharingCurrentRoute: StateFlow<String> = _sharingCurrentRoute

    fun changeCurrent(value: String) {
        _sharingCurrentRoute.value = value
    }

}