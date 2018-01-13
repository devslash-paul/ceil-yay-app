package net.devslash.celiapp

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Build
import android.support.annotation.IdRes
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject
import javax.inject.Named

abstract class BaseFragment : Fragment(), HasFragmentInjector {

    @Inject
    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    protected lateinit var childFragmentManagerIn: FragmentManager


    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(activity: Activity?) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidInjection.inject(this)
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context?) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidInjection.inject(this)
        }
        super.onAttach(context)
    }

    protected fun addChildFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        childFragmentManagerIn.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }
}

@Module abstract class BaseFragmentModule {

    @Module companion object {
        const val FRAGMENT = "BaseFragmentModule.fragment"
        const val CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager"

        @Provides
        @Named(CHILD_FRAGMENT_MANAGER)
        @PerFragment
        fun childFragmentManger(@Named(FRAGMENT) fragment: Fragment): FragmentManager? {
            return fragment.childFragmentManager
        }
    }
}

@Module abstract class BaseChildFragmentModule {
    companion object {
        const val CHILD_FRAGMENT = "BaseChildFragmentModule.childFragment"
    }
}

