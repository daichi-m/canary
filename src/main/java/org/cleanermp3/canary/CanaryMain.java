package org.cleanermp3.canary;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.cleanermp3.canary.processor.CanaryFlight;

/**
 * Hello world!
 *
 */
public class CanaryMain
{
    public static void main( String[] args ) {

        Injector injector = Guice.createInjector(new CanaryInjectorModule());
        CanaryFlight flight = injector.getInstance(CanaryFlight.class);
        flight.flap();
    }

}
