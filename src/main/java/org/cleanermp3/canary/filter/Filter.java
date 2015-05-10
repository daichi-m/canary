package org.cleanermp3.canary.filter;

import org.cleanermp3.canary.exceptions.FilterException;

/**
 * Created by daichi on 7/5/15.
 */
public interface Filter {
	
	public String filter(String raw)
			throws FilterException;
	
}
