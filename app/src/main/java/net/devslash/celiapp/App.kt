package net.devslash.celiapp

import android.app.Activity
import android.app.Application
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}

@Module(includes = [AndroidInjectionModule::class])
abstract class AppModule

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerChildFragment
