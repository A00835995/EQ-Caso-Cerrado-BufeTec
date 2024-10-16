import androidx.lifecycle.ViewModel
import com.example.reto.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Log

class UserViewModel : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    private val _userRelation = MutableStateFlow("")
    val userRelation = _userRelation.asStateFlow()
    private val _userState = MutableStateFlow("")
    val userState = _userState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    fun fetchUserData() {
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            usersRef.child(uid).get().addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
                user?.let { userInfo ->
                    _userName.update { userInfo.nombre ?: "" }
                    _userEmail.update { userInfo.correo ?: "" }
                    _userRelation.update { userInfo.relacion ?: "" }
                    _userState.update { userInfo.estado ?: "" }

                    // Agregar log para verificar que se están obteniendo los datos
                    Log.d("UserViewModel", "Usuario autenticado: ${userInfo.nombre}, Rol: ${userInfo.relacion}")
                }
            }.addOnFailureListener { exception ->
                Log.e("UserViewModel", "Error al obtener la información del usuario", exception)
            }
        }
    }
}
