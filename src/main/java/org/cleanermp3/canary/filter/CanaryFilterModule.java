package org.cleanermp3.canary.filter;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.cleanermp3.canary.filter.impl.BracketFilterImpl;
import org.cleanermp3.canary.filter.impl.TrackNumberFilterImpl;
import org.cleanermp3.canary.filter.impl.UrlFilterImpl;

import org.cleanermp3.canary.filter.FilterAnnotations.BracketFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.TrackNumberFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.UrlFilter;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryFilterModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(Filter.class)
                .annotatedWith(BracketFilter.class)
                .to(BracketFilterImpl.class);
        bind(Filter.class)
                .annotatedWith(TrackNumberFilter.class)
                .to(TrackNumberFilterImpl.class);
        bind(Filter.class)
                .annotatedWith(UrlFilter.class)
                .to(UrlFilterImpl.class);
        bind(FilterFactory.class);
    }
}
