package com.krazylemon.verdetech102.pages.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginModelView: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)


    fun singIn(email: String, password: String, home: () -> Unit)
    =viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        home()
                    }else{
                        Log.d("VerdeTech", "Login with firebase: ${task.result.toString()}")
                    }
                }
        }catch (ex:Exception){
            Log.d("VerdeTech", "Login with firebase: ${ex.message}")
        }
    }

    fun createUser(email: String,password: String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        home()
                    }else{
                        Log.d("VerdeTech", "Failed register User due to: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }
    private fun createUser(displayName: String?){
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("VerdeTech","Creado ${it.id}")
            }.addOnFailureListener{
                Log.d("VerdeTech","Ocurrio un Error:  ${it}")
            }
    }
}