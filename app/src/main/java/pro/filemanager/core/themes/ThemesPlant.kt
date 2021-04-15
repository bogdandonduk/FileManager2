package pro.filemanager.core.themes

import android.content.Context
import android.content.res.Configuration
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.AppBarLayout
import pro.filemanager.FileManager
import pro.filemanager.R
import pro.filemanager.core.preferences.PreferencesPlant

object ThemesPlant {
    private const val KEY_SP_IS_NIGHT_MODE = "isNightMode"

    fun isNightMode(context: Context = FileManager.context) = PreferencesPlant.getSharedPreferences().getBoolean(KEY_SP_IS_NIGHT_MODE, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)

    fun initializeViewBackgroundColor(context: Context = FileManager.context, vararg views: View) {
        views.forEach {
            it.setBackgroundColor(ResourcesCompat.getColor(context.resources, if(isNightMode()) R.color.dark_background else R.color.light_background, null))
        }
    }

    fun initializeViewBackgroundColorAccent(context: Context = FileManager.context, vararg views: View) {
        views.forEach {
            it.setBackgroundColor(ResourcesCompat.getColor(context.resources, if(isNightMode()) R.color.dark_accent else R.color.light_accent, null))
        }
    }

    fun initializeDrawerToggle(context: Context = FileManager.context, toggle: ActionBarDrawerToggle) {
        toggle.drawerArrowDrawable.color = ResourcesCompat.getColor(context.resources, if(isNightMode()) R.color.light_text else R.color.dark_text, null)
    }

    fun initializeHomeAsUpArrow(actionBar: ActionBar) {
        actionBar.setHomeAsUpIndicator(if(isNightMode()) R.drawable.ic_baseline_arrow_back_24_light else R.drawable.ic_baseline_arrow_back_24_dark)
    }

    fun initializeAppBarLayout(appBarLayout: AppBarLayout) {
        appBarLayout.context.setTheme(if(isNightMode()) android.R.style.ThemeOverlay_Material_Dark else android.R.style.ThemeOverlay_Material_Light)
        appBarLayout.invalidate()
    }

    fun initializeText(context: Context = FileManager.context, vararg textViews: TextView) {
        textViews.forEach {
            it.setTextColor(ResourcesCompat.getColor(context.resources, if(isNightMode()) R.color.light_text else R.color.dark_text, null))
        }
    }

    fun initializeImage(context: Context = FileManager.context, imageView: ImageView, darkImageResId: Int, lightImageResId: Int) {
        imageView.setImageResource(if(isNightMode()) darkImageResId else lightImageResId)
    }

    fun initializeMenuIcon(context: Context = FileManager.context, menuItem: MenuItem, darkIconResId: Int, lightIconResId: Int) {
        menuItem.icon = ResourcesCompat.getDrawable(context.resources, if(isNightMode()) lightIconResId else darkIconResId, null)
    }
}