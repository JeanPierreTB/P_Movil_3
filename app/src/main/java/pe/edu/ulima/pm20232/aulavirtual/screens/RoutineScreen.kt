package pe.edu.ulima.pm20232.aulavirtual.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RoutineScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.services.ExerciseMemberService
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.Gray1200

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoutineGrid(navController: NavController, model: RoutineScreenViewModel){
    var intValue by remember { mutableStateOf(0) }
    val exercises by model.exercises.collectAsState()
    var ShowDetailExercise by remember { mutableStateOf(false) }
    val exercisemembers: ExerciseMemberService = ExerciseMemberService()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ) {
        val filteredExercises = exercises.filter { exercise ->
            model.filtrar==false || exercisemembers.findMembersbyExerciseid(exercise.id).contains(model.userId)
        }
        items(filteredExercises.size) { i ->
            Column(){
                println(exercises[i].imageUrl)
                Image(
                    painter = rememberImagePainter(data = exercises[i].imageUrl),
                    contentDescription = exercises[i].name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 10.dp)
                        .clickable {
                            intValue = exercises[i].id.toInt() - exercises[0].id.toInt()

                            ShowDetailExercise = true

                        },
                )
                Text(exercises[i].name)
            }
        }

    }
    if (ShowDetailExercise) {

        AlertDialog(
            onDismissRequest = {
                ShowDetailExercise = false
            },

            title = {
                Text(
                    text = exercises[intValue].name,
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            },
            text={
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(Color.White, shape = RectangleShape)
                    .padding(16.dp)){

                    Text(
                        text = "exercise id : ${exercises[intValue].description}",
                    )
                    Row(){
                        YoutubePlayer(youtubeVideoId = exercises[intValue].videoUrl.split("watch?v=")[1], lifecycleOwner = LocalLifecycleOwner.current)
                    }
                }
            },


            confirmButton = {

            },
            dismissButton = {

            }
        )
    }

}

@Composable
fun SelectOpitionsRoutine(model: RoutineScreenViewModel) {
    var expanded by remember { mutableStateOf(false) }
    // val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(
        Modifier.padding(bottom = 20.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = {Text("Lista de Partes del Cuerpo")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledLabelColor = Gray1200, // Change the label color when disabled
                disabledBorderColor = Gray1200, // Change the border color when disabled
                disabledTextColor = Gray1200
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            DropdownMenuItem(onClick = {
                model.fetchExercieses(0)
                selectedText = "VER TODO"
                expanded = false
            }) {
                Text(text = "VER TODO")
            }
            for ((key, value) in model.bodyPartMap) {
                DropdownMenuItem(onClick = {
                    model.fetchExercieses(key)
                    selectedText = value
                    expanded = false
                }) {
                    Text(text = value)
                }
            }
        }
    }
}

@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        })

}
@Composable
fun RoutineScreen(viewModel: RoutineScreenViewModel, navController: NavHostController) {

    //viewModel.fetchBodyPartsExercises()
    //viewModel.fetchExercieses()

    Column {
        Text("userId: ${viewModel.userId}")
        Text("memberId: ${viewModel.memberId}")
        Text("Ejercicios Asignados: ${viewModel.exercisesCount}")
        Text("Partes del Cuerpo Entrenadas: ${viewModel.bodyPartsCount}")
        SelectOpitionsRoutine(viewModel)
        RoutineGrid(navController, viewModel)
    }
}

