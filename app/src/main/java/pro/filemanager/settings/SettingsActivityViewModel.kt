package pro.filemanager.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.filemanager.core.base.BaseViewModel

@Suppress("UNCHECKED_CAST")
class SettingsActivityViewModel() : BaseViewModel() {

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
            return SettingsActivityViewModel() as T
        }
    }
}