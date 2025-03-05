package com.vtencon.quoteshake.ui.data.settings

import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsPreferenceDataStore @Inject constructor(val settingsRepository : SettingsRepository ) : PreferenceDataStore(){
    override fun putString(key: String?, value: String?) {
        if (key != null && value != null) {
            CoroutineScope(Dispatchers.IO).launch {
                settingsRepository.saveSetting(key, value)
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String? {
        return if (key != null) {
            runBlocking(Dispatchers.IO) {
                settingsRepository.getSetting(key) ?: defValue
            }
        } else {
            defValue
        }
    }
}