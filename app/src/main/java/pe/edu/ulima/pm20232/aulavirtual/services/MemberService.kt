package pe.edu.ulima.pm20232.aulavirtual.services

import pe.edu.ulima.pm20232.aulavirtual.models.BodyPart
import pe.edu.ulima.pm20232.aulavirtual.models.Exercise
import pe.edu.ulima.pm20232.aulavirtual.models.Member
import pe.edu.ulima.pm20232.aulavirtual.models.responses.BodyPartExercisesCount
import pe.edu.ulima.pm20232.aulavirtual.models.responses.ExerciseSetReps
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberService {
    @GET("member/exercises_body_parts") // Reemplaza con la URL de tu punto final
    fun exercisesBodyParts(
        @Query("member_id") memberId: Int? = null,
    ): Call<BodyPartExercisesCount>


    @GET("member/body_parts") // Reemplaza con la URL de tu punto final
    fun bodyParts(
        @Query("member_id") memberId: Int? = null,
    ): Call<List<BodyPart>>

    @GET("/body_part/list") // Reemplaza con la URL de tu punto final
    fun AllbodyParts(
    ): Call<List<BodyPart>>

    @GET("member/exercises") // Reemplaza con la URL de tu punto final
    fun exercises(
        @Query("member_id") memberId: Int? = null,
        @Query("body_part_id") bodyPartId: Int? = null,
    ): Call<List<Exercise>>

    @GET("exercise/list") // Reemplaza con la URL de tu punto final
    fun AllExercises(
        @Query("body_part_id") bodyPartId: Int? = null,
        ): Call<List<Exercise>>
    @GET("member/exercise") // Reemplaza con la URL de tu punto final
    fun exercise(
        @Query("member_id") memberId: Int? = null,
        @Query("exercise_id") exerciseId: Int? = null,
    ): Call<ExerciseSetReps>

    //Yo le agregue esto
    @GET("member/profile") // Reemplaza con la URL de tu punto final
    fun memberTeam(
        @Query("user_id") memberId: Int? = null,
        //Con el Call<List<Member>> podemos crear una lista Member
    ): Call<List<Member>>

}