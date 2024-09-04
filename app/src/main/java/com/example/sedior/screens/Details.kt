import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.AccessTimeFilled
import androidx.compose.material.icons.outlined.DynamicFeed
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.ImageAspectRatio
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sedior.R
import com.example.sedior.utlisChars.ErrorHandle
import com.example.sedior.viewmodel.CharacterViewmodel


@Composable
fun DetailsScreen(navController: NavHostController, viewmodel: CharacterViewmodel) {
    val charResponse by viewmodel.characterResponses.collectAsState()
    when (val response = charResponse) {
        is ErrorHandle.Loading -> {
            ProgressingPage()
        }

        is ErrorHandle.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Details",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.theme_semibold)),
                                fontSize = 25.sp
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew, // Replace with your icon
                                contentDescription = "Top Right Icon"
                            )
                        }
                    }
                    AsyncImage(
                        model = response.data.image,
                        contentDescription = "image2",
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.error),
                        placeholder = painterResource(R.drawable.loading),
                        modifier = Modifier
                            .size(300.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnotherBoxContent(
                            heading = "Name" ,
                            image = Icons.Outlined.DynamicFeed ,
                            subTxt = response.data.name ?: ""
                        )
                        AnotherBoxContent(
                            heading = "Species" ,
                            image = Icons.Outlined.ImageAspectRatio ,
                            subTxt = response.data.species ?: ""
                        )
                        AnotherBoxContent(
                            heading = "Status" ,
                            image = Icons.Outlined.DynamicFeed ,
                            subTxt = response.data.status ?: ""
                        )
                        AnotherBoxContent(
                            heading = "Location Name" ,
                            image = Icons.Outlined.EditLocationAlt ,
                            subTxt = response.data.location?.name ?: ""
                        )
                    }
                }
            }

        }

        is ErrorHandle.Error -> {
            ServiceError(errorType = response.errorType, label = "Move Back") {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun AnotherBoxContent(modifier: Modifier = Modifier, image : ImageVector, heading: String, subTxt : String){
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = heading,
                modifier = modifier.padding(8.dp),
                fontFamily = FontFamily(Font(R.font.theme_semibold)),
                color = Color.Black,
                fontSize = 17.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.offset(x=18.dp)) {
            Icon(
                imageVector = image,
                contentDescription = "Icon",
                tint = Color.White,
                modifier= modifier
                    .background(Color.Cyan , shape = RoundedCornerShape(8.dp))
                    .size(30.dp)
                    .padding(4.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = subTxt,
                modifier = modifier.padding(8.dp),
                fontFamily = FontFamily(Font(R.font.theme_regular)),
                color = Color.Black,
                fontSize = 15.sp)
        }
        TabRowDefaults.Divider(
            color = Color.Black ,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
