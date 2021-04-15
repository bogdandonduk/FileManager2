package pro.filemanager.core.actionbar

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import pro.filemanager.R
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.settings.SettingsActivity

interface ActionBarHost {
    var actionBarRootView: AppBarLayout?
    var toolbar: Toolbar?
    var navDrawerToggle: ActionBarDrawerToggle?
    var optionsMenu: Menu?
    var searchMenuItem: MenuItem?

    fun initializeActionBar(
            activity: BaseActivity,
            toolbar: Toolbar,
            title: String? = null,
            showHomeAsUp: Boolean = true,
            hostDrawerLayout: DrawerLayout? = null,
            mainContentView: View? = null
    ) : ActionBarDrawerToggle? {
        activity.setSupportActionBar(toolbar)

        with(activity.supportActionBar!!) {
            setDisplayHomeAsUpEnabled(showHomeAsUp)

            if(title != null) {
                setDisplayShowTitleEnabled(true)
                this.title = title
            } else
                setDisplayShowTitleEnabled(false)
        }

        return if(showHomeAsUp) {
            if(hostDrawerLayout != null)
                object : ActionBarDrawerToggle(
                        activity,
                        hostDrawerLayout,
                        R.string.open,
                        R.string.close
                ) {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        super.onDrawerSlide(drawerView, slideOffset)

                        with(mainContentView) {
                            this?.translationX = drawerView.width * slideOffset
                        }
                    }
                }
            else {
                toolbar.setNavigationOnClickListener {
                    activity.onBackPressed()
                }

                null
            }
        } else null
    }

    fun openSettingsActivity(activity: BaseActivity) {
        activity.window.exitTransition = null
        activity.startActivity(Intent(activity, SettingsActivity::class.java))
    }
}