package org.chiknrice.concordion.spec;

import org.concordion.api.Resource;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class ResourceExtension implements ConcordionExtension {

    private static final String CSS_SOURCE_PATH = "/spec.css";
    private static final Resource CSS_TARGET_RESOURCE = new Resource("/spec.css");
    private static final String JS_SOURCE_PATH = "/spec.js";
    private static final Resource JS_TARGET_RESOURCE = new Resource("/spec.js");

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withLinkedCSS(CSS_SOURCE_PATH, CSS_TARGET_RESOURCE);
        concordionExtender.withLinkedJavaScript(JS_SOURCE_PATH, JS_TARGET_RESOURCE);
    }

}
