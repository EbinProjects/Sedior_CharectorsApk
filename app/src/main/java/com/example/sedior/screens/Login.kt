import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.sedior.R
import com.example.sedior.utlisChars.Constants
import com.example.sedior.utlisChars.Screens
import com.example.sedior.viewmodel.CharacterViewmodel
import kotlinx.coroutines.NonDisposableHandle.parent


@Composable
fun LoginScreen(viewmodel: CharacterViewmodel, navController: NavHostController,modifier: Modifier = Modifier) {
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val passwordVisibility = remember { mutableStateOf(true) }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val (toTop, bottomButton) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .background(Color.White)
                .constrainAs(toTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(modifier = modifier
                .background(color = Color.White)
                .fillMaxSize(), contentAlignment = Alignment.Center){
                Image(painter = painterResource(id = R.drawable.login),
                      modifier = Modifier
                            .size(250.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    contentDescription = "loh")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .constrainAs(bottomButton) {
                    top.linkTo(toTop.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back!",
                modifier = modifier.fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier.size(3.dp))
            Text(
                text = "Enter your credentials to continue",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier.size(5.dp))
            OutlinedTextField(
                value = userName,
                onValueChange = {user ->
                    userName = user
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                label = {
                    Text(text = "Username")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                label = {
                    Text(text = "Password")
                },
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Filled.KeyOff else Icons.Default.Key,
                            contentDescription = "visibility"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.None
                ),
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None,
            )
            Button(
                onClick = {
                    val message = when {
                        userName.isEmpty() -> "Fill user credentials!"
                        password.isEmpty() -> "Fill password credentials!"
                        userName != Constants.USERNAME -> "Invalid Username!"
                        password != Constants.PASSWORD -> "Invalid Password!"
                        else -> null
                    }

                    message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        viewmodel.login {
                            if (it) {
                                navController.navigate(Screens.homeScreen.route) {
                                    navController.popBackStack()
                                }
                                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
 },
                modifier = modifier
                    .defaultMinSize()
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 8.dp)
                    .background(Color.Blue, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = modifier.size(5.dp))


        }
    }

}