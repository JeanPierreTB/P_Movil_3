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
import pe.edu.ulima.pm20232.aulavirtual.services.UserService2
import org.json.JSONObject
import pe.edu.ulima.pm20232.aulavirtual.configs.HttpStdResponse
import pe.edu.ulima.pm20232.aulavirtual.services.ResetPassword2
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import java.util.concurrent.Flow


class ResetPasswordViewModel(private val context: Context) : ViewModel() {
    var DNI: String by mutableStateOf("")
    var Correo: String by mutableStateOf("")
    var message: String by mutableStateOf("")

    private val resetpassword = BackendClient.buildService(ResetPassword2::class.java)
    private val coroutine: CoroutineScope = viewModelScope

    fun access(navController: NavController): Unit {
        println("BTN PRESSED")
        println(DNI)
        println(Correo)
        println("BTN PRESSED")
        println(DNI)
        println(Correo)
        coroutine.launch {

            try {
                withContext(Dispatchers.IO) {
                    println("Dni"+DNI)
                    println("Correo"+Correo)
                    val response = resetpassword.findOne(DNI, Correo)?.execute()

                    if (response != null) {
                        if (response.body()?.success == true) {
                            val responseData = response.body()
                            if (responseData != null) {

                                message = responseData.message

                                launch(Dispatchers.Main) {
                                    navController.navigate("login")
                                }

                            } else {
                                // Manejar el caso en que responseData sea nulo
                                println("responseData es nulo")
                            }
                        } else {
                            // Manejar el caso en que success no es true
                            message = response.body()?.message ?: "Error desconocido"
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
