package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentFormBinding
import com.example.todo.fragment.DatePickerFragment
import com.example.todo.fragment.TimePickerListener
import com.example.todo.model.Categoria
import com.example.todo.model.Tarefa
import java.time.LocalDate

class FormFragment : Fragment(), TimePickerListener {

    private lateinit var binding: FragmentFormBinding
    private var categoriaSelecionada = 0L
    private var tarefaSelecionada: Tarefa? = null
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFormBinding.inflate(layoutInflater, container, false)

        carregarDados()

        mainViewModel.listCategoria()

        mainViewModel.responseListCategoria.observe(viewLifecycleOwner, { response ->
            Log.d("Requisicao", response.body().toString())
            spinnerCategoria(response.body())
        })
        mainViewModel.dataSelecionada.observe(viewLifecycleOwner, {
            selectedDate -> binding.editData.setText(selectedDate.toString())
        })

        binding.buttonSalvar.setOnClickListener {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }
        binding.editData.setOnClickListener{
            DatePickerFragment(this)
                .show(parentFragmentManager, "DatePicker")
        }
        binding.buttonSalvar.setOnClickListener {
            inserirNoBanco()
        }

        return binding.root
    }

     fun spinnerCategoria(categorias: List<Categoria>?){

         if(categorias != null){
             binding.spinnerCategoria.adapter = ArrayAdapter(
                 requireContext(),
                 com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                 categorias
             )

             binding.spinnerCategoria.onItemSelectedListener =
                 object : AdapterView.OnItemSelectedListener {
                     override fun onItemSelected(
                         p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long
                     ) {
                         val categoriaSelecionadaFun = binding
                             .spinnerCategoria.selectedItem as Categoria

                         categoriaSelecionada = categoriaSelecionadaFun.id
                     }

                     override fun onNothingSelected(p0: AdapterView<*>?) {
                         TODO("Not yet implemented")
                     }

                 }
         }
     }
    fun validarCampos(
        nome: String, desc: String, responsavel: String, data: String
    ): Boolean{

        return !(
                (nome == "" || nome.length < 3 || nome.length >20) ||
                        (desc == "" || desc.length < 5 || desc.length >200) ||
                        (responsavel == "" || desc.length < 3 || desc.length >20) ||
                        data == ""
                )
    }
    fun inserirNoBanco(){

        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val responsavel = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria = Categoria(categoriaSelecionada, null, null)

        if(validarCampos(nome, desc, responsavel, data)){
            val tarefa = Tarefa(
                0, nome, desc, responsavel, data, status, categoria
            )
            mainViewModel.addTarefa(tarefa)
        } else {
            val tarefa = Tarefa(
                tarefaSelecionada?.id!!,
                nome, desc, responsavel, data, status, categoria
            )
            mainViewModel.updateTarefa(tarefa)
        }
        Toast.makeText(
            context, "Tarefa Salva!",
            Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_formFragment_to_listFragment)
    }
    private fun carregarDados() {
        tarefaSelecionada = mainViewModel.tarefaSelecionada
        if(tarefaSelecionada != null) {
            binding.editNome.setText(tarefaSelecionada?.nome)
            binding.editDescricao.setText(tarefaSelecionada?.descricao)
            binding.editResponsavel.setText(tarefaSelecionada?.responsavel)
            binding.editData.setText(tarefaSelecionada?.data)
            binding.switchAtivoCard.isChecked = tarefaSelecionada?.status!!
        } else{
            binding.editNome.text = null
            binding.editDescricao.text = null
            binding.editResponsavel.text = null
            binding.editData.text = null
        }
    }

    override fun onTimeSelected(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }

}