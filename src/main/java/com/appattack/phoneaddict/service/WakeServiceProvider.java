package com.appattack.phoneaddict.service;

import com.google.inject.Singleton;

import com.google.inject.Provider;

import roboguice.inject.ContextSingleton;

@Singleton
public class WakeServiceProvider implements Provider<WakeService>{

    public WakeService get(){
        return new WakeService();
    }
}
