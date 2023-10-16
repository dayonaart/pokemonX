package id.dayona.pokemonx.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import id.dayona.pokemonx.viewmodel.PokemonDetailViewModel
import id.dayona.pokemonx.viewmodel.PokemonViewModel
import kotlinx.coroutines.delay

interface PokemonDetail {
    val pokemonDetailViewModel: PokemonDetailViewModel
    val pokemonViewModel: PokemonViewModel

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun PokemonDetailView(nav: NavController, id: Int) {
        val pokemon = pokemonViewModel.pokeDetail.find { it?.id == id }
        LaunchedEffect(key1 = Unit, block = {
            delay(500L)
            pokemonDetailViewModel.pokemonCries(id)
        })
        Scaffold(topBar = {
            AppBar(pokemonName = pokemon?.name ?: "Unknown")
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = pokemon?.sprites?.other?.home?.frontShiny,
                    contentDescription = "$id",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                FlowRow(maxItemsInEachRow = 3) {
                    pokemon?.types?.forEach { p ->
                        Text(text = "${p?.type?.url}")
                    }
                }
            }
        }
    }

    @Composable
    private fun AppBar(pokemonName: String) {
        Box(
            modifier = Modifier
                .background(color = Color.Unspecified)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = pokemonName, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}