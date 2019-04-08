/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.printer;

import com.zeroc.Ice.Current;
import lombok.extern.slf4j.Slf4j;

/**
 * Server implementation.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class PrinterImpl implements Demo.Printer {

    /**
     * @param string
     * @param current
     */
    @Override
    public void printString(final String string, final Current current) {

        log.debug("Printing: {}", string);
        // System.out.println("Printing: " + string);

    }
}
