package pro.filemanager.core.base

interface ViewModelHost<T : BaseViewModel> {
    var viewModel: T?
    val viewModelInitialization: () -> T

    fun getCurrentViewModel() : T {
        if(viewModel == null)
            viewModel = viewModelInitialization.invoke()

        return viewModel!!
    }
}