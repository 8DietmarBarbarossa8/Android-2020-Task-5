package com.bignerdranch.android.thecatapi.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.thecatapi.api.TheCatApiImplementation
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {
    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> get() = _cats

    init {
        viewModelScope.launch {
            _cats.value = TheCatApiImplementation.getListOfCats()
        }
    }
}
