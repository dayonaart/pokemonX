package id.dayona.pokemonx.composeable

import android.app.Activity
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import id.dayona.pokemonx.viewmodel.PokemonViewModel

interface MainView {
    val pokemonViewModel: PokemonViewModel
    val activity: Activity

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun PokemonGridView(nav: NavController) {
        val ctx = LocalContext.current
        val imageLoader = ImageLoader.Builder(ctx)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        val pokemon =
            pokemonViewModel.pokeDetail.mapNotNull { it }
        FlowRow(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            maxItemsInEachRow = 3
        ) {
            repeat(pokemon.size) {
                Box(
                    modifier = Modifier
                        .clickable {
                            nav.navigate("detail" + "${pokemon[it].id}")
                        }
                        .padding(10.dp)
                        .border(
                            width = 1.dp, color = Color.Blue,
                            shape = RoundedCornerShape(10),
                        )
                        .padding(10.dp)
                        .weight(1f / 2),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp, color = Color.Blue,
                                shape = RoundedCornerShape(10),
                            )
                            .align(alignment = Alignment.TopEnd)
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${pokemon[it].id}", fontSize = 10.sp)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(activity)
                                    .data(data = Uri.parse(pokemon[it].sprites?.versions?.generationV?.blackWhite?.animated?.frontDefault))
                                    .build(),
                                imageLoader = imageLoader,
                            ),
                            contentDescription = "hh"
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "${pokemon[it].name}", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}