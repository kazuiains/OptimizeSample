package id.adiyusuf.optimizesample.screen.sharing.singleton

import javax.inject.Inject

class CountRepositoryImpl @Inject constructor() : CountRepository {
    private var _sharedCount = 0

    override fun getIncrement(): Int = _sharedCount

    override fun setIncrement() {
        _sharedCount++
    }
}