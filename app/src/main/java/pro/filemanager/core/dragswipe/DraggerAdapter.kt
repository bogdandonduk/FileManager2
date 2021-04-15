package pro.filemanager.core.dragswipe

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

interface DraggerAdapter<T> {
    var items: MutableList<T>

    @CallSuper
    fun onItemMove(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, fromPosition: Int, toPosition: Int) : Boolean {
        if(fromPosition < toPosition)
            for(i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        else
            for(i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }

        adapter.notifyItemMoved(fromPosition, toPosition)

        return true
    }
}