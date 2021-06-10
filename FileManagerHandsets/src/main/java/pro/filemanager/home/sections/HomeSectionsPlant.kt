package pro.filemanager.home.sections

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.PreferencesUtils
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.TransactionsUtils
import pro.filemanager.FileManager
import pro.filemanager.R
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.images.library.ImageLibraryActivity

@SuppressLint("NewApi")
object HomeSectionsPlant {
    private const val KEY_SP_DISPLAYED_SECTIONS_ITEMS = "displayedSectionsItems"
    private const val KEY_SP_SECTION_STORAGE = "defaultSectionStorageDisks"
    private const val KEY_SP_SECTION_IMAGES = "defaultSectionImages"
    private const val KEY_SP_SECTION_VIDEO = "defaultSectionVideo"
    private const val KEY_SP_SECTION_AUDIO = "defaultSectionAudio"
    private const val KEY_SP_SECTION_DOCUMENTS = "defaultSectionDocuments"
    private const val KEY_SP_SECTION_DOWNLOADS = "defaultSectionDownloads"
    private const val KEY_SP_SECTION_APKS = "defaultSectionApks"

    const val defaultKeys = "$KEY_SP_SECTION_STORAGE`$KEY_SP_SECTION_IMAGES`$KEY_SP_SECTION_VIDEO`$KEY_SP_SECTION_AUDIO`$KEY_SP_SECTION_DOCUMENTS`$KEY_SP_SECTION_DOWNLOADS`$KEY_SP_SECTION_APKS"

    val defaultItems = mutableListOf<HomeSectionsItem>().apply {
        add(HomeSectionsItem(
                titleResId = R.string.storage,
                persistableKey = KEY_SP_SECTION_STORAGE,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.images,
                persistableKey = KEY_SP_SECTION_IMAGES,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->
                    TransactionsUtils.openActivity(
                        context = baseActivity,
                        activityClass = ImageLibraryActivity::class.java,
                        currentActivityTracker = FileManager.currentActivity
                    )
                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.video,
                persistableKey = KEY_SP_SECTION_VIDEO,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.audio,
                persistableKey = KEY_SP_SECTION_AUDIO,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.documents,
                persistableKey = KEY_SP_SECTION_DOCUMENTS,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.downloads,
                persistableKey = KEY_SP_SECTION_DOWNLOADS,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))

        add(HomeSectionsItem(
                titleResId = R.string.apks,
                persistableKey = KEY_SP_SECTION_APKS,
                initializationAction = {

                },
                clickAction = { view: View, baseActivity: BaseActivity ->

                }
        ))
    }

    fun getDisplayedSectionsItems(context: Context = FileManager.context) : MutableList<String> =
            PreferencesUtils.getSharedPreferences(context).getString(KEY_SP_DISPLAYED_SECTIONS_ITEMS, defaultKeys)!!.split("`").toMutableList()

    fun setDisplayedSectionItems(context: Context = FileManager.context, items: MutableList<String>) {
        PreferencesUtils.getSharedPreferences(context).edit().putString(KEY_SP_DISPLAYED_SECTIONS_ITEMS, StringBuilder("").apply {
            items.forEachIndexed { i: Int, s: String ->
                append(if(i != 0) "`$s" else s)
            }
        }.toString()).apply()
    }

    fun getHomeSectionsItems(context: Context = FileManager.context) = mutableListOf<HomeSectionsItem>().apply {
        val sectionItems = getDisplayedSectionsItems(context)

        with(sectionItems) {
            forEach { key ->
                defaultItems.forEach {
                    if(it.persistableKey.equals(key, true)) add(it)
                }
            }
        }
    }
}