package id.adiyusuf.optimizesample.screen.sharing.viewmodel

import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import id.adiyusuf.optimizesample.BasicSharingViewModel
import id.adiyusuf.optimizesample.BasicViewModel
import id.adiyusuf.optimizesample.screen.sharing.SharingViewModel

@Composable
fun SharingVmScreen(
    viewModel: SharingVmViewModel = hiltViewModel()
) {
    val navController = AppComposition.navigation

    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val sharingViewModel: SharingViewModel = hiltViewModel(viewModelStoreOwner = navController.getBackStackEntry("sharing/main"))

    val scrollState = rememberScrollState()

    val basicSharingViewModel: BasicSharingViewModel = hiltViewModel(
        viewModelStoreOwner = navController.getBackStackEntry("sharing")
    )

    val text by basicViewModel.sharingActivity.collectAsStateWithLifecycle()
    val textPrevPage by sharingViewModel.sharingNextRoute.collectAsStateWithLifecycle()
    val textNested by basicSharingViewModel.text.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {
        Text("Sharing Activity:")
        Text(text)
        Spacer(Modifier.height(16.dp))

        CurrentView()

        Text("Sharing Prev Route:")
        Text(textPrevPage)
        Spacer(Modifier.height(16.dp))

        Text("Sharing Navigation:")
        Text(textNested)
        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("sharing/next")
            }
        ) {
            Text("Next Page")
        }
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("ViewModel")
        viewModel.changeCurrent("Halo guys. ini diambil dari data SharingVmViewModel. data diubah di composable SharingVmScreen dan data di tampilkan di composable CurrentView")
    }
}


@Composable
private fun CurrentView() {
    val viewModel: SharingVmViewModel = hiltViewModel()

    val textCurrentRoute by viewModel.sharingCurrentRoute.collectAsStateWithLifecycle()

    Text("Sharing Current Route:")
    Text(textCurrentRoute)
    Spacer(Modifier.height(16.dp))
}
