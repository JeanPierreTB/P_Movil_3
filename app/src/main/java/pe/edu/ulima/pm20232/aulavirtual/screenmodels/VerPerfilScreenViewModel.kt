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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import pe.edu.ulima.pm20232.aulavirtual.models.BodyPart
import pe.edu.ulima.pm20232.aulavirtual.models.Exercise
import pe.edu.ulima.pm20232.aulavirtual.models.Member
import pe.edu.ulima.pm20232.aulavirtual.models.responses.BodyPartExercisesCount
import pe.edu.ulima.pm20232.aulavirtual.models.responses.ExerciseSetReps
import pe.edu.ulima.pm20232.aulavirtual.services.MemberService
import retrofit2.Response

class VerPerfilScreenViewModel(): ViewModel() {
    private val memberService = BackendClient.buildService(MemberService::class.java)
    private val coroutine: CoroutineScope = viewModelScope

    private var _exercise = MutableStateFlow<Member>(Member(0, 0, "", "", "", "", "", "", 0))
    val exercise: StateFlow<Member> get() = _exercise
    var Ccode: Int by mutableStateOf(0)
    var Cdni: String by mutableStateOf("")
    var Cnames: String by mutableStateOf("")
    var ClastNames: String by mutableStateOf("")
    var Cemail: String by mutableStateOf("")
    var Cphone: String by mutableStateOf("")
    var CimageUrl: String by mutableStateOf("")
    var ClevelId: Int by mutableStateOf(0)

    fun setExercise(e: Member) {
        _exercise.value = e
    }


    fun fetchValidate(userId: Int){
        coroutine.launch {
            Log.d("XFGG", "Q PASO")
            try {

                withContext(Dispatchers.IO) {

                    Log.d("XFGG", "LOLO2")
                    val response = memberService.profile(userId).execute()

                    Log.d("XFGG", response.toString())
                    if (response.isSuccessful) {
                        Log.d("XFGG", "LOLO4")
                        val response: Member = response.body()!!
                        println(response.toString())
                        Log.d("wd", "${response.code.toString()}")
                        val Ccode= "${response.code.toInt()}"

                        setExercise(response)

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