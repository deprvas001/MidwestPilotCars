package com.midwestpilotcars.dependencyInjection


import com.midwestpilotcars.MidwestPilotCars
import com.watcho.network.ApiUtils
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [DataModule::class])
interface DaggerComponent {
    fun inject(midwestPilotCars: MidwestPilotCars)
//    fun inject(splashViewModel: SplashActivityViewModel)
//    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
//    fun inject(videoDetailsViewModel: VideoDetailsViewModel)
    fun inject(apiUtils: ApiUtils)
}

