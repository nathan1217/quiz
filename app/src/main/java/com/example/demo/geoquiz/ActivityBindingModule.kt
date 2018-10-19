package com.example.demo.geoquiz

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun quizActivity(): QuizActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun cheatingActivity(): CheatingActivity
}