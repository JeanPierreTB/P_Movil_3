package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import pe.edu.ulima.pm20232.aulavirtual.models.Generation
import pe.edu.ulima.pm20232.aulavirtual.models.Member
import pe.edu.ulima.pm20232.aulavirtual.services.MemberService

class MemberScreenViewModel: ViewModel() {
    private val memberService2 = BackendClient.buildService(MemberService::class.java)
    private val coroutine: CoroutineScope = viewModelScope
    private val _membersLiveData = MutableLiveData<Map<Int, String>>()
    val membersLiveData: LiveData<Map<Int, String>> get() = _membersLiveData

    // Esta función ahora devuelve un Map<Int, String> en lugar de Unit
    suspend fun fetchAllMemberTeam(memberId: Int): Map<Int, String> {
        return withContext(Dispatchers.IO) {
            val response = memberService2.memberTeam(memberId).execute()
            if (response.isSuccessful) {
                val list: List<Member> = response.body() ?: emptyList()
                val membersMap = mutableMapOf<Int, String>()

                for (g: Member in list) {
                    val memberId = g.id
                    val memberName = g.names
                    val memberLastName = g.lastNames
                    val memberCode = g.code
                    membersMap[memberId] = "Nombre: $memberName $memberLastName Codigo: $memberCode"
                }

                // Actualizar LiveData con el nuevo mapa
                _membersLiveData.postValue(membersMap)

                // Devolver el mapa para que pueda ser utilizado por la actividad si es necesario
                membersMap
            } else {
                // Manejar errores
                emptyMap()
            }
        }
    }
    /*
    //Lo creamos
    val MembersMap = mutableMapOf<Int, String>()
    fun fetchAllMemberTeam(memberId: Int){
        //Con coroutine se puede realizar la ejecucion en un nuevo hilo.
        coroutine.launch {
            try{
                //Con with se maneja peticion sincrona
                withContext(Dispatchers.IO) {
                    //Aqui memberTeam(memberId), nos bota el miembro segun su ID
                    val response = memberService2.memberTeam(memberId).execute()
                    if (response.isSuccessful) {
                        val list: List<Member> = response.body()!!
                        for(g: Member in list){
                            val MemberId = g.id
                            val MemberName = g.names
                            val MemberLastName = g.lastNames
                            val MemberCode = g.code
                            MembersMap[MemberId] =
                                "Nombre: "+ MemberName + " " + MemberLastName + " Codigo: "+MemberCode
                        }
                    } else {
                        // Maneja errores
                    }
                }
            } catch (e: Exception){

            }finally {

            }
        }
    }
     */
}