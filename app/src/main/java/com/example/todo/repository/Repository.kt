package com.example.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo.api.RetrofitInstance
import com.example.todo.model.Categoria
import com.example.todo.model.Tarefa
import retrofit2.Response

class Repository {

    suspend fun listCategoria(): Response<List<Categoria>>{
        return RetrofitInstance.api.listCategoria()
    }
    suspend fun addTarefa(tarefa: Tarefa): Response<Tarefa>{
        return RetrofitInstance.api.addTarefa(tarefa)
    }
    suspend fun listTarefas(): Response<List<Tarefa>>{
        return RetrofitInstance.api.listTarefas()
    }
    suspend fun updateTarefa(tarefa: Tarefa): Response<Tarefa>{
        return RetrofitInstance.api.updateTarefa(tarefa)
    }
    suspend fun deleteTarefa(id: Long): Response<Tarefa>{
        return RetrofitInstance.api.deleteTarefa(id)
    }
}