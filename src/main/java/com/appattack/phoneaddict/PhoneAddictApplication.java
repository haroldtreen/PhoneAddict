package com.appattack.phoneaddict;

import android.app.Application;

import com.google.inject.Module;

import roboguice.RoboGuice;

public class PhoneAddictApplication extends Application {

    public void onCreate(){
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, getModules());
    }

    private Module[] getModules(){
        InjectionModule injectionModule = new InjectionModule();
        Module defaultModule = RoboGuice.newDefaultRoboModule(this);

        Module[] modules = new Module[]{ defaultModule, injectionModule };

        return modules;
    }
}
