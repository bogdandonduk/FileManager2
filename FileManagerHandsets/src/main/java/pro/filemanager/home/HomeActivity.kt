package pro.filemanager.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import bogdandonduk.androidlibs.permissionsandroid.PermissionRequesterActivity
import bogdandonduk.androidlibs.recyclerviewutilsandroid.RecyclerViewHost
import bogdandonduk.androidlibs.recyclerviewutilsandroid.dragswipe.DragSwipeService
import bogdandonduk.androidlibs.standardactionbarandroid.ActionBarHost
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.viewbindingutilsandroid.ViewBinder
import bogdandonduk.androidlibs.viewmodelutilsandroid.ViewModelHost
import pro.filemanager.R
import pro.filemanager.core.GenericViewModelFactory
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.databinding.ActivityHomeBinding
import pro.filemanager.home.sections.HomeSectionsAdapter
import pro.filemanager.home.sections.HomeSectionsPlant

@Suppress("UNCHECKED_CAST")
@SuppressLint("ClickableViewAccessibility")
class HomeActivity : BaseActivity(), ViewBinder<ActivityHomeBinding>,
    PermissionRequesterActivity,
    ViewModelHost<HomeActivityViewModel>,
    RecyclerViewHost {
    override var viewBinding: ActivityHomeBinding? = null
    override val viewBindingInitialization: () -> ActivityHomeBinding = {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override var viewModel: HomeActivityViewModel? = null
    override val viewModelInitialization: () -> HomeActivityViewModel = {
        ViewModelProvider(this, GenericViewModelFactory { HomeActivityViewModel() }).get(HomeActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(root)

            rootView = root

            actionBarRootAppBarLayout = activityHomeActionbarInclude.root

            navDrawerToggle = initializeActionBarWithDrawer(
                activity = this@HomeActivity,
                toolbar = activityHomeActionbarInclude.layoutStandardToolbarToolbar,
                hostDrawerLayout = root,
                mainContentView = activityHomeMainContentCoordinatorView,
                navDrawerView = activityHomeDrawerNavigationView,
                onDrawerSlideBehavior = ActionBarHost.onDrawerSlideMainContentPartialOffsetBehavior,
                syncState = true,
                showTitle = true
            )

            // TODO: Artificially localize openDrawer and closeDrawer contentDescriptions (somehow via localizing Activity's context that is passed into ActionBarDrawerToggle())
        }

        requestPermissions()
    }

    override fun initializeTheme() {
        super.initializeTheme()

        updateLists(mutableListOf<HomeSectionsAdapter>().apply {
            add(getCurrentViewBinding().activityHomeSectionsListRecyclerView.adapter as HomeSectionsAdapter)
        })
    }

    override fun initializeLanguage() {
        super.initializeLanguage()

        UILanguagesService.initializeActionBarTitleLanguage(
            context = this,
            toolbar = toolbar,
            R.string.app_name
        )

        updateLists(mutableListOf<HomeSectionsAdapter>().apply {
            add(getCurrentViewBinding().activityHomeSectionsListRecyclerView.adapter as HomeSectionsAdapter)
        })
    }

    override fun requestPermissions() {
        launchCore()
    }

    override fun launchCore() {
        with(getCurrentViewBinding()) {
            initializeList(
                recyclerView = activityHomeSectionsListRecyclerView,
                adapter = HomeSectionsAdapter(items = HomeSectionsPlant.getHomeSectionsItems(), hostActivity = this@HomeActivity) as RecyclerView.Adapter<RecyclerView.ViewHolder>,
            )

            DragSwipeService.initializeDraggerAdapter(activityHomeSectionsListRecyclerView, activityHomeSectionsListRecyclerView.adapter as HomeSectionsAdapter)
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        PermissionsService.handleStorageRequestResult(
//            activity = this,
//            requestCode = requestCode,
//            grantResults = grantResults,
//            deniedAction = {
//                getCurrentViewModel().requestedPermissionsMap.run {
//                    if(this[requestCode] != null && this[requestCode]!!)
//                        finish()
//                    else
//                        this[requestCode] = true
//
//                    requestPermissions()
//                }
//
//            },
//            grantedAction = {
//                launchCoreFunctionality()
//            }
//        )
//    }
}