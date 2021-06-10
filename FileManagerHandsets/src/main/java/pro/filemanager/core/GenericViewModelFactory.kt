package pro.filemanager.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.filemanager.core.base.viewmodel.BaseViewModel

class GenericViewModelFactory<T : BaseViewModel>(private val viewModelInitialization: () -> T) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T2 : ViewModel?> create(modelClass: Class<T2>): T2 {
        return viewModelInitialization.invoke() as T2
    }
}