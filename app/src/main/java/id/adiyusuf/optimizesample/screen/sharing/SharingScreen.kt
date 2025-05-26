package id.adiyusuf.optimizesample.screen.sharing

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
import id.adiyusuf.optimizesample.BasicSharingViewModel
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun SharingScreen(
    viewModel: SharingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val navController = AppComposition.navigation
    val scrollState = rememberScrollState()

    val basicSharingViewModel: BasicSharingViewModel = hiltViewModel(
        viewModelStoreOwner = navController.getBackStackEntry("sharing")
    )

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
                navController.navigate("sharing/composition")
            }
        ) {
            Text("Composition")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/event")
            }
        ) {
            Text("Event")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/singleton")
            }
        ) {
            Text("Singleton")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/state")
            }
        ) {
            Text("State Hoisting")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/viewmodel")
                viewModel.changeNext("Halo guys. ini diambil dari data SharingViewModel. data diubah di composable SharingScreen (page sebelumnya) dan data di tampilkan di composable SharingVmScreen (page saat ini)")
            }
        ) {
            Text("ViewModel")
        }
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("Sharing")
        basicSharingViewModel.changeText("Sharing Ke semua yang ada di navigation sharing")
        viewModel.changeHeader("Sharing in Jetpack Compose")
        viewModel.changeDescription("tutorial sharing data antar screen, composable function.")
    }
}

@Composable
private fun Header() {
    val viewModel: SharingViewModel = hiltViewModel()

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