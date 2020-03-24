package com.sourabh.chsatplace.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabh.chsatplace.ui.viewModels.MessageItemViewModel

class ViewModelFactory<T> constructor(val creation: () -> T ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creation() as T
    }

}