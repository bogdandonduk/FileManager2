package pro.filemanager.core.dragswipe

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

interface SwiperAdapter<T> {
    var items: MutableList<T>

    @CallSuper
    fun onItemDismiss(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, position: Int) {
        items.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}