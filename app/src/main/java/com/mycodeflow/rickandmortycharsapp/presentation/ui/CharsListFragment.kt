package com.mycodeflow.rickandmortycharsapp.presentation.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mycodeflow.rickandmortycharsapp.MyApp
import com.mycodeflow.rickandmortycharsapp.R
import com.mycodeflow.rickandmortycharsapp.adapters.CharListItemDecorator
import com.mycodeflow.rickandmortycharsapp.adapters.MainMenuCharsListAdapter
import com.mycodeflow.rickandmortycharsapp.databinding.FragmentCharsListBinding
import com.mycodeflow.rickandmortycharsapp.presentation.viewmodels.AppViewModelFactory
import com.mycodeflow.rickandmortycharsapp.presentation.viewmodels.CharViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class CharsListFragment : Fragment(R.layout.fragment_chars_list) {

    private val vb by viewBinding(FragmentCharsListBinding::bind)

    private var charsListAdapter: MainMenuCharsListAdapter? = null

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var charsViewModel: CharViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupViews()
    }

    override fun onDetach() {
        super.onDetach()
        charsListAdapter = null
    }

    private fun setupViewModels() {
        charsViewModel = ViewModelProvider(this, viewModelFactory)
                .get(CharViewModel::class.java)
    }

    private fun setupViews() = with(vb) {
        charsListAdapter = MainMenuCharsListAdapter()
        rvCharsList.apply {
            adapter = charsListAdapter
            addItemDecoration(CharListItemDecorator(requireContext(), 12, 12))
        }

        lifecycleScope.launchWhenResumed {
            charsViewModel.charsList.collectLatest { charsList ->
                charsListAdapter?.setData(charsList)
            }
        }

        lifecycleScope.launchWhenResumed {
            charsViewModel.loading.collectLatest {
                changeLoadingStatus(it)
            }
        }

        lifecycleScope.launchWhenResumed {
            charsViewModel.errorMessage.collectLatest { error->
                changeErrorStatus(error)
            }
        }
    }

    private fun changeLoadingStatus(loading: Boolean) {
        vb.loadingStatus.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun changeErrorStatus(error: String) {
        if (error.isEmpty()){
            vb.errorMessage.visibility = View.GONE
        } else {
            vb.errorMessage.visibility = View.VISIBLE
            vb.errorMessage.text = error
        }
    }
}