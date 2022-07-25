package com.example.todo.adapter

import com.example.todo.model.Tarefa

interface TaskItemclickListener {

    fun onTaskClicked(tarefa: Tarefa)
}