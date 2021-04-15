package pro.filemanager.core.exceptions

object ExceptionsPlant {
    const val MESSAGE_MEDIA_STORE_ERROR = "MediaStore error"
    const val MESSAGE_INVALID_SORT_ORDER = "MediaStore error"

    fun run(tryAction: () -> Unit, vararg exceptionalActions: Pair<Class<out Throwable>, () -> Unit>) {
        try {
            tryAction.invoke()
        } catch (thr: Throwable) {
            exceptionalActions.forEach {
                if(thr::class.java == it.first) it.second.invoke()
            }
        }
    }
}