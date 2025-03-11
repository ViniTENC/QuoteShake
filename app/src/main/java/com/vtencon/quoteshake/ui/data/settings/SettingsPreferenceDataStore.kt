package com.vtencon.quoteshake.ui.data.settings

import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsPreferenceDataStore @Inject constructor(
    private val settingsRepository: SettingsRepository
) : PreferenceDataStore() {

    override fun putString(key: String?, value: String?) {
        if (key != null && value != null) {
            CoroutineScope(Dispatchers.IO).launch {
                when (key) {
                    "language" -> settingsRepository.setLanguage(value)
                    "username" -> settingsRepository.setUserName(value)
                }
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String? {
        return when (key) {
            "language" -> runBlocking(Dispatchers.IO) { settingsRepository.getLanguageSnapshot() }
            "username" -> runBlocking(Dispatchers.IO) { settingsRepository.getUserNameSnapshot() }
            else -> defValue
        }
    }
}
