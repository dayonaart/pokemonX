package id.dayona.pokemonx

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.RUNNING
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.pokemonx.composeable.Navigator
import id.dayona.pokemonx.composeable.RippleLoading
import id.dayona.pokemonx.ui.theme.PokemonXTheme
import id.dayona.pokemonx.viewmodel.CheckPermissionViewModel
import id.dayona.pokemonx.viewmodel.PokemonDetailViewModel
import id.dayona.pokemonx.viewmodel.PokemonViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity(), Navigator {
    private val TAG = "MainActivity"
    override val pokemonViewModel by viewModels<PokemonViewModel>()
    override val pokemonDetailViewModel by viewModels<PokemonDetailViewModel>()
    override val checkPermissionViewModel by viewModels<CheckPermissionViewModel>()
    override val activity: Activity = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loading by pokemonViewModel.pokemonListUrlState.collectAsState(initial = null)
            PokemonXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (loading?.state == RUNNING || loading?.state == ENQUEUED) {
                        true -> RippleLoading()
                        false -> Navigation(this)
                    }
                }
            }
        }
    }
}
