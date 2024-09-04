import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.example.sedior.R
import com.example.sedior.utlisChars.ErrorHandle


@Composable
fun ServiceError(errorType: ErrorHandle.ErrorType, label: String, onMoveBack: () -> Unit ){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.network),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Increased space for better separation
            Text(
                text = when (errorType) {
                    ErrorHandle.ErrorType.Network -> "Check Your Network!Poor"
                    ErrorHandle.ErrorType.Server -> "Server Error!"
                    ErrorHandle.ErrorType.Unauthorized -> "Unauthorized Error!"
                    ErrorHandle.ErrorType.NotFound -> "Not Found Error!"
                    ErrorHandle.ErrorType.Unknown -> "Unknown Error!"
                },
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onMoveBack,
                colors = ButtonDefaults.buttonColors(contentColor = Color.Blue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}


@Composable
fun ProgressingPage(modifier: Modifier =Modifier){
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BallSpinFadeLoaderProgressIndicator(
            modifier = Modifier.width(100.dp).height(100.dp),
            color = Color.Gray,
            animationDuration = 2000,
            maxBallDiameter = 5.dp,
            minBallDiameter = 5.dp,
            diameter = 10.dp,
        )
    }
}

