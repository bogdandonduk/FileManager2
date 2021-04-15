package pro.filemanager.core.vibration

import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import pro.filemanager.FileManager
import pro.filemanager.core.preferences.PreferencesPlant

@SuppressLint("NewApi")
object VibrationPlant {
    fun vibrateOneShot(context: Context = FileManager.context, duration: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        PreferencesPlant.differentiateApiLevel(
                apiLevel = 26,
                higherOrEqualAction = {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(duration, amplitude))
                },
                lowerAction = {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(50)
                }
        )
    }
}