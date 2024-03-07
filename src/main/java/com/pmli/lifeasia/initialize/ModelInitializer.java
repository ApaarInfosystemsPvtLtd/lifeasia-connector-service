package com.pmli.lifeasia.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ModelInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    MasterStore masterStore;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        masterStore.initialize();
    }
}
