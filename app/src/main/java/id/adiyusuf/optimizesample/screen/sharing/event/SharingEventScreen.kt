package id.adiyusuf.optimizesample.screen.sharing.event

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.adiyusuf.optimizesample.BasicViewModel
import id.adiyusuf.optimizesample.component.BoardEventBus
import id.adiyusuf.optimizesample.component.PipeEventBus
import id.adiyusuf.optimizesample.component.TestEvent
import kotlinx.coroutines.launch

@Composable
fun SharingEventScreen(viewModel: SharingEventViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val basicViewModel: BasicViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {

        Text("Send From Composable: ")
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    scope.launch {
                        PipeEventBus.sendEvent(
                            TestEvent.Data(
                                "pipe",
                                "compose",
                                "fufufafa x prabowo"
                            )
                        )
                    }
                }
            ) {
                Text("Pipe Event")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    scope.launch {
                        BoardEventBus.publishEvent(
                            TestEvent.Data(
                                "board",
                                "compose",
                                "mulyono x mulyani"
                            )
                        )
                    }
                }
            ) {
                Text("Board Event")
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Send From ViewModel: ")
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.sendPipeEvent()
                }
            ) {
                Text("Pipe Event")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.publishBoardEvent()
                }
            ) {
                Text("Board Event")
            }
        }
        Spacer(Modifier.height(32.dp))
        Text("Result: ")
        Spacer(Modifier.height(16.dp))
        SampleCompose1()
        SampleCompose2()
    }

    LaunchedEffect(Unit) {
        basicViewModel.changeTitle("Event")
    }
}

@Composable
private fun SampleCompose1() {
    val type = remember { mutableStateOf("") }
    val from = remember { mutableStateOf("") }
    val value = remember { mutableStateOf("") }

    Column {
        Text("Menggunakan: ${type.value}")
        Text("dari: ${from.value}")
        Text("value: ${value.value}")
        Spacer(Modifier.height(16.dp))
    }

    LaunchedEffect(Unit) {
        BoardEventBus.events.collect { event ->
            when (event) {
                is TestEvent.Data -> {
                    type.value = event.type
                    from.value = event.from
                    value.value = event.value
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        PipeEventBus.events.collect { event ->
            when (event) {
                is TestEvent.Data -> {
                    type.value = event.type
                    from.value = event.from
                    value.value = event.value
                }
            }
        }
    }
}


@Composable
private fun SampleCompose2() {
    val viewModel: SharingEventViewModel = hiltViewModel()

    val type by viewModel.type.collectAsStateWithLifecycle()
    val from by viewModel.from.collectAsStateWithLifecycle()
    val value by viewModel.value.collectAsStateWithLifecycle()

    Column {
        Text("Menggunakan: $type")
        Text("dari: $from")
        Text("value: $value")
        Spacer(Modifier.height(16.dp))
    }
}