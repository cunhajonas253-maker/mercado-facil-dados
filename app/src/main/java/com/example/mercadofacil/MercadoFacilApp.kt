package com.example.mercadofacil

import android.app.Application
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration

class MercadoFacilApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa OSMDroid com as preferências do app
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
    }
}
