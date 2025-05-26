package id.adiyusuf.optimizesample.screen.sharing.composition

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import id.adiyusuf.optimizesample.BasicViewModel

val LocalTextState = staticCompositionLocalOf { mutableStateOf("Pesan Default") }
val LocalAppData = staticCompositionLocalOf<SharingCompositionData?> { null }
val LocalToggleState = staticCompositionLocalOf<SharingCompositionState?> { null }

@Composable
fun SharingCompositionScreen() {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val scrollState = rememberScrollState()

    val textState = remember { mutableStateOf("") }
    val data = SharingCompositionData("Material 3", "Indonesia")
    val toggleState = rememberSharingCompositionState()

    CompositionLocalProvider(
        LocalTextState provides textState,
        LocalAppData provides data,
        LocalToggleState provides toggleState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            ChildView()
        }
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("Composition")
    }
}

@Composable
private fun ChildView() {
    val textState = LocalTextState.current
    val data = LocalAppData.current
    val toggleState = LocalToggleState.current

    Column {
        Text("temanya adalah ${data?.theme}")
        Text("bahasanya adalah ${data?.language}")

        Spacer(Modifier.height(16.dp))

        TextField(
            textState.value,
            onValueChange = {
                textState.value = it
            }
        )

        Spacer(Modifier.height(16.dp))

        Text("toggle valuenya adalah ${toggleState?.value ?: "NA"}")

        Button(
            onClick = {
                toggleState?.show()
            }) {
            Text("Show")
        }

        Button(
            onClick = {
                toggleState?.hide()
            }) {
            Text("Hide")
        }
    }
}