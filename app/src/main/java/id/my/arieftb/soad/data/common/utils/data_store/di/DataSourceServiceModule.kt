package id.my.arieftb.soad.data.common.utils.data_store.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.common.utils.data_store.service.AuthPreferenceService
import id.my.arieftb.soad.data.common.utils.data_store.service.AuthPreferenceServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceServiceModule {
    @Provides
    @Singleton
    fun provideAuthPreferenceService(
        @ApplicationContext context: Context
    ): AuthPreferenceService {
        return AuthPreferenceServiceImpl(context)
    }
}
