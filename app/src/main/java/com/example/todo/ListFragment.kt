package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.TarefaAdapter
import com.example.todo.adapter.TaskItemclickListener
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), TaskItemclickListener {

    private lateinit var binding: FragmentListBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel.listTarefas()

        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        val adapter = TarefaAdapter(this, mainViewModel, context)

        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)

        binding.floatingAdd.setOnClickListener {
            mainViewModel.tarefaSelecionada = null
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }
        mainViewModel.myTarefaResponse.observe(viewLifecycleOwner, {
                response -> if(response != null){
            adapter.setLista(response.body()!!)
        }
        })
        return binding.root

    }

    override fun onTaskClicked(tarefa: Tarefa) {
        mainViewModel.tarefaSelecionada = tarefa
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
    }
}

