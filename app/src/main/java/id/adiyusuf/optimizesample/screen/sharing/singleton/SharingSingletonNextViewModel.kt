package id.adiyusuf.optimizesample.screen.sharing.singleton

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharingSingletonNextViewModel @Inject constructor(
    countRepository: CountRepository
) : ViewModel() {
    val count = countRepository.getIncrement().toString()
}