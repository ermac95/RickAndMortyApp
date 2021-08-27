package com.mycodeflow.rickandmortycharsapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.mycodeflow.rickandmortycharsapp.MyApp
import com.mycodeflow.rickandmortycharsapp.R
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem
import com.mycodeflow.rickandmortycharsapp.databinding.FragmentCharsInfoBinding
import com.mycodeflow.rickandmortycharsapp.presentation.viewmodels.AppViewModelFactory
import com.mycodeflow.rickandmortycharsapp.presentation.viewmodels.CharViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class CharsInfoFragment : Fragment(R.layout.fragment_chars_info) {

    private val vb by viewBinding(FragmentCharsInfoBinding::bind)

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
        setupViews(view)
    }

    private fun setupViewModels() {
        charsViewModel = ViewModelProvider(this, viewModelFactory)
                .get(CharViewModel::class.java)
        val charId = arguments?.getInt("char_id")
        if (charId != null) {
            charsViewModel.updateCurrentChar(charId)
        }
    }

    private fun setupViews(view: View) {
        lifecycleScope.launchWhenResumed {
            charsViewModel.currentChar.collectLatest { char->
                updateDetails(char, view)
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

    private fun updateDetails(char: CharItem?, view: View) = with(vb) {
        status.text = getString(R.string.char_status, char?.status)
        idNumber.text = char?.id.toString()
        Glide.with(view)
                .load(char?.image)
                .into(portrait)
        name.text = char?.name
        species.text = char?.species
        gender.text = char?.gender
        originField.text = char?.origin?.name
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