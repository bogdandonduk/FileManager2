package pro.filemanager.core.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import pro.filemanager.FileManager

object PreferencesPlant {
    private const val KEY_PREFERENCES = "pro.filemanager.preferences"
    private const val KEY_SHARED_PREFERENCES = "pro.filemanager.shared_preferences"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = KEY_PREFERENCES)

    fun getSharedPreferences(context: Context = FileManager.context) : SharedPreferences = context.getSharedPreferences(
        KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun differentiateApiLevel(apiLevel: Int, higherOrEqualAction: () -> Unit, lowerAction: () -> Unit) {
        if(Build.VERSION.SDK_INT >= apiLevel)
            higherOrEqualAction.invoke()
        else
            lowerAction.invoke()
    }
}