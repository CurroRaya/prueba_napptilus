package com.raya.curro.pruebanapptilus.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raya.curro.pruebanapptilus.ui.viewmodels.ListViewModel
import com.raya.curro.pruebanapptilus.data.api.RestApi
import com.raya.curro.pruebanapptilus.data.repository.MainRepository

class ListViewModelFactory(private val restApi: RestApi) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(MainRepository(restApi)) as T

        throw IllegalArgumentException("Unknown class name")
    }
}