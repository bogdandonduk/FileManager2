package pro.filemanager.home.sections

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.VibrationUtils
import bogdandonduk.androidlibs.recyclerviewutilsandroid.dragswipe.DraggerAdapter
import bogdandonduk.androidlibs.userinterfacelanguagesandroid.UILanguagesService
import bogdandonduk.androidlibs.userinterfacethemesandroid.UIThemesService
import bogdandonduk.androidlibs.viewbindingutilsandroid.ViewBinder
import pro.filemanager.R
import pro.filemanager.core.base.activity.BaseActivity
import pro.filemanager.databinding.LayoutHomeSectionItemBinding

class HomeSectionsAdapter(override var items: MutableList<HomeSectionsItem>, val hostActivity: BaseActivity) : RecyclerView.Adapter<HomeSectionsAdapter.ViewHolder>(),
    DraggerAdapter<HomeSectionsItem> {
    inner class ViewHolder(
            override val viewBindingInitialization: () -> LayoutHomeSectionItemBinding,
            override var viewBinding: LayoutHomeSectionItemBinding? = viewBindingInitialization.invoke()
    ) : RecyclerView.ViewHolder(viewBinding!!.root), ViewBinder<LayoutHomeSectionItemBinding> {
        lateinit var item: HomeSectionsItem
        fun isItemInitialized() = this::item.isInitialized

        init {
            getCurrentViewBinding().layoutHomeSectionItemSelectableContainerLinearLayout.run {
                setOnClickListener {
                    if(isItemInitialized()) item.clickAction.invoke(getCurrentViewBinding().root, hostActivity)
                }

                setOnLongClickListener {
                    VibrationUtils.vibrateOneShot(context = hostActivity, duration = 50)

                    false
                }
            }
        }
    }

    override fun onItemMove(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, fromPosition: Int, toPosition: Int): Boolean =
        super.onItemMove(adapter, fromPosition, toPosition).apply {
            HomeSectionsPlant.setDisplayedSectionItems(items = mutableListOf<String>().apply {
                items.forEach {
                    add(it.persistableKey)
                }
            })
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder({
        LayoutHomeSectionItemBinding.inflate(hostActivity.layoutInflater, parent, false)
    })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = items[position]

        UIThemesService.initializeViewBackgroundColor(
            context = hostActivity,
            colorResId = R.color.accent,
            views = arrayOf(holder.getCurrentViewBinding().root)
        )

        holder.getCurrentViewBinding().layoutHomeSectionItemTitleTextView.run {
            UIThemesService.initializeTextColor(
                context = hostActivity,
                colorResId = R.color.text,
                texts = arrayOf(this),
            )

            UILanguagesService.initializeTextLanguage(
                context = hostActivity,
                text = this,
                stringResId = holder.item.titleResId
            )
        }

        holder.item.initializationAction.invoke(holder.getCurrentViewBinding().root)
    }

    override fun getItemCount(): Int = items.size
}