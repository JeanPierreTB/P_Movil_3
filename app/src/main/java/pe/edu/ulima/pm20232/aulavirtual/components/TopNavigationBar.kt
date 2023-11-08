package pe.edu.ulima.pm20232.aulavirtual.components

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.edu.ulima.pm20232.aulavirtual.configs.TopBarScreen
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage

@Composable
fun TopNavigationBar(
    navController: NavController,
    screens: List<TopBarScreen>,
    activity: ComponentActivity,
    dataStore: UserStorage
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = "ULima GYM") },
        /*navigationIcon = {
            IconButton(
                onClick = {
                    // Handle navigation icon click (e.g., open drawer or navigate back)
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },*/
        actions = {
            IconButton(
                onClick = {
                    isMenuExpanded = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                screens.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            // Handle menu item click
                            if(item.route != "sign_out"){
                                isMenuExpanded = false
                                navController.navigate(item.route)
                            }else{
                                val d = Log.d("TOP_NAVIGATION_BAR", "cerrar sesión")
                                scope.launch {
                                    dataStore.clearAll()
                                    finishAffinity(activity)
                                }
                            }
                        }
                    ) {
                        Text(text = item.title)
                    }
                }
            }
        },
        // modifier = Modifier.fillMaxSize()
    )
}
