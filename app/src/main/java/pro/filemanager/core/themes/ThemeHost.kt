package pro.filemanager.core.themes

import android.view.View

interface ThemeHost {
    var rootView: View?
    fun initializeLook()
}