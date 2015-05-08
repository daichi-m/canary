package org.cleanermp3.canary.processor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.filter.Filter;
import org.cleanermp3.canary.filter.FilterFactory;
import org.cleanermp3.canary.normalizer.Normalizer;
import org.cleanermp3.canary.normalizer.NormalizerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by daichi on 7/5/15.
 */
public class CanaryFlight {

    private NormalizerFactory normalizerFactory;
    private FilterFactory filterFactory;
    private String musicHome;
    List<Filter> filters;
    List<Normalizer> normalizers;

    @Inject
    public CanaryFlight(NormalizerFactory normalizerFactory,
                        FilterFactory filterFactory,
                        @Named(Constants.MUSIC_DIR_TAG)String musicHome) {
        this.normalizerFactory = normalizerFactory;
        this.filterFactory = filterFactory;
        this.musicHome = musicHome;
    }

    public void populateFiltersAndNormalizers() {
        this.filters = filterFactory.getAvailableFilters();
        this.normalizers = normalizerFactory.getAvailableNormalizers();
    }

    public void flap() {
        populateFiltersAndNormalizers();

        System.out.println(filters);
        System.out.println(normalizers);
    }
}
