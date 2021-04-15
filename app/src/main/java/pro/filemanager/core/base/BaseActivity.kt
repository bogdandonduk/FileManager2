package pro.filemanager.core.base

import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.AppBarLayout
import com.r0adkll.slidr.model.SlidrInterface
import pro.filemanager.R
import pro.filemanager.core.actionbar.ActionBarHost
import pro.filemanager.core.languages.LanguageHost
import pro.filemanager.core.navigationdrawer.NavigationDrawerHost
import pro.filemanager.core.slidr.SlidrHost
import pro.filemanager.core.themes.ThemeHost
import pro.filemanager.core.themes.ThemesPlant

abstract class BaseActivity : AppCompatActivity(), ActionBarHost, ThemeHost, LanguageHost, NavigationDrawerHost, SlidrHost {
    private lateinit var handler: Handler
    fun getHandler() : Handler {
        if(!this::handler.isInitialized)
            handler = Handler(Looper.getMainLooper())

        return handler
    }

    override var rootView: View? = null
    override var actionBarRootView: AppBarLayout? = null
    override var navDrawerRootView: View? = null
    override var toolbar: Toolbar? = null
    override var navDrawerToggle: ActionBarDrawerToggle? = null
    override var optionsMenu: Menu? = null
    override var searchMenuItem: MenuItem? = null

    override var slidrInterface: SlidrInterface? = null

    val optionsMenuThemingAction = {
        if(searchMenuItem != null)
            ThemesPlant.initializeMenuIcon(
                menuItem = searchMenuItem!!,
                darkIconResId = R.drawable.ic_baseline_search_24_dark,
                lightIconResId = R.drawable.ic_baseline_search_24_light
            )

        toolbar?.overflowIcon = ResourcesCompat.getDrawable(resources, if(ThemesPlant.isNightMode()) R.drawable.ic_baseline_overflow_menu_24_light else R.drawable.ic_baseline_overflow_menu_24_dark, null)
    }

    override fun onResume() {
        super.onResume()

        initializeLook()
        initializeLanguage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return navDrawerToggle != null && navDrawerToggle!!.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun initializeLook() {
        if(rootView != null) ThemesPlant.initializeViewBackgroundColor(views = arrayOf(rootView!!))
        if(actionBarRootView != null) {
            ThemesPlant.initializeViewBackgroundColorAccent(views = arrayOf(actionBarRootView!!))
            ThemesPlant.initializeAppBarLayout(actionBarRootView!!)
        }
        
        if(navDrawerRootView != null) ThemesPlant.initializeViewBackgroundColorAccent(views = arrayOf(navDrawerRootView!!))

        if(supportActionBar != null) {
            if(navDrawerToggle != null)
                ThemesPlant.initializeDrawerToggle(toggle = navDrawerToggle!!)
            else
                ThemesPlant.initializeHomeAsUpArrow(actionBar = supportActionBar!!)

            toolbar?.setTitleTextColor(ResourcesCompat.getColor(resources, if(ThemesPlant.isNightMode()) R.color.light_text else R.color.dark_text, null))
        }

        optionsMenuThemingAction.invoke()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home_action_bar, menu)

        optionsMenu = menu
        searchMenuItem = optionsMenu!!.findItem(R.id.menu_activity_home_search)

        optionsMenuThemingAction.invoke()

        optionsMenu!!.findItem(R.id.menu_activity_home_settings).setOnMenuItemClickListener {
            openSettingsActivity(this)

            false
        }

        return true
    }
}