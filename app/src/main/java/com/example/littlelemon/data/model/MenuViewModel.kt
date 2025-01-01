package com.example.littlelemon.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MenuViewModel(application: Application) : AndroidViewModel(application = application) {

    private val appRepository = AppRepository.getInstance(
        application.applicationContext
    )

    private val _menuData = MutableStateFlow<Result<List<MenuItemEntity>>>(Result.Loading)
    val menuData: StateFlow<Result<List<MenuItemEntity>>> = _menuData

    init {
        fetchData()
    }

    fun fetchData() {
        appRepository.getMenuData()
        appRepository.getMenuData()
            .asResult()
            .onEach { _menuData.value = it }
            .launchIn(viewModelScope)
    }
}