package com.krazylemon.verdetech102

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.krazylemon.verdetech102.api.Constantes
import com.krazylemon.verdetech102.api.DhtModel
import com.krazylemon.verdetech102.api.NetworkResponse
import com.krazylemon.verdetech102.api.RetrofitInstance
import com.krazylemon.verdetech102.chat.MessageModel
import kotlinx.coroutines.launch

class ApiViewModel :ViewModel() {

    /** DHT MODEL **/
    private val DhtApi = RetrofitInstance.dhtApi
    private val _DhtResult = MutableLiveData<NetworkResponse<DhtModel>>()
    val DhtResult : LiveData<NetworkResponse<DhtModel>> = _DhtResult

    /**  CHAT BOT **/
    private val generativeModel : GenerativeModel = GenerativeModel(
        "gemini-1.5-pro",
        // see https://github.com/google/secrets-gradle-plugin for further instructions
        Constantes.API_KEY,
        generationConfig = generationConfig {
            temperature = 0.5f
            topK = 64
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"},
        systemInstruction = content {
            text("Eres un chat bot llamado Plant Bot de mi proyecto llamado Verde Tech" +
                    " este es un prototipo hecho con ESP32 que gestiona el riego y toma " +
                    "medidas sobre temperatura y humedad de mi sistema; tu funcion es responder " +
                    "al usuario sobre preguntas relacionadas con el cuidado de los cultivos, plantas " +
                    "y ambiente especificamente cualquier otra pregunta no la podras contestar; " +
                    "el unico idioma de entrada y salida es el Español"
            ) },
        )

    val messageList by lazy{
        mutableStateListOf<MessageModel>()
    }

    fun getData(limit: Int){
        _DhtResult.value = NetworkResponse.Loading
        viewModelScope.launch {
           try {
               val response =  DhtApi.getDht(limit)
               if(response.isSuccessful){
                //Log.i("Response: ",response.body().toString())
                   response.body()?.let{
                       _DhtResult.value = NetworkResponse.Success(it)
                   }
               }else{
                   //Log.i("Error: ",response.body().toString())
                   _DhtResult.value = NetworkResponse.Error("Fallo al cargar la Info")
               }
           } catch (e: Exception) {
               _DhtResult.value = NetworkResponse.Error("Fallo al cargar la Info E:${e.message}")
           }
        }
    }
    /** ChatBot MODEL **/

    fun sendMessage(soli : String){
        viewModelScope.launch {
            try{
                val chat  = generativeModel.startChat(
                    history = messageList.map{
                        content(it.role){
                            text(it.message)
                        }
                    }.toList()
                )
                messageList.add(MessageModel(soli,"user"))
                messageList.add(MessageModel("Escribiendo...","model"))
                val response = chat.sendMessage(soli)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(),"model"))
            }catch (e: Exception){
                messageList.removeLast()
                messageList.add(MessageModel("Error: "+e.message.toString(),"model"))
            }
        }
    }
}