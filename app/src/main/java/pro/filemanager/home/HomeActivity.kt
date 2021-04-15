package pro.filemanager.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import pro.filemanager.R
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.core.base.ViewBinder
import pro.filemanager.core.base.ViewModelHost
import pro.filemanager.core.dragswipe.DragSwipePlant
import pro.filemanager.core.lists.ListHost
import pro.filemanager.databinding.ActivityHomeBinding
import pro.filemanager.home.sections.HomeSectionsAdapter
import pro.filemanager.home.sections.HomeSectionsPlant

@Suppress("UNCHECKED_CAST")
@SuppressLint("ClickableViewAccessibility")
class HomeActivity : BaseActivity(), ViewBinder<ActivityHomeBinding>,
    ViewModelHost<HomeActivityViewModel>, ListHost {
    override var viewBinding: ActivityHomeBinding? = null
    override val viewBindingInitialization: () -> ActivityHomeBinding = {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override var viewModel: HomeActivityViewModel? = null
    override val viewModelInitialization: () -> HomeActivityViewModel = {
        ViewModelProvider(this, HomeActivityViewModel.Factory()).get(HomeActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(getCurrentViewBinding()) {
            setContentView(root)

            rootView = root

            actionBarRootView = activityHomeActionbarInclude.root
            toolbar = activityHomeActionbarInclude.layoutToolbarToolbar

            navDrawerRootView = activityHomeDrawerNavigationView

            navDrawerToggle =
                    initializeActionBar(
                            activity = this@HomeActivity,
                            toolbar = toolbar!!,
                            title = getString(R.string.app_name),
                            hostDrawerLayout = root,
                            mainContentView = activityHomeMainContentCoordinatorView
                    )

            if(navDrawerToggle != null) {
                root.addDrawerListener(navDrawerToggle!!)

                navDrawerToggle!!.syncState()
            }

            initializeList(
                    recyclerView = activityHomeSectionsListRecyclerView,
                    adapter = HomeSectionsAdapter(items = HomeSectionsPlant.getHomeSectionsItems(), hostActivity = this@HomeActivity) as RecyclerView.Adapter<RecyclerView.ViewHolder>,
            )

            DragSwipePlant.initializeDraggerAdapter(activityHomeSectionsListRecyclerView, activityHomeSectionsListRecyclerView.adapter as HomeSectionsAdapter)
        }
    }

    override fun initializeLook() {
        super.initializeLook()

        updateList(adapters = arrayOf(getCurrentViewBinding().activityHomeSectionsListRecyclerView.adapter))
    }

    override fun initializeLanguage() {

    }
}