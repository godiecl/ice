package cl.disc.ucn.pdis.ice.server;

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
        System.out.println("Printing: " + string);

    }
}
