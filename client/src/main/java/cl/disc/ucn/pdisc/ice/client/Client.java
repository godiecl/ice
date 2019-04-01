package cl.disc.ucn.pdisc.ice.client;

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
