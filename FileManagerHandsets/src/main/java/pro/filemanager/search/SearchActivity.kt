package pro.filemanager.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.TransactionsUtils
import bogdandonduk.androidlibs.standardactionbarandroid.OptionsMenuItemsKeysExtensionVocabulary
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.viewbindingutilsandroid.ViewBinder
import bogdandonduk.androidlibs.viewmodelutilsandroid.ViewModelHost
import com.r0adkll.slidr.Slidr
import pro.filemanager.FileManager
import pro.filemanager.R
import pro.filemanager.core.GenericViewModelFactory
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.databinding.ActivitySearchBinding
import pro.filemanager.settings.SettingsActivity

class SearchActivity : BaseActivity(), ViewBinder<ActivitySearchBinding>,
    ViewModelHost<SearchActivityViewModel> {
    override var viewBinding: ActivitySearchBinding? = null
    override val viewBindingInitialization: () -> ActivitySearchBinding = {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override var viewModel: SearchActivityViewModel? = null
    override val viewModelInitialization: () -> SearchActivityViewModel = {
        ViewModelProvider(this, GenericViewModelFactory { SearchActivityViewModel() }).get(SearchActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(root)

            slidrInterface = Slidr.attach(this@SearchActivity)

            rootView = root
            actionBarRootAppBarLayout = activitySearchActionbarInclude.root

            initializeActionBarWithBackNavigation(
                activity = this@SearchActivity,
                toolbar = activitySearchActionbarInclude.layoutSearchableToolbarToolbar
            )

            activitySearchActionbarInclude.layoutSearchableToolbarSearchEditText.run {
                UILanguagesService.initializeEditTextHintLanguage(this@SearchActivity, this, R.string.search)

                requestFocus()
            }
        }
    }

    override fun initializeLanguage() {
        super.initializeLanguage()

        UILanguagesService.initializeHomeAsUpIndicatorTooltipLanguage(
            activity = this,
            toolbar,
            R.string.go_back
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search_action_bar, menu)

        optionsMenuItems[OptionsMenuItemsKeysExtensionVocabulary.KEY_ITEM_SETTINGS] =
            menu?.findItem(R.id.menu_activity_search_settings) to { _: String, _: MenuItem? ->
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
}