package id.adiyusuf.optimizesample.screen.sharing.state

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import id.adiyusuf.optimizesample.BasicViewModel

@Composable
fun SharingStateScreen() {
    val context = LocalContext.current
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    var inputText by remember { mutableStateOf("") }
    var inputSaveableText by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        ComposeSample(
            text = inputText,
            onTextChange = { newText -> inputText = newText }
        )
        Text("You typed: $inputText")

        Spacer(Modifier.height(16.dp))

        ComposeSample(
            text = inputSaveableText,
            onTextChange = { newText -> inputSaveableText = newText }
        )
        Text("You typed saveable: $inputSaveableText")
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("State Hoisting")
    }
}

@Composable
private fun ComposeSample(
    text: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text("Enter text")
        }
    )
}