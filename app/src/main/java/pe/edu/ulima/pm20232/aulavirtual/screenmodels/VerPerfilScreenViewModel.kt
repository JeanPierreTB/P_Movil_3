package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import pe.edu.ulima.pm20232.aulavirtual.models.BodyPart
import pe.edu.ulima.pm20232.aulavirtual.models.Member
import pe.edu.ulima.pm20232.aulavirtual.models.responses.BodyPartExercisesCount
import pe.edu.ulima.pm20232.aulavirtual.services.MemberService
import retrofit2.Response

class VerPerfilScreenViewModel(): ViewModel() {
    private val memberService = BackendClient.buildService(MemberService::class.java)
    private val coroutine: CoroutineScope = viewModelScope

    var userId: Int by mutableStateOf(0)
    var Ccode: Int by mutableStateOf(0)
    var Cdni: String by mutableStateOf("")
    var Cnames: String by mutableStateOf("")
    var ClastNames: String by mutableStateOf("")
    var Cemail: String by mutableStateOf("")
    var Cphone: String by mutableStateOf("")
    var CimageUrl: String by mutableStateOf("")
    var ClevelId: Int by mutableStateOf(0)

    fun fetchValidate(){
        coroutine.launch {
            Log.d("XFGG", "Q PASO")
            try {
                Log.d("XFGG", "LOLO1")
                withContext(Dispatchers.IO) {

                    Log.d("XFGG", "LOLO2")


                    Log.d("XFGG", "LOLO22")
                    val response = memberService.profile(userId).execute()

                    Log.d("XFGG", "LOLO3")
                    if (response.isSuccessful) {
                        Log.d("XFGG", "LOLO4")
                        val response: Member = response.body()!!
                        Ccode= response.code
                        Log.d("XFGG", "LOLO5")
                        Cdni= response.dni
                        Cnames= response.names
                        ClastNames= response.lastNames
                        Cemail= response.email
                        Cphone= response.phone
                        CimageUrl= response.imageUrl
                        ClevelId= response.levelId



                    }else{
                        println("hakuna")

                    }


                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }
}