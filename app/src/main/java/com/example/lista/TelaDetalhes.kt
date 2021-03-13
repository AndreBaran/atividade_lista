package com.example.lista

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class TelaDetalhes : Fragment() {

    lateinit var db: AppDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //pegar banco de dados
        db = Room.databaseBuilder(
            context, //acesso ao "aplicativo" (Activity Principal)
            AppDatabase::class.java, //use esse esquema de banco de dados
            "listadb") //o banco de dados se chama assim
            .allowMainThreadQueries() //permita eu engasgar a tela
            .build() //vai!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tela_detalhes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Lógicas do Fragmento de Detalhes
        val dao = db.afazerDao()
        var afazerID = arguments?.getLong("afazerID")
        if (afazerID != null){
            val afazer = dao.get(afazerID)
            val titulo_afazer : TextView = view.findViewById(R.id.titulo_afazer)
            val descricao_afazer : TextView = view.findViewById(R.id.editTextTextMultiLine)

            titulo_afazer.text=afazer.titulo
            descricao_afazer.text=afazer.descricao

            val bt_salvar : Button =  view.findViewById(R.id.salvar)
            bt_salvar.setOnClickListener{
                val titulo = titulo_afazer.text.toString()
                val descricao = descricao_afazer.text.toString()

                val afazer = Afazer(titulo,descricao)
                afazer.id=afazerID
                dao.update(afazer)
                hideKeyboard()
                volta_lista(it)
                //this.findNavController().navigate(R.id.detalhes_para_lista)

            }

        }
    }
    fun volta_lista(v: View) {
        v.findNavController().navigate(R.id.detalhes_para_lista)
    }
    fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.requireActivity().currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}