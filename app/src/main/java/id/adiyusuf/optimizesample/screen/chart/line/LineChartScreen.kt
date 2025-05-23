package id.adiyusuf.optimizesample.screen.chart.line

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
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun LineChartScreen() {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    basicViewModel.changeTitle("Line")

    val scrollState = rememberScrollState()

    val modelProducerBasic = remember { CartesianChartModelProducer() }
    val modelProducerAiTest = remember { CartesianChartModelProducer() }
    val modelProducerElectricCar = remember { CartesianChartModelProducer() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text("Basic Line")
        JetpackComposeBasicLineChart(modelProducerBasic)
        Spacer(Modifier.height(32.dp))

        Text("AI Test Scores")
        JetpackComposeAITestScores(modelProducerAiTest)
        Spacer(Modifier.height(32.dp))

        Text("Electric Car")
        JetpackComposeElectricCarSales(modelProducerElectricCar)
        Spacer(Modifier.height(32.dp))
    }

    LaunchedEffect(Unit) {
        modelProducerBasic.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(13, 8, 7, 12, 0, 1, 15, 14, 0, 11, 6, 12, 0, 11, 12, 11) }
        }

        modelProducerAiTest.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { AiTestScoresData.forEach { (_, map) -> series(map.keys, map.values) } }
            extras { extraStore -> extraStore[AiTestScoresLegendLabelKey] = AiTestScoresData.keys }
        }

        modelProducerElectricCar.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(ElectricCarX, ElectricCarY) }
        }
    }
}