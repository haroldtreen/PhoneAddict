package com.appattack.phoneaddict;

import com.appattack.phoneaddict.services.ServiceUtilities;
import com.google.inject.AbstractModule;

import org.mockito.Mockito;

public class TestInjectionModule extends AbstractModule{

    @Override
    protected void configure(){
        bind(ServiceUtilities.class).toInstance(Mockito.mock(ServiceUtilities.class));
    }
}
