package ru.yourok.torrserve.settings

import android.os.Environment
import android.preference.PreferenceManager
import ru.yourok.torrserve.app.App
import java.io.File

object Settings {

    fun getPlayer(): String = get("player", "")
    fun getPlayer(v: String) = set("player", v)

    fun getChooserAction(): Int = get("chooser_action", 0)
    fun setChooserAction(v: Int) = set("chooser_action", v)

    fun isBootStart(): Boolean = get("boot_start", false)
    fun setBootStart(v: Boolean) = set("boot_start", v)

    fun isRootStart(): Boolean = get("root_start", false)
    fun setRootStart(v: Boolean) = set("root_start", v)

    // "http://192.168.43.46:8090"
    // get("host", "http://127.0.0.1:8090")
    fun getHost(): String = "http://10.0.0.10:8090"
    fun setHost(host: String) = set("host", host)


    /////////////////////////////////////////////////////////
    fun getTorrPath(): String {
        val state = Environment.getExternalStorageState()
        var filesDir: File?
        if (Environment.MEDIA_MOUNTED == state)
            filesDir = App.context.getExternalFilesDir(null)
        else
            filesDir = App.context.filesDir

        if (filesDir == null)
            filesDir = App.context.cacheDir

        if (filesDir == null)
            filesDir = File("/sdcard/Torrserve")

        if (!filesDir.exists())
            filesDir.mkdirs()
        return filesDir.path
    }
/////////////////////////////////////////////////////////

    fun <T> get(name: String, def: T): T {
        try {
            val prefs = PreferenceManager.getDefaultSharedPreferences(App.context)
            if (prefs.all.containsKey(name))
                return prefs.all[name] as T
            return def
        } catch (e: Exception) {
            return def
        }
    }

    private fun set(name: String, value: Any?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(App.context)
        when (value) {
            is String -> prefs.edit().putString(name, value).apply()
            is Boolean -> prefs.edit().putBoolean(name, value).apply()
            is Float -> prefs.edit().putFloat(name, value).apply()
            is Int -> prefs.edit().putInt(name, value).apply()
            is Long -> prefs.edit().putLong(name, value).apply()
            is MutableSet<*>? -> prefs.edit().putStringSet(name, value as MutableSet<String>?).apply()
            else -> prefs.edit().putString(name, value.toString()).apply()
        }
    }
}
