/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdisc.printer;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Implementacion del cliente.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class Client {

    /**
     * @param args arguments.
     */
    public static void main(final String[] args) {

        final StopWatch sw = StopWatch.createStarted();

        log.debug("Starting the client ..");

        try (Communicator communicator = Util.initialize(args)) {

            final ObjectPrx proxy = communicator.stringToProxy("Printer:default -p 10000");
            final Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(proxy);

            if (printer == null) {
                throw new IllegalStateException("Invalid proxy!");
            }

            log.debug("Time: {}", sw.toString());

            printer.printString("Hello World!");

            log.debug("Time: {}", sw.toString());

        }

    }

}
