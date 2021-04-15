package pro.filemanager.core.dragswipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class DraggerItemTouchHelperCallback<T>(private val draggerAdapter: DraggerAdapter<T>) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.DOWN)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) : Boolean =
            draggerAdapter.onItemMove(
                adapter = draggerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                fromPosition = viewHolder.adapterPosition,
                toPosition = target.adapterPosition
            )

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { }

    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = false
}