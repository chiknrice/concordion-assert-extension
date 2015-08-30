package org.chiknrice.concordion;

import org.concordion.api.*;
import org.concordion.internal.Row;
import org.concordion.internal.Table;
import org.concordion.internal.util.Check;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.chiknrice.concordion.Const.*;

/**
 * @author <a href="mailto:adrian.bondoc@verifone.com">Ian Bondoc</a>
 */
public class AssertEqualsMapCommand extends AbstractAssertCommand {

    @Override
    public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), format("Nesting commands inside an '%s' is not supported", ASSERT_EQUALS_MAP));
        Check.isTrue(commandCall.getElement().getLocalName().equals("table"),
                format("'%s' command can only be used on <table> element", ASSERT_EQUALS_MAP));

        Object actual = evaluator.evaluate(commandCall.getExpression());
        Map<String, String> actualMap;
        if (actual instanceof Map) {
            actualMap = (Map<String, String>) actual;
        } else {
            throw new RuntimeException(format("'%s' is only applicable to a %s instance", ASSERT_EQUALS_MAP, Map.class.getName()));
        }

        Table table = new Table(commandCall.getElement());
        Row header = table.getLastHeaderRow();
        int columnCount = header.getCells().length;
        int keyColumnIndex = -1;
        int valueColumnIndex = -1;
        for (Element thCell : header.getCells()) {
            String columnAs = thCell.getAttributeValue(COLUMN_AS, NAMESPACE);
            if (columnAs != null) {
                switch (columnAs) {
                    case KEY:
                        keyColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    case VALUE:
                        valueColumnIndex = header.getIndexOfCell(thCell);
                        break;
                    default:
                        throw new RuntimeException(format("Unsupported %s attribute value %s", COLUMN_AS, columnAs));
                }
            }
        }

        if (keyColumnIndex < 0 || valueColumnIndex < 0) {
            throw new RuntimeException(
                    format("Invalid configuration, %s should define which columns represent the key and value using '%s' attribute", ASSERT_EQUALS_MAP, COLUMN_AS));
        }

        Map<String, String> rowsToMatch = new HashMap<>(actualMap);

        Row[] detailRows = table.getDetailRows();
        for (Row detailRow : detailRows) {
            Element[] cells = detailRow.getCells();
            if (cells.length != columnCount) {
                throw new RuntimeException(
                        format("The <table> '%s' command only supports rows with an equal number of columns.", ASSERT_EQUALS_MAP));
            }
            Element keyCell = cells[keyColumnIndex];
            Element valueCell = cells[valueColumnIndex];
            tryToExpand(keyCell, evaluator);
            tryToExpand(valueCell, evaluator);

            String key = keyCell.getText();
            String value = valueCell.getText();

            if (rowsToMatch.containsKey(key)) {
                announceSuccess(keyCell);
                String actualValue = rowsToMatch.remove(key);
                if (value.equals(actualValue)) {
                    resultRecorder.record(Result.SUCCESS);
                    announceSuccess(valueCell);
                } else {
                    resultRecorder.record(Result.FAILURE);
                    announceFailure(valueCell, value, actualValue);
                }
            } else {
                resultRecorder.record(Result.FAILURE);
                announceMissingRow(detailRow.getElement());
            }
        }
        for (Map.Entry<String, String> surplusEntry : rowsToMatch.entrySet()) {
            resultRecorder.record(Result.FAILURE);
            Row surplusRow = table.addDetailRow();
            Element[] cells = surplusRow.getCells();
            cells[keyColumnIndex].appendText(surplusEntry.getKey());
            cells[valueColumnIndex].appendText(surplusEntry.getValue());
            announceSurplusRow(surplusRow.getElement());
        }
    }

}
