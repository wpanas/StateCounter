package com.github.wpanas.statecounter.action

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActionFragmentProvider {
    @ContributesAndroidInjector
    abstract fun actionListFragmentProvider(): ActionListFragment
}