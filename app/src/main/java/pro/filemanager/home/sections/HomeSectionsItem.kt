package pro.filemanager.home.sections

import android.view.View
import pro.filemanager.core.base.BaseActivity

data class HomeSectionsItem(
        val titleResId: Int,
        val persistableKey: String,
        val initializationAction: (rootView: View) -> Unit,
        val clickAction: (view: View, activity: BaseActivity) -> Unit
) {
    override fun toString(): String = persistableKey
}