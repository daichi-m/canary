package org.cleanermp3.canary.filter;

import com.google.inject.Inject;
import org.cleanermp3.canary.filter.FilterAnnotations.TrackNumberFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.TrailingPunctuationFilter;
import org.cleanermp3.canary.filter.FilterAnnotations.UrlFilter;

import java.util.Arrays;
import java.util.List;

public class FilterFactory {

    Filter trailingPunctuationFilter;
    Filter trackNumberFilter;
    Filter urlFilter;

    @Inject
    public FilterFactory(@TrailingPunctuationFilter Filter trailingPunctuationFilter,
                         @TrackNumberFilter Filter trackNumberFilter,
                         @UrlFilter Filter urlFilter) {

        this.trailingPunctuationFilter = trailingPunctuationFilter;
        this.urlFilter = urlFilter;
        this.trackNumberFilter = trackNumberFilter;
    }

    public List<Filter> getAvailableFilters() {

        Filter [] filters = {urlFilter, trackNumberFilter, trailingPunctuationFilter};
        return Arrays.asList(filters);
    }

}
