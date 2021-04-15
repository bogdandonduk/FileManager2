package pro.filemanager.images.library

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.r0adkll.slidr.Slidr
import pro.filemanager.R
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.core.base.ViewBinder
import pro.filemanager.core.base.ViewModelHost
import pro.filemanager.core.lists.ListHost
import pro.filemanager.databinding.ActivityImageLibraryBinding

class ImageLibraryActivity : BaseActivity(), ViewBinder<ActivityImageLibraryBinding>, ViewModelHost<ImageLibraryActivityViewModel>, ListHost {
    override var viewBinding: ActivityImageLibraryBinding? = null
    override val viewBindingInitialization: () -> ActivityImageLibraryBinding = {
        ActivityImageLibraryBinding.inflate(layoutInflater)
    }

    override var viewModel: ImageLibraryActivityViewModel? = null
    override val viewModelInitialization: () -> ImageLibraryActivityViewModel = {
        ViewModelProvider(this, ImageLibraryActivityViewModel.Factory()).get(ImageLibraryActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(getCurrentViewBinding().root)

            slidrInterface = Slidr.attach(this@ImageLibraryActivity)

            rootView = root
            actionBarRootView = activityImageLibraryActionbarInclude.root

            toolbar = activityImageLibraryActionbarInclude.layoutToolbarToolbar

            initializeActionBar(
                    activity = this@ImageLibraryActivity,
                    title = getString(R.string.images),
                    toolbar = toolbar!!,
                    showHomeAsUp = true
            )
        }
    }
}