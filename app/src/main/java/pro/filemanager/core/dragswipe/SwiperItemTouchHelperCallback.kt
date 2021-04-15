package pro.filemanager.core.dragswipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class SwiperItemTouchHelperCallback<T>(private val swiperAdapter: SwiperAdapter<T>) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.DOWN)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = true

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swiperAdapter.onItemDismiss(
                adapter = swiperAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                position = viewHolder.adapterPosition
        )
    }

    override fun isLongPressDragEnabled() = false

    override fun isItemViewSwipeEnabled() = true
}