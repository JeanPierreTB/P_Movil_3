package pe.edu.ulima.pm20232.aulavirtual.models

import com.google.gson.annotations.SerializedName

data class Member (
    //Cada vez que se escriba distinto a lo que te bota el API (URL). poner el @
    val id: Int,
    @SerializedName("user")
    val code: Int,
    val dni: String,
    val names: String,
    @SerializedName("last_names")
    val lastNames: String,
    val email: String,
    val phone: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("level_id")
    val levelId: Int
)