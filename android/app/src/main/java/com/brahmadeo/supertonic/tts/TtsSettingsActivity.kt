package com.brahmadeo.supertonic.tts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TtsSettingsActivity : AppCompatActivity() {

    private lateinit var currentVoiceValue: TextView
    private lateinit var currentLanguageValue: TextView
    private lateinit var currentQualityValue: TextView
    private lateinit var openAppButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tts_settings)

        currentVoiceValue = findViewById(R.id.currentVoiceValue)
        currentLanguageValue = findViewById(R.id.currentLanguageValue)
        currentQualityValue = findViewById(R.id.currentQualityValue)
        openAppButton = findViewById(R.id.openAppButton)

        openAppButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        populateSettings()
    }

    private fun populateSettings() {
        val prefs = getSharedPreferences("SupertonicPrefs", Context.MODE_PRIVATE)
        val voiceFile = prefs.getString("selected_voice", "M1.json") ?: "M1.json"
        val voiceName = voiceFile.removeSuffix(".json")
        val language = prefs.getString("selected_lang", "en") ?: "en"
        val steps = prefs.getInt("diffusion_steps", 5)

        currentVoiceValue.text = voiceName
        currentLanguageValue.text = when (language) {
            "ko" -> getString(R.string.language_korean)
            "es" -> getString(R.string.language_spanish)
            "pt" -> getString(R.string.language_portuguese)
            "fr" -> getString(R.string.language_french)
            else -> getString(R.string.language_english)
        }
        currentQualityValue.text = getString(R.string.quality_steps_fmt, steps)
    }
}
