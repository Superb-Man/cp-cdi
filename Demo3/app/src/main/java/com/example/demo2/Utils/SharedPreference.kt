import android.content.Context

class SharedPrefManager(context: Context) {

    private val prefs = context.getSharedPreferences("health_data_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val EXPIRY_TIME_MS = 3 * 60 * 1000
        private const val KEY_TIMESTAMP = "save_time"
    }

    fun save(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
        prefs.edit().putLong(KEY_TIMESTAMP, System.currentTimeMillis()).apply()
    }

    fun get(key: String): String? {
        val savedTime = prefs.getLong(KEY_TIMESTAMP, 0L)
        if (System.currentTimeMillis() - savedTime > EXPIRY_TIME_MS) {
            clear()
            return null
        }
        return prefs.getString(key, null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}

