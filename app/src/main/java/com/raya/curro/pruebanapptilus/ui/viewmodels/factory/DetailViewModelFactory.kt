package com.raya.curro.pruebanapptilus.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raya.curro.pruebanapptilus.data.api.RestApi
import com.raya.curro.pruebanapptilus.ui.viewmodels.DetailViewModel

class DetailViewModelFactory(private val restApi: RestApi) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(restApi) as T
}