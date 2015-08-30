package org.chiknrice.concordion;

import org.concordion.api.AbstractCommand;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.listener.*;
import org.concordion.internal.util.Announcer;

import static org.chiknrice.concordion.Const.EVAL;
import static org.chiknrice.concordion.Const.NAMESPACE;

/**
 * @author <a href="mailto:adrian.bondoc@verifone.com">Ian Bondoc</a>
 */
public abstract class AbstractAssertCommand extends AbstractCommand {

    private Announcer<AssertEqualsListener> assertEqualsListeners = Announcer.to(AssertEqualsListener.class);
    private Announcer<VerifyRowsListener> verifyRowsListeners = Announcer.to(VerifyRowsListener.class);

    public void addAssertEqualsListener(AssertEqualsListener listener) {
        assertEqualsListeners.addListener(listener);
    }

    protected void announceSuccess(Element element) {
        assertEqualsListeners.announce().successReported(new AssertSuccessEvent(element));
    }

    protected void announceFailure(Element element, String expected, Object actual) {
        assertEqualsListeners.announce().failureReported(new AssertFailureEvent(element, expected, actual));
    }

    public void addVerifyRowsListener(VerifyRowsListener listener) {
        verifyRowsListeners.addListener(listener);
    }

    protected void announceMissingRow(Element element) {
        verifyRowsListeners.announce().missingRow(new MissingRowEvent(element));
    }

    protected void announceSurplusRow(Element element) {
        verifyRowsListeners.announce().surplusRow(new SurplusRowEvent(element));
    }

    protected void tryToExpand(Element e, Evaluator evaluator) {
        String expression = e.getAttributeValue(EVAL, NAMESPACE);
        if (expression != null) {
            Object result = evaluator.evaluate(expression);
            if (result != null) {
                e.appendText(result.toString());
                e.addAttribute("title", expression);
                e.addStyleClass("cr-eval");
            } else {
                Element child = new Element("em");
                child.appendText("null");
                e.appendChild(child);
            }
        }
    }

}
