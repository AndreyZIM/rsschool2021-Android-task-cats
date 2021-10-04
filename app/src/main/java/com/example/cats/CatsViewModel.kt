package com.example.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cats.data.CatsRepository
import com.example.cats.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject public constructor(repo: CatsRepository) : ViewModel() {
    val cats = repo.getImagesList().cachedIn(viewModelScope)
}