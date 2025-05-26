package id.adiyusuf.optimizesample.screen.result

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.adiyusuf.optimizesample.AppComposition
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val navController = AppComposition.navigation
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Header()
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("result/argument")
            }
        ) {
            Text("Argument")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("result/state")
            }
        ) {
            Text("State")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.changeHeader("Result in Jetpack Compose")
        viewModel.changeDescription("tutorial mengirim data ke screen sebelumnya.")
        basicViewModel.changeTitle("Result")
    }
}

@Composable
private fun Header() {
    val viewModel: ResultViewModel = hiltViewModel()

    val header by viewModel.header.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()

    Column {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(0.2f))
                .padding(16.dp)
        ) {
            Text(header)
            Text(description, fontSize = 12.sp)

        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}