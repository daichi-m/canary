package org.cleanermp3.canary.filter;

import com.google.inject.AbstractModule;
import org.cleanermp3.canary.filter.FilterAnnotations.TrackNumberFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.TrailingPunctuationFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.UrlFilter;
import org.cleanermp3.canary.filter.impl.TrackNumberFilterImpl;
import org.cleanermp3.canary.filter.impl.TrailingPunctuationsFilterImpl;
import org.cleanermp3.canary.filter.impl.UrlFilterImpl;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryFilterModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(Filter.class)
                .annotatedWith(TrailingPunctuationFilter.class)
                .to(TrailingPunctuationsFilterImpl.class);
        bind(Filter.class)
                .annotatedWith(TrackNumberFilter.class)
                .to(TrackNumberFilterImpl.class);
        bind(Filter.class)
                .annotatedWith(UrlFilter.class)
                .to(UrlFilterImpl.class);
        bind(FilterFactory.class);
    }
}
