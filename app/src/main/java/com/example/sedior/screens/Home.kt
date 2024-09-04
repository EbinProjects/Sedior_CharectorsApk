import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sedior.R
import com.example.sedior.utlisChars.ErrorHandle
import com.example.sedior.utlisChars.Screens
import com.example.sedior.viewmodel.CharacterViewmodel


@Composable
fun HomePage(navController: NavHostController, viewmodel: CharacterViewmodel) {
    val charResponse by viewmodel.charactersResponses.collectAsState()
    val serach by viewmodel.searchValue.collectAsState()
    val characters by viewmodel.searchedChars.collectAsState()
    val searching by viewmodel.isSearching.collectAsState()
    val endReached by remember { viewmodel.reachEnded }
    when (val response = charResponse) {
        is ErrorHandle.Loading -> {
            ProgressingPage()
        }

        is ErrorHandle.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.theme_semibold)),
                            fontSize = 25.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(
                        onClick = { viewmodel.logOut {
                            navController.navigate(Screens.loginScreen.route){
                                navController.popBackStack()
                            }
                        } },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Top Right Icon"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp),
                    value = serach,
                    onValueChange = {
                        viewmodel.searchSongs(it)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.None
                    ),
                    trailingIcon = {
                        if (serach.isNotEmpty()) {
                            Icon(imageVector = Icons.Default.Remove,
                                contentDescription = "",
                                tint = Color(0xFFC53333),
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .offset(x = 10.dp)
                                    .clickable {
                                    viewmodel.searchSongs("")
                                    })
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Search...",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.theme_semibold)),
                                fontSize = 16.sp
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                    if (searching){
                        ProgressingPage()
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(characters.size) { count ->
                            if (count>= characters.size - 1 ){
                                viewmodel.loadNextPage()
                            }
                            DataInSide(text = characters[count].name.toString(), image = characters[count].image){
                                if (it){
                                    viewmodel.fetchCahrecter(characters[count].id)
                                    navController.navigate(Screens.Details.route)
                                }
                            }
                        }

                    }



            }

        }

        is ErrorHandle.Error -> {
            ServiceError(errorType = response.errorType, label = "Retry") {
                viewmodel.currentPage = 1
                viewmodel.loadNextPage()
            }
        }
    }


}

@Composable
fun DataInSide(text: String, image: String?, function: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { function(true) }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "image1",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.error),
                placeholder = painterResource(R.drawable.loading),
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                textAlign = TextAlign.Center,
                text = text.uppercase(),
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

    }
}
