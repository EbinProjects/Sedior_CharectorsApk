import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sedior.R
import com.example.sedior.utlisChars.Screens
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController,modifier : Modifier =Modifier) {
    val content = LocalContext.current
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logo),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop,
            contentDescription = "splash")
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "SEIDOR",
            style = TextStyle(
                fontFamily = FontFamily.Cursive,
                fontSize = 25.sp
            ),
            modifier = Modifier,
            color = Color.Black
        )
    }
    LaunchedEffect(Unit) {
        delay(1400)
        val pref = content.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val login = pref.getBoolean("Login", false)
        val destination = if (login) Screens.homeScreen.route else Screens.loginScreen.route
        navController.navigate(destination) {
            navController.popBackStack()
        }
    }


}
