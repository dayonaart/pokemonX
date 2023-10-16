package id.dayona.pokemonx.viewmodel

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class CheckPermissionViewModel : ViewModel() {
    val listOfPermission =
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE
        )
    var permissionNamed by mutableStateOf<String?>(null)

    var permissionListNamed by mutableStateOf<List<String?>>(listOf())
    var isPermissionDenied by mutableStateOf(false)
    var isPermissionListDenied by mutableStateOf(listOf(false))
    private var requestPermissionLauncher by mutableStateOf<ManagedActivityResultLauncher<String, Boolean>?>(
        null
    )
    private var requestMultiplePermissionLauncher by mutableStateOf<ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>?>(
        null
    )
    var hasOpenSettings by mutableStateOf(false)
    var hasOpenSettingsList by mutableStateOf<List<Boolean>>(listOf())

    fun initRequestPermissionLauncher(launcher: ManagedActivityResultLauncher<String, Boolean>) {
        requestPermissionLauncher = launcher
    }

    fun initRequestMultiplePermissionLauncher(
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        requestMultiplePermissionLauncher = launcher
    }

    fun triggerCheckPermission() {
        requestMultiplePermissionLauncher?.launch(listOfPermission)
    }

    fun setPermissionName(permission: String) {
        permissionNamed = permission
    }

    fun setPermissionListName(permission: List<String>) {
        permissionListNamed = permission
    }

    fun setPermanentlyDeclined(status: Boolean) {
        isPermissionDenied = status
    }

    fun setPermanentlyListDeclined(status: List<Boolean>) {
        isPermissionListDenied = status
    }

    fun setHasOpenSetting(open: Boolean) {
        hasOpenSettings = open
    }

    fun setHasOpenSettingList(open: List<Boolean>) {
        hasOpenSettingsList = open
    }

    fun gotoSettingApp(context: Context) {
        val intentSetting = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intentSetting)
    }
}