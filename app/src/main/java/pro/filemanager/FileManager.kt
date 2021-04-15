package pro.filemanager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import java.util.*

@SuppressLint("StaticFieldLeak")
class FileManager : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        Log.d("TAG", "onCreate: " + Locale.getDefault().language)


    }
}