package id.adiyusuf.optimizesample.component

sealed interface TestEvent {
    data class Data(
        val type: String,
        val from: String,
        val value: String,
    ) : TestEvent
}