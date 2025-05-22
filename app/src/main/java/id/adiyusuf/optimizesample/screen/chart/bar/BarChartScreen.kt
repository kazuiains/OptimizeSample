package id.adiyusuf.optimizesample.screen.chart.bar

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun BarChartScreen() {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    basicViewModel.changeTitle("Bar")

    val scrollState = rememberScrollState()

    val modelProducerBasicColumn = remember { CartesianChartModelProducer() }
    val modelProducerDailyDigitalMediaUse = remember { CartesianChartModelProducer() }
    val modelProducerRockMetalRatios = remember { CartesianChartModelProducer() }
    val modelProducerTemperature = remember { CartesianChartModelProducer() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text("Basic Column")
        JetpackComposeBasicColumnChart(modelProducerBasicColumn)
        Spacer(Modifier.height(32.dp))

        Text("Daily Digital Media Use")
        JetpackComposeDailyDigitalMediaUse(
            modelProducerDailyDigitalMediaUse
        )
        Spacer(Modifier.height(32.dp))

        Text("Rock Metal Ratios")
        JetpackComposeRockMetalRatios(modelProducerRockMetalRatios)
        Spacer(Modifier.height(32.dp))

        Text("Temperature Anomalies")
        JetpackComposeTemperatureAnomalies(modelProducerTemperature)
        Spacer(Modifier.height(32.dp))
    }

    LaunchedEffect(Unit) {
        modelProducerBasicColumn.runTransaction {
            // Learn more: https://patrykandpatrick.com/eji9zq.
            columnSeries { series(5, 6, 5, 2, 11, 8, 5, 2, 15, 11, 8, 13, 12, 10, 2, 7) }
        }

        modelProducerDailyDigitalMediaUse.runTransaction {
            columnSeries { DailyDigitalY.values.forEach { series(DailyDigitalX, it) } }
            extras { it[DailyDigitalLegendLabelKey] = DailyDigitalY.keys }
        }

        modelProducerRockMetalRatios.runTransaction {
            // Learn more: https://patrykandpatrick.com/eji9zq.
            columnSeries { series(RockMetalRatiosData.values) }
            extras { it[BottomAxisLabelKey] = RockMetalRatiosData.keys.toList() }
        }

        modelProducerTemperature.runTransaction {
            // Learn more: https://patrykandpatrick.com/eji9zq.
            columnSeries { series(TemperatureX, TemperatureY) }
        }
    }
}