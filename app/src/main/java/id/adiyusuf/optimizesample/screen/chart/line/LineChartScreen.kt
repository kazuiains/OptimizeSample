package id.adiyusuf.optimizesample.screen.chart.line

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun LineChartScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    basicViewModel.changeTitle("Line")
}