package pro.filemanager.core.languages

import android.content.Context
import pro.filemanager.FileManager
import pro.filemanager.core.preferences.PreferencesPlant
import java.util.*

object LanguagesPlant {
    private const val KEY_SP_APP_LANGUAGE = "appLanguage"

    fun getAppLanguage(context: Context = FileManager.context) = PreferencesPlant.getSharedPreferences(context).getString(KEY_SP_APP_LANGUAGE, Locale.getDefault().language)

    fun setAppLanguage(context: Context = FileManager.context, value: String) {
        PreferencesPlant.getSharedPreferences(context).edit().putString(KEY_SP_APP_LANGUAGE, value).apply()
    }
}