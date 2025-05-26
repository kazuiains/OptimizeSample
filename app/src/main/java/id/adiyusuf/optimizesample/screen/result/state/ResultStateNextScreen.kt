package id.adiyusuf.optimizesample.screen.result.state

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.adiyusuf.optimizesample.AppComposition

@Composable
fun ResultStateNextScreen() {
    val navController = AppComposition.navigation

    Button(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = {
            val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set("result_from", "SavedStateHandle - Composable")
            savedStateHandle?.set("result_value", "Ululululu byur byar")
            navController.popBackStack() // Kembali ke destinasi sebelumnya
        }
    ) {
        Text("Send Result")
    }
}