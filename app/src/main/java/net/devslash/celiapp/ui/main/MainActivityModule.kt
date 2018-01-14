package net.devslash.celiapp.ui.main

import android.view.View
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import net.devslash.celiapp.SingletonUtil

@Module class MainActivityModule {

    @Provides
    fun provideMainView(activity: MainActivity): View {
        return activity.imgview
    }

    @Provides
    fun provideSingletonUtil(): SingletonUtil {
        return SingletonUtil()
    }
}
