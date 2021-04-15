package pro.filemanager.settings

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.r0adkll.slidr.Slidr
import pro.filemanager.R
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.core.base.ViewBinder
import pro.filemanager.core.base.ViewModelHost
import pro.filemanager.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity(), ViewBinder<ActivitySettingsBinding>, ViewModelHost<SettingsActivityViewModel> {
    override var viewBinding: ActivitySettingsBinding? = null
    override val viewBindingInitialization: () -> ActivitySettingsBinding = {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override var viewModel: SettingsActivityViewModel? = null
    override val viewModelInitialization: () -> SettingsActivityViewModel = {
        ViewModelProvider(this, SettingsActivityViewModel.Factory()).get(SettingsActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(root)

            initializeTransitions(window)
            slidrInterface = Slidr.attach(this@SettingsActivity)

            rootView = root
            actionBarRootView = activitySettingsActionbarInclude.root

            toolbar = activitySettingsActionbarInclude.layoutToolbarToolbar

            initializeActionBar(
                activity = this@SettingsActivity,
                title = getString(R.string.settings),
                toolbar = activitySettingsActionbarInclude.layoutToolbarToolbar,
                showHomeAsUp = true
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_settings_action_bar, menu)

        optionsMenu = menu
        searchMenuItem = optionsMenu!!.findItem(R.id.menu_activity_settings_search)

        optionsMenuThemingAction.invoke()

        return true
    }
}