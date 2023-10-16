package id.dayona.pokemonx.composeable

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import id.dayona.pokemonx.viewmodel.CheckPermissionViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface CheckPermission : ComposeUtils {
    val checkPermissionViewModel: CheckPermissionViewModel

    @Composable
    fun PermissionDialogView(nav: NavController) {
        val ctx = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val permissionName = checkPermissionViewModel.permissionNamed
        AlertDialog(
            onDismissRequest = {

            }, buttons = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Divider()
                    Text(
                        text = if (checkPermissionViewModel.isPermissionDenied) "Open Setting" else "Grant Permission",
                        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                if (checkPermissionViewModel.isPermissionDenied) {
                                    nav.popBackStack("show_permission", inclusive = true)
                                    checkPermissionViewModel.gotoSettingApp(ctx)
                                } else {
                                    nav.popBackStack("show_permission", inclusive = true)
                                    checkPermissionViewModel.triggerCheckPermission()
                                }
                            }
                            .padding(10.dp)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (permissionName) {
                        "android.permission.CAMERA" -> Text(
                            text = if (checkPermissionViewModel.isPermissionDenied) "This Application need to use Camera please enable it from setting" else "Please enable Camera Permission",
                            textAlign = TextAlign.Center
                        )

                        "android.permission.RECORD_AUDIO" -> Text(
                            text = if (checkPermissionViewModel.isPermissionDenied) "This Application need to use Audio please enable it from setting" else "Please enable AUDIO Permission",
                            textAlign = TextAlign.Center
                        )

                        "android.permission.CALL_PHONE" -> Text(
                            text = if (checkPermissionViewModel.isPermissionDenied) "This Application need to use Phone please enable it from setting" else "Please enable Phone Permission",
                            textAlign = TextAlign.Center
                        )

                        else -> {
                            Text(
                                text = "All permission required for using this application",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun CheckPermissionView(
        nav: NavController,
        activity: Activity,
        permission: String,
    ) {

        val coroutineScope = rememberCoroutineScope()
        val ctx = LocalContext.current
        val multiplePermission =
            rememberMultiplePermissionsState(permissions = checkPermissionViewModel.listOfPermission.toList())
        val permissionState =
            rememberPermissionState(permission = permission)
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (!it && permissionState.status.shouldShowRationale) {
                    checkPermissionViewModel.setPermanentlyDeclined(true)
                    checkPermissionViewModel.setHasOpenSetting(true)
                    nav.navigate("show_permission")
                    return@rememberLauncherForActivityResult
                }
                if (it) {
                    return@rememberLauncherForActivityResult
                } else {
                    nav.navigate("show_permission")

                }
            }

        val requestMultiplePermissionsLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                val isGranted = it.values.toList()
                val permissionName = it.keys.toList()
                isGranted.forEachIndexed { i, b ->
                    coroutineScope.launch {
                        async {
                            if (!isGranted[i]) {
                                checkPermissionViewModel.setPermissionName(permissionName[i])
                                checkPermissionViewModel.setPermanentlyDeclined(true)
                                checkPermissionViewModel.setHasOpenSetting(true)
                                nav.navigate("show_permission")
                                Log.d("TAG", "$it ")
                            }
                        }.join()
                    }
                }
            }

        OnLifecycleEvent(onEvent = { l, e ->
            when (e) {
                Lifecycle.Event.ON_CREATE -> {
//                    checkPermissionViewModel.setPermissionName(permissionState.permission)
                    checkPermissionViewModel.initRequestPermissionLauncher(requestPermissionLauncher)
//                    requestPermissionLauncher.launch(listOfPermission.first())
                    checkPermissionViewModel.initRequestMultiplePermissionLauncher(
                        requestMultiplePermissionsLauncher
                    )
                    requestMultiplePermissionsLauncher.launch(checkPermissionViewModel.listOfPermission)
                }

                Lifecycle.Event.ON_START -> {}
                Lifecycle.Event.ON_RESUME -> {
                    if (checkPermissionViewModel.hasOpenSettings) {
//                        requestPermissionLauncher.launch(permission)
                        requestMultiplePermissionsLauncher.launch(checkPermissionViewModel.listOfPermission)
                    }
                }

                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {}
                Lifecycle.Event.ON_ANY -> {}
            }
        })
    }
}