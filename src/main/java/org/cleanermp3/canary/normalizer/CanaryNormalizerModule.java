package org.cleanermp3.canary.normalizer;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.cleanermp3.canary.normalizer.NormalizerAnnotations.TitleCaseNormalizer;
import org.cleanermp3.canary.normalizer.NormalizerAnnotations.ArtistNameNormalizer;
import org.cleanermp3.canary.normalizer.impl.ArtistNameNormalizerImpl;
import org.cleanermp3.canary.normalizer.impl.TitleCaseNormalizerImpl;

/**
 * Created by daichi on 8/5/15.
 */
public class CanaryNormalizerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Normalizer.class)
                .annotatedWith(TitleCaseNormalizer.class)
                .to(TitleCaseNormalizerImpl.class);
        bind(Normalizer.class)
                .annotatedWith(ArtistNameNormalizer.class)
                .to(ArtistNameNormalizerImpl.class);
        bind(NormalizerFactory.class);
    }
}
