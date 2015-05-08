package org.cleanermp3.canary;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.cleanermp3.canary.filter.CanaryFilterModule;
import org.cleanermp3.canary.normalizer.CanaryNormalizerModule;
import org.cleanermp3.canary.processor.CanaryFlight;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryInjectorModule extends AbstractModule {

    @Override
    protected void configure() {

        boolean propertyBound = false;

        bind(CanaryFlight.class);
        install(new CanaryNormalizerModule());
        install(new CanaryFilterModule());
        try {
            InputStream propStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(Constants.CANARY_PROPS_FILE);
            Properties prop = new Properties();
            if(propStream != null) {
                prop.load(propStream);
                Names.bindProperties(binder(), prop);
                propertyBound = true;
            }
        } catch (IOException exception) {
            System.err.println("Could not bind properties: " + exception);
            propertyBound = false;
        }

        // Bind the system defaults then
        if(!propertyBound) {
            Map<String, String> defaultProps = new LinkedHashMap<>();
            defaultProps.put(Constants.USER_HOME_TAG, Constants.USER_HOME);
            defaultProps.put(Constants.MUSIC_DIR_TAG, Constants.DEFAULT_MUSIC_DIR);
            Names.bindProperties(binder(), defaultProps);
        }
    }
}
