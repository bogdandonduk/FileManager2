package pro.filemanager.core.lists

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pro.filemanager.core.threads.ThreadsPlant

interface ListHost {
    fun initializeList(
            recyclerView: RecyclerView,
            adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
            layoutManager: RecyclerView.LayoutManager? = null,
            changeAnimationsEnabled: Boolean = false
    ) {
        with(recyclerView) {
            if(layoutManager != null) this.layoutManager = layoutManager

            this.adapter = adapter

            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = changeAnimationsEnabled
        }
    }

    fun updateList(vararg adapters: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
        adapters.forEach {
            ThreadsPlant.globalCoroutineScopeMain.launch {
                for(i in 0 until it!!.itemCount) {
                    it.notifyItemChanged(i)
                }
            }
        }
    }
}