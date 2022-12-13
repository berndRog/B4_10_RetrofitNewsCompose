package de.rogallab.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import de.rogallab.android.data.repository.NewsRepositoryImpl
import de.rogallab.android.domain.INewsRepository
import de.rogallab.android.domain.INewsUseCases
import de.rogallab.android.domain.usecases.NewsUseCasesImpl

import javax.inject.Singleton

// @Binds Shothand for binding an interface type

@InstallIn(ViewModelComponent::class)
@Module
abstract class AppBindViewModelModules {
   @ViewModelScoped
   @Binds
   abstract fun bindNewsUseCases(
      newsUseCases: NewsUseCasesImpl
   ): INewsUseCases
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsSingletonModules {
   @Binds
   @Singleton
   abstract fun bindTasksRepository(
      newsRepositoryImpl: NewsRepositoryImpl
   ): INewsRepository
}

//@InstallIn(ActivityComponent::class)
//@Module
//abstract class FreeActivityModule {
//
//   @ActivityScoped
//   @Binds
//   @FreeModule
//   abstract fun bindFreeModule(impl: FreeActivity): YourInterface
//}