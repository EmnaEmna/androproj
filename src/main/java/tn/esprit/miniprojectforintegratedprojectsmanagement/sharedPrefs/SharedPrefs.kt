package tn.esprit.miniprojectforintegratedprojectsmanagement.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

@Suppress("UNCHECKED_CAST")
class SharedPrefs(context: Context) {
    companion object {
        private const val PREF = "local_prefs"
        private const val TOKEN = "token"
        private const val CURRENT_USER= "current_user"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveToken(token: String) {
        sharedPref.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String? = sharedPref.getString(TOKEN, null)

    /*fun saveCurrentUser(user: UserEntity) {
        sharedPref.edit().putString(CURRENT_USER, gson.toJson(user)).apply()
    }

    fun getCurrentUser(): UserEntity? = gson.fromJson(sharedPref.getString(CURRENT_USER, null), UserEntity::class.java)*/

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().clear().apply()
    }
}