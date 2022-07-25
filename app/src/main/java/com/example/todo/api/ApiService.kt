package com.example.todo.api

import com.example.todo.model.Categoria
import com.example.todo.model.Tarefa
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("categoria")
    suspend fun listCategoria(): Response<List<Categoria>>

    @POST("tarefa")
    suspend fun addTarefa(
        @Body tarefa: Tarefa
    ): Response<Tarefa>

    @GET("tarefa")
    suspend fun listTarefas(): Response<List<Tarefa>>

    @PUT("tarefa")
    suspend fun updateTarefa(
        @Body tarefas: Tarefa
    ): Response<Tarefa>

    @DELETE("tarefa/{id}")
    suspend fun deleteTarefa(
        @Path("id") valor: Long
    ): Response<Tarefa>
}