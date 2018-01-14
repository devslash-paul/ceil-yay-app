package net.devslash.celiapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.devslash.celiapp.ui.main.MainActivityComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(subcomponents = arrayOf(
        MainActivityComponent::class
))
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .build()
    }
}