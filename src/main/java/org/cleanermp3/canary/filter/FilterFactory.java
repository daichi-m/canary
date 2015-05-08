package org.cleanermp3.canary.filter;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.cleanermp3.canary.filter.FilterAnnotations.*;

public class FilterFactory {

    Filter bracketFilter;
    Filter trackNumberFilter;
    Filter urlFilter;

    @Inject
    public FilterFactory(@BracketFilter Filter bracketFilter,
                         @TrackNumberFilter Filter trackNumberFilter,
                         @UrlFilter Filter urlFilter) {

        this.bracketFilter = bracketFilter;
        this.urlFilter = urlFilter;
        this.trackNumberFilter = trackNumberFilter;
    }

    public List<Filter> getAvailableFilters() {

        Filter [] filters = {urlFilter, trackNumberFilter, bracketFilter};
        return Arrays.asList(filters);
    }

}
