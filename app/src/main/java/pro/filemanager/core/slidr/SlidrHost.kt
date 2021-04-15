package pro.filemanager.core.slidr

import android.annotation.SuppressLint
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import com.r0adkll.slidr.model.SlidrInterface
import pro.filemanager.core.exceptions.ExceptionsPlant

@SuppressLint("RtlHardcoded")
interface SlidrHost {
    var slidrInterface: SlidrInterface?

    fun initializeTransitions(window: Window, edge: Int = Gravity.END) {
        val slideTransition = Slide().apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }

        ExceptionsPlant.run(
                tryAction = {
                    slideTransition.slideEdge = edge

                    window.enterTransition = slideTransition
                    window.exitTransition = slideTransition
                },
                exceptionalActions = arrayOf(
                        Pair(IllegalArgumentException::class.java) {
                            slideTransition.slideEdge = Gravity.RIGHT

                            window.enterTransition = slideTransition
                            window.exitTransition = slideTransition
                        }
                )
        )
    }
}