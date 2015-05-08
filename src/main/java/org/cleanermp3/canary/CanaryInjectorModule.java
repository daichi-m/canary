package org.cleanermp3.canary;

import com.google.inject.AbstractModule;
import org.cleanermp3.canary.filter.CanarySieveModule;
import org.cleanermp3.canary.normalizer.CanaryNormalizerModule;
import org.cleanermp3.canary.processor.CanaryFlight;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryInjectorModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(CanaryFlight.class);
        install(new CanaryNormalizerModule());
        install(new CanarySieveModule());
    }
}
