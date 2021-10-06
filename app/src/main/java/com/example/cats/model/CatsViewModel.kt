package com.example.cats.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cats.data.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(repo: CatsRepository) : ViewModel() {
    val cats = repo.getImagesList().cachedIn(viewModelScope)
}