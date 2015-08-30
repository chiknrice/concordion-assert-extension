/*
 * Copyright (c) 2014 Ian Bondoc
 *
 * This file is part of concordion-assert-extension
 *
 * concordion-assert-extension is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or(at your option) any later version.
 *
 * concordion-assert-extension is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
package org.chiknrice.concordion;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.internal.listener.AssertResultRenderer;
import org.concordion.internal.listener.VerifyRowsResultRenderer;

import static org.chiknrice.concordion.Const.*;

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
public class AssertCommandExtension implements ConcordionExtension {

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        AssertResultRenderer resultRenderer = new AssertResultRenderer();
        VerifyRowsResultRenderer rowsResultRenderer = new VerifyRowsResultRenderer();
        AssertEqualsMapCommand assertEqualsMapCommand = new AssertEqualsMapCommand();
        assertEqualsMapCommand.addAssertEqualsListener(resultRenderer);
        assertEqualsMapCommand.addVerifyRowsListener(rowsResultRenderer);
        concordionExtender.withCommand(NAMESPACE, ASSERT_EQUALS_MAP, assertEqualsMapCommand);

        AssertEqualsCollectionCommand assertEqualsCollectionCommand = new AssertEqualsCollectionCommand();
        assertEqualsCollectionCommand.addAssertEqualsListener(resultRenderer);
        assertEqualsCollectionCommand.addVerifyRowsListener(rowsResultRenderer);
        concordionExtender.withCommand(NAMESPACE, ASSERT_EQUALS_COLLECTION, assertEqualsCollectionCommand);
    }
}
