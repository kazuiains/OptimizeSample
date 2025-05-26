package id.adiyusuf.optimizesample.screen.sharing.singleton

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SharingSingletonViewModel @Inject constructor(
    private val countRepository: CountRepository
) : ViewModel() {
    private val _count = MutableStateFlow(countRepository.getIncrement().toString())
    val count: StateFlow<String> = _count

    fun increment() {
        countRepository.setIncrement()
        _count.value = countRepository.getIncrement().toString()
    }
}