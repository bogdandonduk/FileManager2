package pro.filemanager.core.base.activity

import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.TransactionsUtils
import bogdandonduk.androidlibs.popuputilsandroid.PopupUtilsService
import bogdandonduk.androidlibs.standardactionbarandroid.ActionBarHost
import bogdandonduk.androidlibs.standardactionbarandroid.OptionsMenuItemsKeysExtensionVocabulary
import bogdandonduk.androidlibs.standardactionbarandroid.SlidrHost
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesHost
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.userinterfacethemesandroid.UIThemesHost
import bogdandonduk.androidlibs.userinterfacethemesandroid.UIThemesService
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.r0adkll.slidr.model.SlidrInterface
import pro.filemanager.FileManager
import pro.filemanager.R
import pro.filemanager.search.SearchActivity
import pro.filemanager.settings.SettingsActivity

abstract class BaseActivity : AppCompatActivity(),
        ActionBarHost,
        UIThemesHost,
        UILanguagesHost,
        SlidrHost {

    val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    // ActionBarHost
    override var actionBarRootAppBarLayout: AppBarLayout? = null
    override var navDrawerRootNavigationView: NavigationView? = null

    override var toolbar: Toolbar? = null
    override var navDrawerToggle: ActionBarDrawerToggle? = null
    override val optionsMenuItems = mutableMapOf<String, Pair<MenuItem?, (itemKey: String, menuItem: MenuItem?) -> Unit>>()

    override var slidrInterface: SlidrInterface? = null
    //

    var rootView: View? = null

    fun initializeThemeAndLanguage(repeatDelay: Long = 0) {
        initializeTheme()
        initializeLanguage()

        if(repeatDelay > 0)
            handler.postDelayed({
                initializeThemeAndLanguage()
            }, repeatDelay)
    }

    override fun onResume() {
        super.onResume()

        FileManager.currentActivity = this

        initializeThemeAndLanguage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = (navDrawerToggle != null && navDrawerToggle!!.onOptionsItemSelected(item)) || super.onOptionsItemSelected(item)

    override fun initializeLanguage() {
        PopupUtilsService.getTooltipPopupBuilder()
        getHomeAsUpIndicatorAsView(this, toolbar)
        (
            activity = this,
            toolbar,
            R.string.open_menu
        )

        UILanguagesService.initializeOverflowMenuTooltipLanguage(
            context = this,
            toolbar,
            R.string.more_options
        )

        with(optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SEARCH]?.first) {
            UILanguagesService.initializeOptionsItemTitleLanguage(
                context = this@BaseActivity,
                menuItem = this,
                stringResId = R.string.search
            )

            UILanguagesService.initializeOptionsMenuItemTooltipLanguage(
                context = this@BaseActivity,
                menuItem = this,
                stringResId = R.string.search
            )
        }

        with(optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SETTINGS]?.first) {
            UILanguagesService.initializeOptionsItemTitleLanguage(
                context = this@BaseActivity,
                menuItem = this,
                stringResId = R.string.settings
            )

            UILanguagesService.initializeOptionsMenuItemTooltipLanguage(
                context = this@BaseActivity,
                menuItem = this,
                stringResId = R.string.settings
            )
        }
    }

    override fun initializeTheme() {
        UIThemesService.initializeViewBackgroundColor(
                context = this,
                colorResId = R.color.background,
                views = arrayOf(rootView)
        )

        with(actionBarRootAppBarLayout) {
            UIThemesService.initializeViewBackgroundColor(
                    context = this@BaseActivity,
                    colorResId = R.color.accent,
                    views = arrayOf(this)
            )

            UIThemesService.initializeAppBarLayoutStyle(
                    context = this@BaseActivity,
                    appBarLayout = this
            )
        }

        UIThemesService.initializeViewBackgroundColor(
                context = this,
                colorResId = R.color.accent,
                views = arrayOf(navDrawerRootNavigationView)
        )

        if(navDrawerToggle != null)
            UIThemesService.initializeDrawerToggleColor(
                    context = this,
                    colorResId = R.color.text,
                    drawerToggle = navDrawerToggle
            )
        else
            UIThemesService.initializeHomeAsUpIndicator(
                    context = this,
                    actionBar = supportActionBar,
                    lightThemeDrawableResId = R.drawable.ic_baseline_arrow_back_24_dark,
                    darkThemeDrawableResId = R.drawable.ic_baseline_arrow_back_24_light,
            )

        UIThemesService.initializeActionBarTitleColor(
            context = this,
            toolbar = toolbar,
            colorResId = R.color.text
        )

        UIThemesService.initializeMenuIcon(
            context = this,
            menuItem = optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SEARCH]?.first,
            drawableResId = R.drawable.ic_baseline_search_24
        )

        UIThemesService.initializeOverflowMenuIcon(
            context = this,
            toolbar = toolbar,
            drawableResId = R.drawable.ic_baseline_overflow_menu_24
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home_action_bar, menu)

        optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SEARCH] =
            menu?.findItem(R.id.menu_activity_home_search) to { _: String, _: MenuItem? ->
                TransactionsUtils.openActivity(
                    context = this,
                    activityClass = SearchActivity::class.java,
                    currentActivityTracker = FileManager.currentActivity
                )
            }

        optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SETTINGS] =
            menu?.findItem(R.id.menu_activity_home_settings) to { _: String, _: MenuItem? ->
                TransactionsUtils.openActivity(
                    context = this,
                    activityClass = SettingsActivity::class.java,
                    currentActivityTracker = FileManager.currentActivity
                )
            }

        initializeOptionsMenu()

        initializeThemeAndLanguage(1000)

        return true
    }

    open fun launchCore() {

    }
}