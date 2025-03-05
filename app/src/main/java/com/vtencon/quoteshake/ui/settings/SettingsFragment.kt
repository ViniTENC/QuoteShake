package com.vtencon.quoteshake.ui.settings
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.vtencon.quoteshake.R
import com.vtencon.quoteshake.ui.data.settings.SettingsPreferenceDataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var settingsPreferenceDataStore: SettingsPreferenceDataStore
    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)    }
}