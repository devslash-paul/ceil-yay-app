package net.devslash.celiapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject
import javax.inject.Named

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), HasFragmentInjector {

    @Inject
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    lateinit var fragmentManagerIn: FragmentManager

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        fragmentManagerIn.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }
}

@Module
abstract class BaseActivityModule {

    @Module
    companion object {
        const val ACTIVITY_FRAGMENT_MANAGER: String = "BaseActivityModule.activityFragmentManager"

        @Provides
        @Named(ACTIVITY_FRAGMENT_MANAGER)
        @PerActivity
        fun activityFragmentManager(activity: Activity): FragmentManager {
            return activity.fragmentManager
        }
    }

    @Binds
    abstract fun activityContext(activity: Activity): Context

}
