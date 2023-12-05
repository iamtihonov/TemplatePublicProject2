package ua.artem.template.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.artem.template.utils.IViewUtil
import ua.artem.template.utils.ViewUtil
import ua.good.utils.IResourcesUtil
import ua.good.utils.ResourcesUtil

/**
 * Dagger модуль с жизненным циклом FragmentComponent
 */
@Module
@InstallIn(FragmentComponent::class)
class AndroidModule {

    @Provides
    fun provideViewUtil(@ApplicationContext context: Context): IViewUtil {
        return ViewUtil(context)
    }

    @Provides
    fun provideResourcesUtil(@ApplicationContext context: Context): IResourcesUtil {
        return ResourcesUtil(context)
    }
}
