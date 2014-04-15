package com.the42apps.jacocosample;

import junit.framework.TestCase;

/**
 * Created by skyisle on 2014. 4. 15..
 */
public class CalculatorTest extends TestCase {
    public void testSum() throws Exception {
        Calculator calculator = new Calculator();

        assertEquals(5, calculator.sum(2,3));
    }
}
