package pe.edu.ulima.pm20232.aulavirtual.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import pe.edu.ulima.pm20232.aulavirtual.configs.BASE_URL
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RoutineScreenViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RoutineDetailScreen(navController: NavController, viewModel: RoutineScreenViewModel){
    Column(){
        Text("id : ${viewModel.exercise.value.id}")
        Text("nombre : ${viewModel.exercise.value.name}")
        Text("descripción : ${viewModel.exercise.value.description}")
        Text("imagen : ${viewModel.exercise.value.imageUrl}")
        Text("video : ${viewModel.exercise.value.videoUrl}")
        Text("series : ${viewModel.exercise.value.sets}")
        Text("repeticiones : ${viewModel.exercise.value.reps}")

        val uri = Uri.parse(BASE_URL + viewModel.exercise.value.imageUrl)
        val painter = rememberImagePainter(
            data = uri.scheme + "://" + uri.host + uri.path + (if (uri.query != null) uri.query else ""),
            builder = {
                // You can apply transformations here if needed
                transformations(CircleCropTransformation())
            }
        )
        Image(
            painter = painter,
            contentDescription = null, // Set a proper content description if required
            modifier = Modifier.size(200.dp, 200.dp)
        )
    }
}