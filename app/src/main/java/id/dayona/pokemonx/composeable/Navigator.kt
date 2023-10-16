package id.dayona.pokemonx.composeable

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

interface Navigator : MainView, PokemonDetail, CheckPermission {

    @Composable
    fun Navigation(activity: Activity) {
        val listOfPermission =
            listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        val nav = rememberNavController()
        NavHost(navController = nav, startDestination = "main") {
            composable("main") {
                CheckPermissionView(nav = nav, activity, listOfPermission.first())
                PokemonGridView(nav)
            }
            composable(
                "detail" + "{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("id")?.toInt() ?: 0
                PokemonDetailView(nav, id)
            }

            dialog(
                "show_permission",
                dialogProperties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                )
            ) {
                PermissionDialogView(nav)
            }
        }
    }
}