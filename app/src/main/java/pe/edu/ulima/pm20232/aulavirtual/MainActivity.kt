package pe.edu.ulima.pm20232.aulavirtual

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pe.edu.ulima.pm20232.aulavirtual.components.BottomNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.components.TopNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.configs.BottomBarScreen
import pe.edu.ulima.pm20232.aulavirtual.configs.TopBarScreen
import pe.edu.ulima.pm20232.aulavirtual.factories.LoginScreenViewModelFactory
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.HomeScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.LoginScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.PokemonDetailScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.ProfileScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RoutineScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screens.HomeScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.LoginScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.PokemonDetailScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.PokemonScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.ProfileScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.ResetPasswordScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.RoutineDetailScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.RoutineScreen
import pe.edu.ulima.pm20232.aulavirtual.screens.SplashScreen
import pe.edu.ulima.pm20232.aulavirtual.factories.RegisterViewModelFactory
import pe.edu.ulima.pm20232.aulavirtual.factories.ResetPasswordViewModelFactory
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.*
import pe.edu.ulima.pm20232.aulavirtual.screens.*
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.AulaVirtualTheme

class MainActivity : ComponentActivity() {
    private val loginScrennViewModel: LoginScreenViewModel by viewModels {
        LoginScreenViewModelFactory(applicationContext)
    }
    private val resetPasswordViewModel:ResetPasswordViewModel by viewModels {
        ResetPasswordViewModelFactory(applicationContext)
    }
    private val registerViewModel:RegisterViewModel by viewModels {
        RegisterViewModelFactory(applicationContext)
    }
    private val profileScrennViewModel by viewModels<ProfileScreenViewModel>()
    private val homeScrennViewModel by viewModels<HomeScreenViewModel>()
    private val pokemonDetailScrennViewModel by viewModels<PokemonDetailScreenViewModel>()
    private val routineScreenViewModel by viewModels<RoutineScreenViewModel>()

    //Para la conexion de la base de datos
    private val viewModel: MemberScreenViewModel by viewModels()
    var userId: Int by mutableStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        // set preferencesManager in viewModels

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
                    val blackList: List<String> = listOf("profile", "login")
                    val currentRoute = navBackStackEntry?.destination?.route
                    var showDialog by remember { mutableStateOf(false) }
                    var showShare by remember { mutableStateOf(false) }
                    val dataStore = UserStorage(applicationContext)

                    var imageUri by remember { mutableStateOf<Uri?>(null) }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()){ uri: Uri? ->
                        imageUri = uri
                    }
                    val launcherImage = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult(),
                        onResult = {
                            Log.d("POKEMON_DETAIL_SCREEN", "onResult")
                        }
                    )
                    val context = LocalContext.current
                    Scaffold(
                        topBar = {
                            if(
                                currentRoute !in listOf("reset", "login","register","profile")
                                ) {
                                val screens: List<TopBarScreen> = listOf(
                                    /*TopBarScreen(
                                        route = "home",
                                        title = "Home",
                                    ),*/
                                    TopBarScreen(
                                        route = "ver_perfil",
                                        title = "Ver Perfil",
                                    ),
                                    /*TopBarScreen(
                                        route = "pokemon",
                                        title = "Ver Pokemones",
                                    ),*/
                                    TopBarScreen(
                                        title = "Acerca de",
                                        onClick = {
                                            Log.d("MAIN_ACTIVITY","PROFILE!!")
                                            showDialog = true
                                        }
                                    ),
                                    TopBarScreen(
                                        title = "Cerrar Sesión",
                                        onClick = {
                                            Log.d("MAIN_ACTIVITY","Cerrar Sesión!!")
                                            finishAffinity()
                                        }
                                    ),

                                    )
                                TopNavigationBar(navController, screens, this, dataStore, userId)

                            }
                        },
                        bottomBar = {
                            if(currentRoute !in listOf("reset", "login","register","profile")) {
                                val screens: List<BottomBarScreen> = listOf(
                                    BottomBarScreen(
                                        route = "routine",
                                        title = "Mi Rutina",
                                        icon = Icons.Default.DateRange,
                                        onClick = {
                                            routineScreenViewModel.filtrar = true
                                        }
                                    ),
                                    BottomBarScreen(
                                        route = "routine",
                                        title = "Ejercicios",
                                        icon = Icons.Default.List,
                                        onClick = {
                                            routineScreenViewModel.filtrar = false
                                        }
                                    ),
                                    BottomBarScreen(
                                        title = "Compartir",
                                        icon = Icons.Default.Share,
                                        onClick = {
                                            showShare = true
                                        }
                                    ),
                                )
                                BottomNavigationBar(navController = navController, screens)

                            }
                        },
                        content = {
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = {
                                        showDialog = false
                                    },
                                    title = {
                                        Text(
                                            text = "Integrantes del Grupo",
                                            //color = Color.Black,
                                            fontSize = 24.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    text = {
                                        /*
                                        //Funciona pero el profe quiere solo los integrantes del grupo.
                                        //Ademas el cuadro se ve muy grande.
                                            LazyColumn(
                                                contentPadding = PaddingValues(16.dp)
                                            ) {
                                                items(memberList) { member ->
                                                    Text(text = "${member.code} - ${member.names}")
                                                }
                                            }
                                        */
                                        LazyColumn(
                                            contentPadding = PaddingValues(16.dp)
                                        ) {
                                            val arrayIntegrantes = intArrayOf(5, 6, 7, 9, 14, 23)
                                            lifecycleScope.launch {
                                                arrayIntegrantes.forEach { memberId ->
                                                    val membersMap = viewModel.fetchAllMemberTeam(memberId)
                                                    // Ahora puedes utilizar membersMap en tu actividad
                                                    // Por ejemplo, imprimir los valores
                                                    membersMap.forEach { (memberId, memberInfo) ->
                                                        println("$memberInfo")
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    confirmButton = {

                                    },
                                    dismissButton = {

                                    }
                                )
                            }
                            if (showShare) {
                                AlertDialog(
                                    onDismissRequest = {
                                        showShare = false
                                    },
                                    title = {
                                        Text(
                                            text = "Gracias por compartir",
                                            color = Color.Black,
                                            fontSize = 24.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    text = {
                                        /*
                                        val imageUrl = "https://cdn-icons-png.flaticon.com/512/1384/1384095.png"
                                        val uri = Uri.parse(imageUrl)
                                        val painter = rememberImagePainter(
                                            data = uri.scheme + "://" + uri.host + uri.path + (if (uri.query != null) uri.query else ""),
                                            builder = {
                                                // You can apply transformations here if needed
                                                transformations(CircleCropTransformation())
                                            }
                                        )
                                        */
                                        Column(){

                                            Row(){
                                                //Facebook
                                                Button(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(top = 1.dp, /*start = 40.dp, end = 40.dp*/),
                                                    onClick = {
                                                        val intent = Intent(Intent.ACTION_SEND)
                                                        intent.type = "image/jpg"
                                                        val appPackage = "com.facebook.katana"
                                                        val nombre = "XD"
                                                        intent.putExtra(Intent.EXTRA_TITLE, "Has seleccionado un $nombre")
                                                        intent.putExtra(Intent.EXTRA_TEXT, imageUri)
                                                        launcherImage.launch(intent)
                                                        intent.setPackage(appPackage)
                                                        if(intent.resolveActivity(context.packageManager) != null){
                                                            launcherImage.launch(intent)
                                                        }
                                                    },

                                                    ){
                                                    Text(
                                                        "Compartir en Facebook",
                                                    )
                                                }

                                            }
                                        }
                                    },
                                    confirmButton = {

                                    },
                                    dismissButton = {

                                    }
                                )
                            }
                            NavHost(navController, startDestination = "login") {
                                composable(route = "splash") {
                                    SplashScreen {
                                        navController.navigate("login")
                                    }
                                }
                                composable(route = "home") {
                                    Log.d("HOME", "home screen")
                                    HomeScreen(navController, homeScrennViewModel)
                                }
                                composable(route = "pokemon") {
                                    Log.d("POKEMON", "pokemons screen")
                                    PokemonScreen(navController)
                                }
                                composable(route = "reset") {
                                    Log.d("ROUTER", "reset")
                                    ResetPasswordScreen(resetPasswordViewModel,navController)
                                }
                                composable(route = "profile") {
                                    Log.d("ROUTER", "profile")
                                    ProfileScreen(navController, profileScrennViewModel)
                                }
                                composable(route = "routine_detail") {
                                    Log.d("ROUTER", "routine_detail")
                                    RoutineDetailScreen(navController, routineScreenViewModel)
                                }
                                composable(route = "register") {
                                    RegisterScreen(registerViewModel, navController)
                                }
                                composable(route = "routine?user_id={user_id}&member_id={member_id}", arguments = listOf(
                                    navArgument("user_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    },
                                    navArgument("member_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                ), content = { entry ->
                                    val memberId = entry.arguments?.getInt("member_id")!!
                                    userId = entry.arguments?.getInt("user_id")!!
                                    routineScreenViewModel.memberId = memberId
                                    routineScreenViewModel.userId = userId
                                    routineScreenViewModel.fetchBodyPartsExercises()
                                    routineScreenViewModel.fetchBodyParts()
                                    routineScreenViewModel.fetchExercieses()
                                    RoutineScreen(routineScreenViewModel, navController)
                                })
                                composable(route = "pokemon/edit?pokemon_id={pokemon_id}", arguments = listOf(
                                    navArgument("pokemon_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                ), content = { entry ->
                                    val pokemonId = entry.arguments?.getInt("pokemon_id")!!
                                    pokemonDetailScrennViewModel.pokemonId = pokemonId
                                    PokemonDetailScreen(navController, pokemonDetailScrennViewModel)
                                })
                                composable(route = "login") {
                                    Log.d("ROUTER", "login")
                                    val dataStore = UserStorage(applicationContext)
                                    if(dataStore.getUserId.collectAsState(initial = 9999).value != 0){
                                        println(dataStore.getUserId.collectAsState(initial = 0).value)
                                        val userId = dataStore.getUserId.collectAsState(initial = 0).value
                                        val memberId = dataStore.getMemberId.collectAsState(initial = 0).value
                                        Log.d("ROUTER", dataStore.getUserId.collectAsState(initial = 0).value.toString())
                                        if (userId != null && memberId != null){
                                            routineScreenViewModel.memberId = memberId
                                            routineScreenViewModel.userId = userId
                                            routineScreenViewModel.fetchBodyPartsExercises()
                                            routineScreenViewModel.fetchBodyParts()
                                            routineScreenViewModel.fetchExercieses()
                                            RoutineScreen(routineScreenViewModel, navController)
                                        }
                                    }else{
                                        LoginScreen(loginScrennViewModel, navController)
                                    }
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}
