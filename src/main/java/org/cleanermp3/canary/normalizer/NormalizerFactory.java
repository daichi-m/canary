package org.cleanermp3.canary.normalizer;

import com.google.inject.Inject;
import org.cleanermp3.canary.normalizer.NormalizerAnnotations.ArtistNameNormalizer;
import org.cleanermp3.canary.normalizer.NormalizerAnnotations.TitleCaseNormalizer;

import java.util.Arrays;
import java.util.List;

public class NormalizerFactory {

    Normalizer artistNameNormalizer;
    Normalizer titleCaseNormalizer;

    @Inject
    public  NormalizerFactory(@TitleCaseNormalizer Normalizer titleCaseNormalizer,
                              @ArtistNameNormalizer Normalizer artistNameNormalizer) {
        this.titleCaseNormalizer = titleCaseNormalizer;
        this.artistNameNormalizer = artistNameNormalizer;
    }

    public List<Normalizer> getAvailableNormalizers() {
        Normalizer [] normalizers = {titleCaseNormalizer, artistNameNormalizer};
        return Arrays.asList(normalizers);
    }

}
