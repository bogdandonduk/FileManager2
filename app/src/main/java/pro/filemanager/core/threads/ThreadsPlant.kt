package pro.filemanager.core.threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job

object ThreadsPlant {
    val globalCoroutineScopeIO = CoroutineScope(IO)
    val globalCoroutineScopeIOJobs = mutableMapOf<String, Job>()

    val globalCoroutineScopeDefault = CoroutineScope(Default)
    val globalCoroutineScopeDefaultJobs = mutableMapOf<String, Job>()

    val globalCoroutineScopeMain = CoroutineScope(Main)
    val globalCoroutineScopeMainJobs = mutableMapOf<String, Job>()

    val globalCoroutineScopeMainImmediate = CoroutineScope(Main.immediate)
    val globalCoroutineScopeMainImmediateJobs = mutableMapOf<String, Job>()
}