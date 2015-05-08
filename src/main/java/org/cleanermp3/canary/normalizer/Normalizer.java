package org.cleanermp3.canary.normalizer;

import org.cleanermp3.canary.exceptions.NormalizationException;

/**
 * Created by daichi on 7/5/15.
 */
public interface Normalizer {
	
	public String normalize(String raw) throws NormalizationException;
}
