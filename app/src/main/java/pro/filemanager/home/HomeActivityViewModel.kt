package pro.filemanager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.filemanager.core.base.BaseViewModel

@Suppress("UNCHECKED_CAST")
class HomeActivityViewModel : BaseViewModel() {

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
            return HomeActivityViewModel() as T
        }
    }
}