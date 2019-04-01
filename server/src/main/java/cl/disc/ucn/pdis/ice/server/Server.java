package cl.disc.ucn.pdis.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementacion del server.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class Server {

    /**
     *
     * @param args arguments.
     */
    public static void main(final String[] args) {

        log.debug("Starting the server ..");

        try (Communicator communicator = Util.initialize(args)) {

            log.debug("Communicator ok, building adapter ..");
            final ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("PrinterAdapter", "default -p 10000");
            final Object printer = new PrinterImpl();

            log.debug("Adding printer to adapter ..");
            adapter.add(printer, Util.stringToIdentity("Printer"));
            adapter.activate();

            log.debug("Waiting for data ..");
            communicator.waitForShutdown();

        }

        log.debug("Server end.");

    }

}
