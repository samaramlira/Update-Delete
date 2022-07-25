package com.example.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Categoria
import com.example.todo.model.Tarefa
import com.example.todo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var tarefaSelecionada: Tarefa? = null

    private val _responseListCategoria =
        MutableLiveData<Response<List<Categoria>>>()

    val responseListCategoria: LiveData<Response<List<Categoria>>> =
        _responseListCategoria

    private val _myTarefaResponse =
        MutableLiveData<Response<List<Tarefa>>>()

    val myTarefaResponse: LiveData<Response<List<Tarefa>>> =
        _myTarefaResponse

    val dataSelecionada = MutableLiveData<LocalDate>()

    init {
        dataSelecionada.value = LocalDate.now()

        listCategoria()
    }

    init {
        listCategoria()
    }

    fun listCategoria(){

        viewModelScope.launch {
            try{
                val response = repository.listCategoria()
                _responseListCategoria.value = response
            }catch (e: Exception){
                Log.d("ErroRequisicao", e.message.toString())
            }
        }
    }

    fun addTarefa(tarefa: Tarefa){
        viewModelScope.launch {
            try {
                repository.addTarefa(tarefa)
                listTarefas()
            } catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }
    fun listTarefas(){
        viewModelScope.launch {
            try {
                val response = repository.listTarefas()
                _myTarefaResponse.value = response
            } catch (e: Exception) {
                Log.e("Developer", "Error", e)
            }
        }
    }
    fun updateTarefa(tarefa: Tarefa){
        viewModelScope.launch {
            try{
                repository.updateTarefa(tarefa)
                listTarefas()
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }
    fun deleteTarefa(id: Long){
        viewModelScope.launch {
            try {
                repository.deleteTarefa(id)
                listTarefas()
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }
}