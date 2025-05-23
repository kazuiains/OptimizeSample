package id.adiyusuf.optimizesample.screen.sharing.composition

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

val StateStateLocalMessage = staticCompositionLocalOf { mutableStateOf("Pesan Default") }
val DataLocalMessage = staticCompositionLocalOf<SharingCompositionData?> { null }
val StateHolderDataLocalMessage = staticCompositionLocalOf<SharingCompositionState?> { null }

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
        StateStateLocalMessage provides textState,
        DataLocalMessage provides data,
        StateHolderDataLocalMessage provides toggleState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
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
    val textState = StateStateLocalMessage.current
    val data = DataLocalMessage.current
    val toggleState = StateHolderDataLocalMessage.current

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