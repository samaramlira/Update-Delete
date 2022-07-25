package com.example.todo.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.security.ConfirmationPrompt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.MainActivity
import com.example.todo.MainViewModel
import com.example.todo.R
import com.example.todo.model.Tarefa


class TarefaAdapter (
    private val taskItemclickListener: TaskItemclickListener,
    private val mainViewModel: MainViewModel,
    private val context: Context?
        ) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(){

    private var listTarefas = emptyList<Tarefa>()

    class TarefaViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val textNome = view.findViewById<TextView>(R.id.textNome)
        val textDescricao = view.findViewById<TextView>(R.id.textDescricao)
        val textResponsavel = view.findViewById<TextView>(R.id.textResponsavel)
        val textData = view.findViewById<TextView>(R.id.textData)
        val switchCardAtivo = view.findViewById<Switch>(R.id.switchCardAtivo)
        val textCategoria = view.findViewById<TextView>(R.id.textCategoria)
        val buttonDeletar = view.findViewById<Button>(R.id.buttonDeletar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {

        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_tarefa, parent, false)

        return TarefaViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {

        val tarefa = listTarefas[position]

        holder.textNome.text = tarefa.nome
        holder.textDescricao.text = tarefa.descricao
        holder.textResponsavel.text = tarefa.responsavel
        holder.textData.text = tarefa.data
        holder.switchCardAtivo.isChecked = tarefa.status
        holder.textCategoria.text = tarefa.categoria.descricao

        holder.itemView.setOnClickListener{
            taskItemclickListener.onTaskClicked(tarefa)
        }
        holder.buttonDeletar.setOnClickListener {
            deleteShow(tarefa.id)
        }
        holder.switchCardAtivo
            .setOnCheckedChangeListener { compoundButton, ativo ->
                tarefa.status = ativo
                mainViewModel.updateTarefa(tarefa)
            }
    }
    override fun getItemCount(): Int {
        return listTarefas.size
    }

    fun setLista(lista: List<Tarefa>){
        listTarefas = lista
        notifyDataSetChanged()
    }
    fun deleteShow(id: Long){

        val builder = AlertDialog.Builder(context)

        builder.setTitle("Excluir")
        builder.setMessage("Tem certeza que deseja excluir a tarefa?")
        builder.setPositiveButton("Sim") { dialogInterface: DialogInterface, i: Int ->
            mainViewModel.deleteTarefa(id)
        }
        builder.setNegativeButton("Não"){ dialogInterface: DialogInterface, i: Int ->
        }
        builder.show()
    }

}