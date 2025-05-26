package id.adiyusuf.optimizesample.screen.sharing.singleton

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.adiyusuf.optimizesample.AppComposition
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun SharingSingletonScreen(viewModel: SharingSingletonViewModel = hiltViewModel()) {
    val navController = AppComposition.navigation

    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val scrollState = rememberScrollState()

    val count by viewModel.count.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text("From Compose A - Count: ${CountState.sharedCount.value}")
        Button(onClick = { CountState.increment() }) {
            Text("Increment")
        }
        Spacer(Modifier.height(16.dp))
        SampleCompose()

        Spacer(Modifier.height(32.dp))

        Text("From Compose A from viewmodel - Count: $count")
        Button(onClick = {viewModel.increment() }) {
            Text("Increment Viewmodel")
        }
        Spacer(Modifier.height(16.dp))
        SampleCompose2()

        Spacer(Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/singleton/next")
            }
        ) {
            Text("Next Page")
        }
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("Singleton")
    }
}

@Composable
private fun SampleCompose() {
    Text("SampleCompose - Count: ${CountState.sharedCount.value}")
}

@Composable
private fun SampleCompose2() {
    val viewModel: SharingSingletonViewModel = hiltViewModel()
    val count by viewModel.count.collectAsStateWithLifecycle()

    Text("SampleCompose2 from viewmodel - Count: $count")
}