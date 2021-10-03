package com.bignerdranch.android.thecatapi.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.thecatapi.api.TheCatApiImplementation
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {
    private val _cats = MutableLiveData<MutableList<Cat>>()
    val cats: MutableLiveData<MutableList<Cat>> get() = _cats

    init {
        viewModelScope.launch {
            _cats.value = TheCatApiImplementation.getListOfCats()
        }
    }

    fun updateListCats(newCats: MutableList<Cat>){
        cats.value?.addAll(newCats)
    }
}
