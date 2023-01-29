package com.raya.curro.pruebanapptilus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.raya.curro.pruebanapptilus.R
import com.raya.curro.pruebanapptilus.data.api.RestApiImpl
import com.raya.curro.pruebanapptilus.data.api.RetrofitBuilder
import com.raya.curro.pruebanapptilus.data.model.Results
import com.raya.curro.pruebanapptilus.databinding.FragmentCharacterslistBinding
import com.raya.curro.pruebanapptilus.intent.MainIntent
import com.raya.curro.pruebanapptilus.intent.state.MainState
import com.raya.curro.pruebanapptilus.ui.adapters.CharacterAdapter
import com.raya.curro.pruebanapptilus.ui.adapters.listeners.OnCharacterClickListener
import com.raya.curro.pruebanapptilus.ui.viewmodels.ListViewModel
import com.raya.curro.pruebanapptilus.ui.viewmodels.factory.ListViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_characterslist.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OompaLoompaListFragments : Fragment() {

    private var _binding: FragmentCharacterslistBinding? = null

    private val listViewModel: ListViewModel by viewModels { ListViewModelFactory(
        RestApiImpl(RetrofitBuilder.apiService)
    ) }

    private val mainAdapter by lazy {
        CharacterAdapter(requireContext(), ArrayList(), object: OnCharacterClickListener {
            override fun onCharacterClick(id: Int?) {
                lifecycleScope.launch {
                    val bundle = bundleOf(
                        "id" to id
                    )
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        bundle
                    )
                }
            }
        })
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterslistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupClicks()
        observeViewModel()
        if(mainAdapter.itemCount == 0)
            fetchGetCharacters()
    }

    private fun fetchGetCharacters() {
        lifecycleScope.launch {
            listViewModel.userIntent.send(MainIntent.FetchOompaLoompas)
        }
    }


    private fun getMoreCharacters() {
        lifecycleScope.launch {
            listViewModel.userIntent.send(MainIntent.GetMoreOompaLoompas)
        }
    }


    private fun setupUI(){
        character_list_recycler_view.run {
            adapter = mainAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)){
                        getMoreCharacters()
                    }
                }

            })
        }
    }


    private fun setupClicks(){
        characters_retry_button.setOnClickListener {
            fetchGetCharacters()
        }
    }


    private fun observeViewModel(){
        lifecycleScope.launch {
            listViewModel.mainState.collect { mainState ->
                when(mainState){
                    is MainState.Loading -> {
                        character_list_recycler_view.visibility = View.VISIBLE
                        characters_progress_bar.visibility = View.VISIBLE
                        characters_retry_button.visibility = View.GONE
                    }

                    is MainState.LoadTasks -> {
                        renderList(mainState.oompaLoompas.results)
                    }

                    is MainState.Error -> {
                        character_list_recycler_view.visibility = View.GONE
                        characters_progress_bar.visibility = View.GONE
                        characters_retry_button.visibility = View.VISIBLE
                        Toast.makeText(context, "Error: ${mainState.error}", Toast.LENGTH_SHORT).show()
                    }
                    is MainState.Idle -> {
                        characters_retry_button.visibility = View.GONE
                        characters_progress_bar.visibility = View.GONE
                        character_list_recycler_view.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun renderList(listTodoTask: ArrayList<Results>){
        mainAdapter.setChatacterList(listTodoTask)
        listViewModel.setIdleFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}