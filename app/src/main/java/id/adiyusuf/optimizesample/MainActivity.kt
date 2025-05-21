package id.adiyusuf.optimizesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.adiyusuf.optimizesample.screen.home.HomeScreen
import id.adiyusuf.optimizesample.screen.result.ResultScreen
import id.adiyusuf.optimizesample.screen.result.argument.ResultArgumentScreen
import id.adiyusuf.optimizesample.screen.result.state.ResultStateScreen
import id.adiyusuf.optimizesample.screen.result.viewmodel.ResultVmScreen
import id.adiyusuf.optimizesample.screen.sharing.SharingScreen
import id.adiyusuf.optimizesample.screen.sharing.composition.SharingCompositionScreen
import id.adiyusuf.optimizesample.screen.sharing.event.SharingEventScreen
import id.adiyusuf.optimizesample.screen.sharing.remember.SharingRememberScreen
import id.adiyusuf.optimizesample.screen.sharing.singleton.SharingSingletonScreen
import id.adiyusuf.optimizesample.screen.sharing.state.SharingStateScreen
import id.adiyusuf.optimizesample.screen.sharing.viewmodel.SharingVmScreen
import id.adiyusuf.optimizesample.ui.theme.OptimizeSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OptimizeSampleTheme {
                val context = LocalContext.current
                val viewModel: BasicViewModel = hiltViewModel(
                    viewModelStoreOwner = context as ComponentActivity
                )
                val navController = rememberNavController()

                val title by viewModel.appBar.collectAsStateWithLifecycle()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "home"

                CompositionLocalProvider(LocalNavController provides navController) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(title)
                                },
                                navigationIcon = {
                                    AnimatedVisibility(
                                        currentRoute != "home",
                                        enter = fadeIn() + expandHorizontally(),
                                        exit = shrinkHorizontally() + fadeOut(),
                                    ) {
                                        IconButton(
                                            onClick = {
                                                navController.popBackStack()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable(route = "home") {
                                HomeScreen()
                            }

                            sharingNavigation()

                            resultNavigation()
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.sharingNavigation() {
    navigation(startDestination = "sharing/main", route = "sharing") {
        composable(route = "sharing/main") {
            SharingScreen()
        }
        composable("sharing/composition") {
            SharingCompositionScreen()
        }
        composable("sharing/event") {
            SharingEventScreen()
        }
        composable("sharing/remember") {
            SharingRememberScreen()
        }
        composable("sharing/singleton") {
            SharingSingletonScreen()
        }
        composable("sharing/state") {
            SharingStateScreen()
        }
        composable("sharing/viewmodel") {
            SharingVmScreen()
        }
    }
}

fun NavGraphBuilder.resultNavigation() {
    navigation(startDestination = "result/main", route = "result") {
        composable(route = "result/main") {
            ResultScreen()
        }
        composable("result/argument") {
            ResultArgumentScreen()
        }
        composable("result/state") {
            ResultStateScreen()
        }
        composable("result/viewmodel") {
            ResultVmScreen()
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavHostController not provided")
}

object AppComposition {
    /**
     * Retrieves the current [NavHostController] at the call site's position in the hierarchy.
     */
    val navigation: NavHostController
        @Composable
        @ReadOnlyComposable
        get() = LocalNavController.current
}