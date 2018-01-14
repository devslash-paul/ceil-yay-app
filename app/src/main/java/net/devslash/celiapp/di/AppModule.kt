package net.devslash.celiapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import net.devslash.celiapp.barcode.net.BarcodeDao
import net.devslash.celiapp.ui.main.MainActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module(subcomponents = [
        MainActivityComponent::class
])
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

    @Provides
    @Singleton
    fun providesRetroBuilder(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl("http://abc.net.au")
                .build()
    }

    @Provides
    @Singleton
    fun provideBarcodeDao(retro: Retrofit): BarcodeDao {
         return retro.create(BarcodeDao::class.java)
    }
}