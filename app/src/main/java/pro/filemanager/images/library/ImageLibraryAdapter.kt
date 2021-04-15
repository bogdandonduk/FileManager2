package pro.filemanager.images.library

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pro.filemanager.core.base.BaseActivity
import pro.filemanager.core.base.ViewBinder
import pro.filemanager.databinding.LayoutImageLibraryItemBinding

class ImageLibraryAdapter(val hostActivity: BaseActivity) : RecyclerView.Adapter<ImageLibraryAdapter.ViewHolder>() {
    inner class ViewHolder(
            override val viewBindingInitialization: () -> LayoutImageLibraryItemBinding,
            override var viewBinding: LayoutImageLibraryItemBinding? = viewBindingInitialization.invoke()
    ) : RecyclerView.ViewHolder(viewBinding!!.root), ViewBinder<LayoutImageLibraryItemBinding> {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder({
        LayoutImageLibraryItemBinding.inflate(hostActivity.layoutInflater, parent, false)
    })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getCurrentViewBinding().layoutImageLibraryTitleTextView.text = position.toString()
    }

    override fun getItemCount(): Int = 50
}