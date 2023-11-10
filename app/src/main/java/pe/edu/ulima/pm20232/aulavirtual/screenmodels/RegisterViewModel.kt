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
import pe.edu.ulima.pm20232.aulavirtual.services.RegisterService2
import pe.edu.ulima.pm20232.aulavirtual.services.ResetPassword2
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import java.util.concurrent.Flow


class RegisterViewModel(private val context: Context) : ViewModel() {
    var Nombre:String by mutableStateOf("")
    var Apellidos:String by mutableStateOf("")
    var DNI:String by mutableStateOf("")
    var Correo:String by mutableStateOf("")
    var Telefono:String by mutableStateOf("")
    var Contraseña:String by mutableStateOf("")
    var Repetir:String by mutableStateOf("")
    var message:String by mutableStateOf("")

    private val registerService = BackendClient.buildService(RegisterService2::class.java)
    private val coroutine: CoroutineScope = viewModelScope

    fun access(navController: NavController): Unit {
        println("BTN PRESSED")
        println(DNI)
        println(Correo)
        println("BTN PRESSED")
        println(DNI)
        println(Correo)

        if (Nombre.isEmpty() || Apellidos.isEmpty() || DNI.isEmpty() ||
            Correo.isEmpty() || Telefono.isEmpty() || Contraseña.isEmpty() || Repetir.isEmpty()) {
            message = "Falta completar algunos datos"
            return
        }

        if(Contraseña!=Repetir){
            message="Contraseñas no coinciden"
            return
        }



            coroutine.launch {

                try {
                    withContext(Dispatchers.IO) {
                        println("Dni"+DNI)
                        println("Correo"+Correo)
                        val response = registerService.findOne(DNI, Correo)?.execute()

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
        message=""
        DNI=""
        Correo=""
        Nombre=""
        Apellidos=""
        Telefono=""
        Contraseña=""
        Repetir=""





    }
}
