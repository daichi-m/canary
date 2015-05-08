package org.cleanermp3.canary.normalizer;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryNormalizerModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<Normalizer> normalizerBinder = Multibinder.newSetBinder(binder(), Normalizer.class);
        normalizerBinder.addBinding().to(Normalizer.class);

    }
}
