package id.adiyusuf.optimizesample.screen.result.state

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.adiyusuf.optimizesample.AppComposition
import id.adiyusuf.optimizesample.BasicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Composable
fun ResultStateScreen() {
    val navController = AppComposition.navigation
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )
    val scrollState = rememberScrollState()
    val navBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = navBackStackEntry?.savedStateHandle

    // Obs result
    val resultFrom by remember(navBackStackEntry) {
        savedStateHandle?.getLiveData<String>("result_from")
            ?.asFlow()
            ?.stateIn(
                scope = CoroutineScope(Dispatchers.Main.immediate),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ""
            ) ?: MutableStateFlow("")
    }.collectAsStateWithLifecycle()

    // Obs result state flow
    val resultValue by remember(navBackStackEntry) {
        savedStateHandle?.getStateFlow("result_value", "") ?: MutableStateFlow("")
    }.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text("From: $resultFrom")
        Spacer(modifier = Modifier.height(16.dp))
        Text("value: $resultValue")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate("result/state/next")
            }
        ) {
            Text("Next Page")
        }
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("Saved State Handle")
    }
}