package com.raya.curro.pruebanapptilus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.raya.curro.pruebanapptilus.R
import com.raya.curro.pruebanapptilus.data.api.RestApiImpl
import com.raya.curro.pruebanapptilus.data.api.RetrofitBuilder
import com.raya.curro.pruebanapptilus.data.model.Results
import com.raya.curro.pruebanapptilus.databinding.FragmentDetailBinding
import com.raya.curro.pruebanapptilus.intent.DetailIntent
import com.raya.curro.pruebanapptilus.intent.MainIntent
import com.raya.curro.pruebanapptilus.intent.state.DetailState
import com.raya.curro.pruebanapptilus.intent.state.MainState
import com.raya.curro.pruebanapptilus.ui.viewmodels.DetailViewModel
import com.raya.curro.pruebanapptilus.ui.viewmodels.factory.DetailViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_characterslist.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class OompaLoompaDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private var idOompaLoompa: Int = 0
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idOompaLoompa = arguments?.getSerializable("id") as Int

        val _detailViewModel: DetailViewModel by viewModels { DetailViewModelFactory(
            RestApiImpl(RetrofitBuilder.apiService)) }
        detailViewModel = _detailViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupObserver()

        fetchGetCharacters()
    }

    private fun fetchGetCharacters() {
        lifecycleScope.launch {
            detailViewModel.setId(idOompaLoompa)
            detailViewModel.userIntent.send(DetailIntent.FetchOompaLoompaDetail)
        }
    }

    private fun setupObserver(){
        detailViewModel.characterItem.observe(viewLifecycleOwner) {
            bindElements(it)
        }
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            detailViewModel.detailState.collect { detailState ->
                when(detailState){
                    is DetailState.Loading -> {
                        detail_content.visibility = View.GONE
                        detail_progress_bar.visibility = View.VISIBLE
                        detail_retry_button.visibility = View.GONE
                    }

                    is DetailState.LoadTasks -> {
                        bindElements(detailState.oompaLoompa)
                    }

                    is DetailState.Error -> {
                        detail_content.visibility = View.GONE
                        detail_progress_bar.visibility = View.GONE
                        detail_retry_button.visibility = View.VISIBLE
                        Toast.makeText(context, "Error: ${detailState.error}", Toast.LENGTH_SHORT).show()
                    }
                    is DetailState.Idle -> {
                        detail_retry_button.visibility = View.GONE
                        detail_progress_bar.visibility = View.GONE
                        detail_content.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun bindElements(detail: Results?) {
        detailViewModel.setIdleFragment()

        with(binding) {
            detailName.text = getString(R.string.oompa_loompa_name, detail?.firstName, detail?.lastName)
            detailGenre.text = if(detail?.gender.isNullOrEmpty()) "NO DESCRIPTION" else detail?.gender
            detailWork.text = if(detail?.profession.isNullOrEmpty()) "NO DESCRIPTION" else detail?.profession
            detailHeigh.text = if(detail?.height == null) "NO DESCRIPTION" else detail.height.toString()
            Picasso.with(binding.root.context)
                .load(detail?.getImageUrl())
                .into(detailImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}