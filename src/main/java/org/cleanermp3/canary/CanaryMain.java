package org.cleanermp3.canary;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.cleanermp3.canary.exceptions.CanaryException;
import org.cleanermp3.canary.processor.CanaryFlight;

/**
 * Hello world!
 *
 */
public class CanaryMain
{
    private static final Logger log = Logger.getLogger(Constants.LOG4J_LOGGER);
    public static void main( String[] args ) {

        Injector injector = Guice.createInjector(new CanaryInjectorModule());
        CanaryFlight flight = injector.getInstance(CanaryFlight.class);
        try {
            flight.flap();
        } catch (CanaryException base) {
            log.error(base);
        }
    }

}
