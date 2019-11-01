package com.midwestpilotcars

import android.app.Application

import com.midwestpilotcars.dependencyInjection.DaggerComponent
import com.midwestpilotcars.dependencyInjection.DaggerDaggerComponent
import com.midwestpilotcars.dependencyInjection.DataModule
import javax.inject.Singleton

@Singleton
class MidwestPilotCars : Application() {

    init {
        midwestPilotCars = this
    }

    var daggerComponent: DaggerComponent? = null
    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
        daggerComponent!!.inject(midwestPilotCars!!)
    }

    companion object {
        private var midwestPilotCars: MidwestPilotCars? = null
        fun getInstance(): MidwestPilotCars {
            if(midwestPilotCars == null)
                midwestPilotCars = MidwestPilotCars()
            return midwestPilotCars!!
        }

    }

    private fun initDaggerComponent() {
        daggerComponent = DaggerDaggerComponent.builder()
                .dataModule(DataModule(this))
                .build()
    }
}
