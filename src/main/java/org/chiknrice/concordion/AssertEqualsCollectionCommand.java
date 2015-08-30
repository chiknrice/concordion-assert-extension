package org.chiknrice.concordion;

import org.concordion.api.*;
import org.concordion.internal.util.Check;

import java.util.Collection;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.ASSERT_EQUALS_COLLECTION;

/**
 * @author <a href="mailto:adrian.bondoc@verifone.com">Ian Bondoc</a>
 */
public class AssertEqualsCollectionCommand extends AbstractAssertCommand {

    @Override
    public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside an '%s' is not supported", ASSERT_EQUALS_COLLECTION));
        Check.isTrue(commandCall.getElement().getLocalName().equals("ul"),
                format("'%s' command can only be used on <ul> element", ASSERT_EQUALS_COLLECTION));

        Element ul = commandCall.getElement();

        Object result = evaluator.evaluate(commandCall.getExpression());

        if (result instanceof Collection) {
            Collection<?> collection = (Collection<?>) result;
            Element[] liArray = ul.getChildElements("li");
            nextLI:
            for (Element li : liArray) {
                tryToExpand(li, evaluator);

                String value = li.getText();

                if (collection.contains(value)) {
                    collection.remove(value);
                    resultRecorder.record(Result.SUCCESS);
                    announceSuccess(li);
                } else {
                    resultRecorder.record(Result.FAILURE);
                    announceMissingRow(li);
                }
            }
            for (Object o : collection) {
                Element li = new Element("li");
                ul.appendChild(li);
                resultRecorder.record(Result.FAILURE);
                announceSurplusRow(li);
            }
        } else {
            throw new RuntimeException(format("'%s' is only applicable to an %s instance", ASSERT_EQUALS_COLLECTION, Collection.class.getName()));
        }
    }

}
