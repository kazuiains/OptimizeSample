package id.adiyusuf.optimizesample.screen.sharing.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.adiyusuf.optimizesample.AppComposition
import id.adiyusuf.optimizesample.BasicSharingViewModel

@Composable
fun NextScreen() {
    val navController = AppComposition.navigation
    val scrollState = rememberScrollState()
    val basicSharingViewModel: BasicSharingViewModel = hiltViewModel(
        viewModelStoreOwner = navController.getBackStackEntry("sharing")
    )

    val textNested by basicSharingViewModel.text.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text("Cuma Nampilin ya guys!")
        Text(textNested)
    }
}