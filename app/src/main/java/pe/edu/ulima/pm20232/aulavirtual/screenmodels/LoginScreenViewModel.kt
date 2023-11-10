package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.content.Context
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import android.content.SharedPreferences
import kotlinx.coroutines.delay
import pe.edu.ulima.pm20232.aulavirtual.services.UserService2
import org.json.JSONObject
import pe.edu.ulima.pm20232.aulavirtual.configs.HttpStdResponse
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import java.util.concurrent.Flow


class LoginScreenViewModel(private val context: Context): ViewModel() {
    var user: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var message: String by mutableStateOf("")
    var bottomSheetCollapse: Boolean by mutableStateOf(true)
    var termsAndConditionsChecked: Boolean by mutableStateOf(false)

    private val userService = BackendClient.buildService(UserService2::class.java)
    private val coroutine: CoroutineScope = viewModelScope
    val dataStore = UserStorage(context)

    fun access(navController: NavController): Unit{
        println("BTN PRESSED")
        println(user)
        println(password)
        println("BTN PRESSED")
        println(user)
        println(password)
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = userService.findOne(user, password)?.execute()
                    if (response != null) {
                        if (response.body()!!.success == true) {
                            val responseData = response.body()!!
                            val jsonData = JSONObject(responseData.data)
                            val userId = jsonData.getInt("user_id")
                            val memberId = jsonData.getInt("member_id")

                            // localstorage
                            dataStore.saveUserId(userId)
                            dataStore.saveMemberId(memberId)
                            viewModelScope.launch {
                                message="Bienvenido"
                                delay(1000)

                            }

                            println("routine?user_id=${userId}&member_id=${memberId}")
                            launch(Dispatchers.Main) {
                                navController.navigate("routine?user_id=${userId}&member_id=${memberId}")
                                user=""
                                password=""
                                message=""
                            }

                        } else {
                            // Maneja errores

                            message = response.body()!!.message




                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }

        /*
        if(user == "admin" && password == "123"){
            navController.navigate("profile")
        }else{
            val userService: UserService = UserService()
            val userId = userService.checkUser(user, password)
            if(userId != 0){
                navController.navigate("home?user_id=${userId}")
            }else{
                message = "Usuario y contraseña no coinciden"
            }
        }
         */
    }
}