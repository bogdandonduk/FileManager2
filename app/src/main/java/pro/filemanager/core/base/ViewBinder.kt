package pro.filemanager.core.base

interface ViewBinder<T> {
    var viewBinding: T?
    val viewBindingInitialization: () -> T

    fun getCurrentViewBinding() : T {
        if(viewBinding == null)
            viewBinding = viewBindingInitialization.invoke()

        return viewBinding!!
    }
}