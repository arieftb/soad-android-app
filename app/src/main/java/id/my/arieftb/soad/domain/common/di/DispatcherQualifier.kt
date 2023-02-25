package id.my.arieftb.soad.domain.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO
