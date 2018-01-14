package net.devslash.celiapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.devslash.celiapp.ui.main.MainActivity
import net.devslash.celiapp.ui.main.MainActivityModule

@Module abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules=arrayOf(MainActivityModule::class))
    abstract fun bindMainActivitiy(): MainActivity
}
