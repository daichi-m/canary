package org.cleanermp3.canary;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by daichi on 7/5/15.
 */
public class CanaryTest {

    CanaryMain main;

    @BeforeTest
    public void init() {
         main = new CanaryMain();
    }

}
