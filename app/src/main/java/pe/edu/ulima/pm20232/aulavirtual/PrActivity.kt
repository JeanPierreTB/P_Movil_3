package pe.edu.ulima.pm20232.aulavirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.HomeScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.ProfileScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.VerPerfilScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screens.HomeScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.VerPerfil
import pe.edu.ulima.pm20232.aulavirtual.services.GenerationService
import pe.edu.ulima.pm20232.aulavirtual.services.MemberServices
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.AulaVirtualTheme
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.Orange400
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.White400

class PrActivity : ComponentActivity() {
    private val verPerfilScrennViewModel by viewModels<VerPerfilScreenViewModel>()
    val imageUrl = "https://e.rpp-noticias.io/xlarge/2021/11/02/140114_1168254.jpg"
    val IconPerson = Icons.Default.Person//IMPORTANTE no BORRAR el icono de la persona
    private val homeScrennViewModel by viewModels<HomeScreenViewModel>()
    val IconPhone = Icons.Default.Phone
    val IconEmail = Icons.Default.Email
    private val memberService = MemberServices()
    private val generationService = BackendClient.buildService(GenerationService::class.java)
    //private val coroutine: CoroutineScope = viewModelScope

    @Composable
    fun ImageView(url: String, height: Int, width: Int) {
        val painter = rememberImagePainter(
            data = url,
            builder = {
                // You can apply transformations here if needed
                transformations(CircleCropTransformation())
            }
        )
        Image(
            painter = painter,
            contentDescription = null, // Set a proper content description if required
            modifier = Modifier.size(width.dp, height.dp)
        )
    }

    @Composable
    fun ButtonWithIcon2(
        text: String,
        onClick: () -> Unit
    ) {
        Button( //crear función para botones
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Orange400, // cambia color por estado
                contentColor = if (isSystemInDarkTheme()) White400 else Color.Black,// cambia color por estado
                // Text and icon color
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        var param1 = 5

        var memberWithId1 = memberService.memberList.find { it.id == param1 }

        val extras = intent.extras
        if (extras != null) {
            // Retrieve values from the Intent's extras
            param1 = extras.getInt("userId")
            Log.d("ADMIN ACTIVITY", param1.toString())
            memberWithId1 = memberService.memberList.find { it.id == param1 }

        }

        super.onCreate(savedInstanceState)
        setContent {
            AulaVirtualTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val blackList: List<String> = listOf("profile", "login", "ver_perfil")
                    val currentRoute = navBackStackEntry?.destination?.route


                    VerPerfil(navController, param1, verPerfilScrennViewModel)
                }
            }
        }
    }
}
