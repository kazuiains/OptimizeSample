package id.adiyusuf.optimizesample.screen.sharing.singleton

interface CountRepository {
    fun getIncrement(): Int
    fun setIncrement()
}