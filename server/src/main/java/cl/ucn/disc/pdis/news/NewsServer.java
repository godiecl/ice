/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.news;

import cl.disc.ucn.pdis.news.zeroice.model.NewsAPI;
import com.zeroc.Ice.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementacion del server de articulos.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class NewsServer {

    /**
     * @param args
     */
    public static void main(final String[] args) {

        // Properties
        final Properties properties = Util.createProperties(args);
        properties.setProperty("Ice.Package.model", "cl.disc.ucn.pdis.news.zeroice");
        // https://doc.zeroc.com/ice/latest/property-reference/ice-trace
        properties.setProperty("Ice.Trace.Admin.Properties", "1");
        properties.setProperty("Ice.Trace.Locator", "2");
        properties.setProperty("Ice.Trace.Network", "3");
        properties.setProperty("Ice.Trace.Protocol", "1");
        properties.setProperty("Ice.Trace.Slicing", "1");
        properties.setProperty("Ice.Trace.ThreadPool", "1");
        properties.setProperty("Ice.Compression.Level", "9");
        properties.setProperty("Ice.Plugin.Slf4jLogger.java", "cl.ucn.disc.pdis.news.ice.Slf4jLoggerPluginFactory");

        InitializationData initializationData = new InitializationData();
        initializationData.properties = properties;

        log.debug("Starting the NewsServer ..");
        // The communicator
        try (Communicator communicator = Util.initialize(initializationData)) {

            // The adapter
            final ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("NewsAPIAdapter", "default -p 10000 -z");

            // The API
            final NewsAPI newsAPI = new NewsAPIImpl();

            final Identity identity = Util.stringToIdentity(NewsAPI.class.getName());
            log.debug("Using name: {} and category: {} as identity.", identity.name, identity.category);

            // Register the API
            adapter.add(newsAPI, identity);
            adapter.activate();

            log.debug("Waiting for connections ..");

            communicator.waitForShutdown();
        }

        log.debug("Server ended!");

    }

}
