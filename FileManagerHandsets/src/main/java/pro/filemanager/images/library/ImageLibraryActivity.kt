package pro.filemanager.images.library

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import bogdandonduk.androidlibs.bottomsheetmodalsandroid.BottomSheetModalsService
import bogdandonduk.androidlibs.bottomsheetmodalsandroid.anatomy.BottomSheetModalAnatomy
import bogdandonduk.androidlibs.bottomsheetmodalsandroid.simple.SimpleBottomSheetModalArgReference
import bogdandonduk.androidlibs.permissionsandroid.PermissionRequesterActivity
import bogdandonduk.androidlibs.permissionsandroid.PermissionsService
import bogdandonduk.androidlibs.recyclerviewutilsandroid.RecyclerViewHost
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.userinterfacethemesandroid.UIThemesService
import bogdandonduk.androidlibs.viewbindingutilsandroid.ViewBinder
import bogdandonduk.androidlibs.viewmodelutilsandroid.ViewModelHost
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.r0adkll.slidr.Slidr
import pro.filemanager.R
import pro.filemanager.core.GenericViewModelFactory
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.databinding.ActivityImageLibraryBinding

class ImageLibraryActivity : BaseActivity(), ViewBinder<ActivityImageLibraryBinding>,
    PermissionRequesterActivity,
    ViewModelHost<ImageLibraryActivityViewModel>,
    RecyclerViewHost {
    override var viewBinding: ActivityImageLibraryBinding? = null
    override val viewBindingInitialization: () -> ActivityImageLibraryBinding = {
        ActivityImageLibraryBinding.inflate(layoutInflater)
    }

    override var viewModel: ImageLibraryActivityViewModel? = null
    override val viewModelInitialization: () -> ImageLibraryActivityViewModel = {
        ViewModelProvider(this, GenericViewModelFactory { ImageLibraryActivityViewModel() }).get(ImageLibraryActivityViewModel::class.java)
    }

    var manageStorageRationaleTag: String = "manageStorageRationale"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(getCurrentViewBinding()) {
            setContentView(getCurrentViewBinding().root)

            slidrInterface = Slidr.attach(this@ImageLibraryActivity)

            rootView = root

            actionBarRootAppBarLayout = activityImageLibraryActionbarInclude.root

            toolbar = activityImageLibraryActionbarInclude.layoutStandardToolbarToolbar

            initializeActionBarWithBackNavigation(
                activity = this@ImageLibraryActivity,
                toolbar = activityImageLibraryActionbarInclude.layoutStandardToolbarToolbar
            )
        }

        requestPermissions()
    }

    @SuppressLint("InlinedApi", "ShowToast")
    override fun requestPermissions() {
        val rationaleAction = { permissionRequestAction: () -> Int? ->
            val backgroundColor = UIThemesService.resolveColorResource(this, R.color.accent)

            val textColor = UIThemesService.resolveColorResource(this, R.color.text)

            val clickableTextColor = UIThemesService.resolveColorResource(this, R.color.clickable_text)

            val textItems = mutableListOf<BottomSheetModalAnatomy.TextItem>().apply {
                add(
                    BottomSheetModalAnatomy.TextItem(
                        UILanguagesService.resolveStringResource(this@ImageLibraryActivity, R.string.it_is_required_for_file_system_access_and_management),
                        textColor
                    )
                )
            }

            BottomSheetModalsService
                .getSimpleModalBuilder(manageStorageRationaleTag)
                .setBackgroundColor(backgroundColor)
                .setTitle(
                    BottomSheetModalAnatomy.TextItem(
                        UILanguagesService.resolveStringResource(this, R.string.no_storage_permission),
                        textColor
                    )
                )
                .setTextItems(textItems)
                .setPositiveButton(
                    BottomSheetModalAnatomy.ButtonItem(
                        UILanguagesService.resolveStringResource(this@ImageLibraryActivity, R.string.allow),
                        clickableTextColor
                    ) { _: View, bottomSheetDialogFragment: BottomSheetDialogFragment ->
                        bottomSheetDialogFragment.dismiss()
                        getCurrentViewModel().requestedPermissionsCodesMap[Manifest.permission.MANAGE_EXTERNAL_STORAGE] = permissionRequestAction.invoke()
                    }
                )
                .setNegativeButton(
                    BottomSheetModalAnatomy.ButtonItem(
                        UILanguagesService.resolveStringResource(this@ImageLibraryActivity, R.string.deny),
                        clickableTextColor
                    ) { _: View, bottomSheetDialogFragment: BottomSheetDialogFragment ->
                        bottomSheetDialogFragment.dismiss()
                        finish()
                    }
                )
                .setOnCancelAction {
                    finish()
                }
                .setModalContextPopupMenu(
                    BottomSheetModalAnatomy.Popup.ModalContext.getMenuBuilder()
                        .setBackgroundColor(UIThemesService.resolveColorResource(this, R.color.accent, !UIThemesService.isDarkThemeEnabled(this)))
                        .addItems(
                            mutableListOf<BottomSheetModalAnatomy.Popup.Item>().apply {
                                add(
                                    BottomSheetModalAnatomy.Popup.ModalContext.DefaultItems.ItemCopy(
                                        context = this@ImageLibraryActivity,
                                        text = UILanguagesService.resolveStringResource(this@ImageLibraryActivity, R.string.copy_text),
                                        textColor = textColor,
                                        textItems = textItems,
                                        toast = Toast.makeText(this@ImageLibraryActivity, UILanguagesService.resolveStringResource(this@ImageLibraryActivity, R.string.text_copied), Toast.LENGTH_LONG)
                                    )
                                )
                            }
                        )
                        .build()

                )
                .show(supportFragmentManager)
        }

        getCurrentViewModel().requestedPermissionsCodesMap[Manifest.permission.MANAGE_EXTERNAL_STORAGE] = PermissionsService.requestManageStorage(
            activity = this,
            deniedRationaleAction = rationaleAction,
            doNotAskAgainRationaleAction = rationaleAction,
            api30Action = rationaleAction
        ) {
            launchCore()
        }
    }

    override fun launchCore() {
        Toast.makeText(this, "SHIT", Toast.LENGTH_SHORT).show()
    }

    override fun initializeLanguage() {
        super.initializeLanguage()

        UILanguagesService.initializeActionBarTitleLanguage(
            context = this,
            toolbar = toolbar,
            R.string.images
        )

        UILanguagesService.initializeHomeAsUpIndicatorTooltipLanguage(
            activity = this,
            toolbar,
            R.string.go_back
        )
    }

    override fun initializeTheme() {
        super.initializeTheme()

        BottomSheetModalsService.getModalArgReferenceForTag<SimpleBottomSheetModalArgReference>(manageStorageRationaleTag)?.run {
            if(modal != null) {
                UIThemesService.resolveColorResource(this@ImageLibraryActivity, R.color.accent).run {
                    backgroundColor = this
                    modalContextPopupMenu.backgroundColor = this
                }


                UIThemesService.resolveColorResource(this@ImageLibraryActivity, R.color.text).run {
                    title.textColor = this

                    textItems.forEach {
                        it.textColor = this
                    }

                    modalContextPopupMenu.items.forEach {
                        it.textColor = this
                    }
                }

                UIThemesService.resolveColorResource(this@ImageLibraryActivity, R.color.clickable_text).run {
                    positiveButton.textColor = this
                    negativeButton.textColor = this
                }


                modal!!.redraw()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        PermissionsService.handleReturnFromAppSettingsForManageStorage(
            this,
            {
                finish()
            }
        ) {
            launchCore()
        }
    }

    @SuppressLint("InlinedApi")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        getCurrentViewModel().requestedPermissionsCodesMap[Manifest.permission.MANAGE_EXTERNAL_STORAGE]?.run {
            PermissionsService.handlePermissionsRequestResult(
                activity = this@ImageLibraryActivity,
                responseRequestCode = requestCode,
                requestCode = this,
                grantResults = grantResults,
                permissions = permissions
            ).run {
                allowedPermissions.run {
                    if(contains(Manifest.permission.READ_EXTERNAL_STORAGE) && contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        launchCore()
                    else
                        finish()
                }
            }
        }
    }
}