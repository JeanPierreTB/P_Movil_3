package pe.edu.ulima.pm20232.aulavirtual.models

import com.google.gson.annotations.SerializedName

data class Member (
    val id: Int,
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