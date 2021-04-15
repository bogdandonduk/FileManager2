package pro.filemanager.home.sections

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.core.base.ViewBinder
import pro.filemanager.core.dragswipe.DraggerAdapter
import pro.filemanager.core.themes.ThemesPlant
import pro.filemanager.core.vibration.VibrationPlant
import pro.filemanager.databinding.LayoutHomeSectionItemBinding

class HomeSectionsAdapter(override var items: MutableList<HomeSectionsItem>, val hostActivity: BaseActivity) : RecyclerView.Adapter<HomeSectionsAdapter.ViewHolder>(), DraggerAdapter<HomeSectionsItem> {
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
                    VibrationPlant.vibrateOneShot(duration = 50)

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

        ThemesPlant.initializeViewBackgroundColorAccent(views = arrayOf(
                holder.getCurrentViewBinding().root
        ))

        holder.getCurrentViewBinding().layoutHomeSectionItemTitleTextView.run {
            ThemesPlant.initializeText(textViews = arrayOf(this))
            text = hostActivity.getString(holder.item.titleResId)
        }

        holder.item.initializationAction.invoke(holder.getCurrentViewBinding().root)
    }

    override fun getItemCount(): Int = items.size
}