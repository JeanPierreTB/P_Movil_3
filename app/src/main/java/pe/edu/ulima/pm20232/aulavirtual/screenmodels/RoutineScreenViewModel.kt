package pe.edu.ulima.pm20232.aulavirtual.screenmodels

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
import pe.edu.ulima.pm20232.aulavirtual.models.responses.BodyPartExercisesCount
import pe.edu.ulima.pm20232.aulavirtual.models.responses.ExerciseSetReps
import pe.edu.ulima.pm20232.aulavirtual.services.*
import retrofit2.Response

class RoutineScreenViewModel(): ViewModel(){
    private val memberService = BackendClient.buildService(MemberService::class.java)
    private val coroutine: CoroutineScope = viewModelScope

    var userId: Int by mutableStateOf(0)
    var memberId: Int by mutableStateOf(0)
    var bodyPartsCount: Int by mutableStateOf(0)
    var exercisesCount: Int by mutableStateOf(0)

    val bodyPartMap = mutableMapOf<Int, String>()
    val bodyPartFlow = MutableStateFlow(bodyPartMap.toMap())
    private var _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises
    fun setExercises(newItems: List<Exercise>) {
        _exercises.value = newItems
    }

    private var _exercise = MutableStateFlow<ExerciseSetReps>(ExerciseSetReps(0, "", "", "", "", 0, 0))
    val exercise: StateFlow<ExerciseSetReps> get() = _exercise
    fun setExercise(e: ExerciseSetReps) {
        _exercise.value = e
    }

    fun fetchBodyPartsExercises(){
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = memberService.exercisesBodyParts(memberId).execute()
                    if (response.isSuccessful) {
                        val response: BodyPartExercisesCount = response.body()!!
                        bodyPartsCount = response.bodyParts
                        exercisesCount = response.exercises
                    } else {
                        // Maneja errores
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun fetchBodyParts(){
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = memberService.bodyParts(memberId).execute()
                    if (response.isSuccessful) {
                        val list: List<BodyPart> = response.body()!!
                        for(i: BodyPart in list){
                            bodyPartMap[i.id] = i.name
                        }
                    } else {
                        // Maneja errores
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun fetchExercieses(bodyPartId: Int? = null){
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response: Response<List<Exercise>>
                    if(bodyPartId == null || bodyPartId == 0){
                        response = memberService.exercises(memberId).execute()
                    }else{
                        response = memberService.exercises(memberId, bodyPartId).execute()
                    }
                    if (response.isSuccessful) {
                        val list: List<Exercise> = response.body()!!
                        setExercises(list)
                    } else {
                        // Maneja errores
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun fetchExercise(exerciseId: Int){
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = memberService.exercise(memberId, exerciseId).execute()
                    if (response.isSuccessful) {
                        var exercise = response.body()!!
                        println(exercise.toString())
                        setExercise(exercise)
                    } else {
                        // Maneja errores
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }
}