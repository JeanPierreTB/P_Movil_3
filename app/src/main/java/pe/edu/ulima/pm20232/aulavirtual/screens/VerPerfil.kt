package pe.edu.ulima.pm20232.aulavirtual.screens

import android.util.Log
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import pe.edu.ulima.pm20232.aulavirtual.models.Member
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.HomeScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RoutineScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.VerPerfilScreenViewModel
import pe.edu.ulima.pm20232.aulavirtual.services.MemberServices
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.Orange400
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.White400
val imageUrl = "https://e.rpp-noticias.io/xlarge/2021/11/02/140114_1168254.jpg"
val IconPerson = Icons.Default.Person//IMPORTANTE no BORRAR el icono de la persona

val IconPhone = Icons.Default.Phone
val IconEmail = Icons.Default.Email
private val memberService = MemberServices()







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
@Composable
fun Informacion(navController: NavHostController, param1:Int, model: VerPerfilScreenViewModel) {

    var memberWithId1 = memberService.memberList.find { it.id == param1 }
    Box(
        // caja gris (light)
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else White400), // cambia color por estado
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            //verticalArrangement = Arrangement.Center, --> centrado vertical
            //horizontalAlignment = Alignment.CenterHorizontally --> centrado horizontal
            horizontalAlignment = Alignment.Start // Alineación a la izquierda

        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Column() {
                    IconButton(
                        onClick = {
                            navController.navigate("home?user_id=${param1}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (isSystemInDarkTheme()) White400 else Color.Black
                        )

                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 260.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (isSystemInDarkTheme()) White400 else Color.Black
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                ImageView(url = imageUrl, width = 100, height = 100)
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    if (memberWithId1 != null) {
                        Text(
                            text = "${memberWithId1.names}",
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        // Alineación vertical al centro
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Row() {
                                Icon(
                                    imageVector = IconPerson,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isSystemInDarkTheme()) White400 else Color.Black
                                )
                                if (memberWithId1 != null) {
                                    Text(
                                        text = "${memberWithId1.code}",
                                        color = if (isSystemInDarkTheme()) Color.Gray else Color.Black,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            Text(
                                "Crossfitero",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                    }

                }
            }

            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = IconPhone,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp),
                    tint = if (isSystemInDarkTheme()) White400 else Color.Black
                )
                if (memberWithId1 != null) {
                    Text(
                        text = "${memberWithId1.phone}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = IconEmail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp),
                    tint = if (isSystemInDarkTheme()) White400 else Color.Black,
                )
                if (memberWithId1 != null) {
                    Text(
                        text = "${memberWithId1.email}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))//espacio vertical de 20.dp
            ButtonWithIcon2(
                text = "Actualizar datos",
                onClick = { /* Tu función de clic aquí */ })

            Spacer(modifier = Modifier.height(40.dp)) //Espacio para los datos

            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
            ) {
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 65.dp)
                    ) {
                        Text(
                            "22",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        ) //Los numeros deben ser más grandes y gruesos
                    }
                    Row(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            "Ejercicios Asignados",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 85.dp)
                    ) {
                        Text(
                            "4",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            "Partes del cuerpo entrenadas",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 16.sp,

                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(120.dp)) //Espaciado grande antes de botón

            Row(
            ) {
                ButtonWithIcon2(
                    text = "Cerrar Sesión",
                    onClick = { })

            }
        }


    }}
    /*Box(
        // caja gris (light)
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else White400), // cambia color por estado
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start // Alineación a la izquierda

        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Column() {
                    IconButton(
                        onClick = {
                            navController.navigate("home")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (isSystemInDarkTheme()) White400 else Color.Black
                        )

                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 260.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (isSystemInDarkTheme()) White400 else Color.Black
                    )
                }
            }//}}}}}}}}
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                ImageView(url = imageUrl, width = 100, height = 100)
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    if (memberWithId1 != null) {
                        Text(
                            text = "${model.Cnames}",
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        // Alineación vertical al centro
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Row() {
                                Icon(
                                    imageVector = IconPerson,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isSystemInDarkTheme()) White400 else Color.Black
                                )

                                    Text(
                                        text = "${model.Ccode}",
                                        color = if (isSystemInDarkTheme()) Color.Gray else Color.Black,
                                        fontSize = 14.sp
                                    )

                            }
                            Text(
                                "Crossfitero",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                    }

                }
            }

            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = IconPhone,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp),
                    tint = if (isSystemInDarkTheme()) White400 else Color.Black
                )

                    Text(
                        text = "${model.Cphone}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

            }
            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = IconEmail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp),
                    tint = if (isSystemInDarkTheme()) White400 else Color.Black,
                )

                    Text(
                        text = "${model.Cemail}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

            }

            Spacer(modifier = Modifier.height(20.dp))//espacio vertical de 20.dp
            ButtonWithIcon2(
                text = "Actualizar datos",
                onClick = { /* Tu función de clic aquí */ })

            Spacer(modifier = Modifier.height(40.dp)) //Espacio para los datos

            Row(
                // Espacio a la izquierda del texto para que...
                // los siguientes elementos esten a la misma altura horizontal de la imagen
            ) {
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 65.dp)
                    ) {
                        Text(
                            "22",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        ) //Los numeros deben ser más grandes y gruesos
                    }
                    Row(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            "Ejercicios Asignados",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Column(
                    // Espacio a la izquierda del texto para que...
                    // los siguientes elementos esten a la misma altura horizontal de la imagen
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 85.dp)
                    ) {
                        Text(
                            "4",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            "Partes del cuerpo entrenadas",
                            color = if (isSystemInDarkTheme()) White400 else Color.Black, // cambia color por estado
                            fontSize = 16.sp,

                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(120.dp)) //Espaciado grande antes de botón

            Row(
            ) {
                ButtonWithIcon2(
                    text = "Cerrar Sesión",
                    onClick = { /* Tu función de clic aquí */ })

            }
        }


    }*/

@Composable
fun VerPerfil(navController: NavHostController,param1:Int, viewModel: VerPerfilScreenViewModel){
    Log.d("XFGG", "VerPerfil1")
    viewModel.userId = param1
    Log.d("XFGG", "VerPerfil2")
    viewModel.fetchValidate()
    Log.d("XFGG", "VerPerfil3")
    Informacion(navController, param1, viewModel)

}
