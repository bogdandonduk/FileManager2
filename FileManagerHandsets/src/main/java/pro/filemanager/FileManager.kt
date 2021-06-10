package pro.filemanager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import pro.filemanager.core.base.activity.BaseActivity

@SuppressLint("StaticFieldLeak")
class FileManager : Application() {
    companion object {
        lateinit var context: Context
        var currentActivity: BaseActivity? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }
}