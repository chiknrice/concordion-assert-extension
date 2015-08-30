package org.chiknrice.concordion.spec;

import org.concordion.api.ExpectedToFail;

import java.util.Map;

/**
 * @author <a href="mailto:adrian.bondoc@verifone.com">Ian Bondoc</a>
 */
@ExpectedToFail
public class AssertEqualsMapSpecFixture extends AssertBaseFixture {

    public Map<String, String> toMap(Map<String, String> param) {
        return param;
    }

}
