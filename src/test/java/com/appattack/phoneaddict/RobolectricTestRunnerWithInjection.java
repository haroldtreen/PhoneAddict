package com.appattack.phoneaddict;

import com.google.inject.Module;

import org.junit.runners.model.InitializationError;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;

import java.lang.reflect.Method;

import roboguice.RoboGuice;

import static roboguice.RoboGuice.getInjector;
import static roboguice.RoboGuice.setBaseApplicationInjector;

public class RobolectricTestRunnerWithInjection extends RobolectricTestRunner{

    public RobolectricTestRunnerWithInjection(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {
        return TestLifeCycleWithInjection.class;
    }

    public static class TestLifeCycleWithInjection extends DefaultTestLifecycle{
        @Override
        public void beforeTest(Method method){
            super.beforeTest(method);

            setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, getModules());
        }

        @Override
        public void prepareTest(Object test){
            getInjector(Robolectric.application).injectMembers(test);
        }

        private Module[] getModules(){
            TestInjectionModule testInjectionModule = new TestInjectionModule();
            Module defaultModule = RoboGuice.newDefaultRoboModule(Robolectric.application);

            Module[] modules = new Module[]{ defaultModule, testInjectionModule };

            return modules;
        }
    }
}
