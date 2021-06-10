package pro.filemanager.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import bogdandonduk.androidlibs.standardactionbarandroid.OptionsMenuItemsKeysExtensionVocabulary
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.viewbindingutilsandroid.ViewBinder
import bogdandonduk.androidlibs.viewmodelutilsandroid.ViewModelHost
import com.r0adkll.slidr.Slidr
import pro.filemanager.R
import pro.filemanager.core.GenericViewModelFactory
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity(), ViewBinder<ActivitySettingsBinding>,
    ViewModelHost<SettingsActivityViewModel> {
    override var viewBinding: ActivitySettingsBinding? = null
    override val viewBindingInitialization: () -> ActivitySettingsBinding = {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override var viewModel: SettingsActivityViewModel? = null
    override val viewModelInitialization: () -> SettingsActivityViewModel = {
        ViewModelProvider(this, GenericViewModelFactory { SettingsActivityViewModel() }).get(SettingsActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(root)

            slidrInterface = Slidr.attach(this@SettingsActivity)

            rootView = root
            actionBarRootAppBarLayout = activitySettingsActionbarInclude.root

            initializeActionBarWithBackNavigation(
                activity = this@SettingsActivity,
                toolbar = activitySettingsActionbarInclude.layoutStandardToolbarToolbar
            )
        }
    }

    override fun initializeLanguage() {
        super.initializeLanguage()

        UILanguagesService.initializeActionBarTitleLanguage(
            context = this,
            toolbar = toolbar,
            R.string.settings
        )

        UILanguagesService.initializeHomeAsUpIndicatorTooltipLanguage(
            activity = this,
            toolbar,
            R.string.go_back
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_settings_action_bar, menu)

        optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SEARCH] =
            menu?.findItem(R.id.menu_activity_settings_search) to { _: String, _: MenuItem? ->

            }

        initializeOptionsMenu()

        initializeThemeAndLanguage(1000)

        return true
    }
}