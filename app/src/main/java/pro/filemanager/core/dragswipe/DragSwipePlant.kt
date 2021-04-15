package pro.filemanager.core.dragswipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

object DragSwipePlant {
    fun <T> initializeDraggerAdapter(recyclerView: RecyclerView, draggerAdapter: DraggerAdapter<T>) {
        ItemTouchHelper(DraggerItemTouchHelperCallback(draggerAdapter)).attachToRecyclerView(recyclerView)
    }
}