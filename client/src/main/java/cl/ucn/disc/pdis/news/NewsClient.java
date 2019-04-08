/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.news;

import cl.disc.ucn.pdis.news.zeroice.model.Article;
import cl.disc.ucn.pdis.news.zeroice.model.NewsAPI;
import cl.disc.ucn.pdis.news.zeroice.model.NewsAPIPrx;
import com.zeroc.Ice.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementacion del cliente.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class NewsClient {

    /**
     * @param args
     */
    public static void main(final String[] args) {

        // Properties
        final Properties properties = Util.createProperties(args);
        properties.setProperty("Ice.Package.model", "cl.disc.ucn.pdis.news.zeroice");

        InitializationData initializationData = new InitializationData();
        initializationData.properties = properties;

        log.debug("Starting the News client ..");

        try (Communicator communicator = Util.initialize(initializationData)) {

            final ObjectPrx proxy = communicator.stringToProxy(NewsAPI.class.getName() + ":default -p 10000");
            final NewsAPIPrx newsAPI = NewsAPIPrx.checkedCast(proxy);

            if (newsAPI == null) {
                throw new IllegalStateException("Invalid proxy!");
            }

            final Article[] articles = newsAPI.getTopArticles();
            log.debug("Articles: {}", articles.length);

        }

        log.debug("Done.");
    }

}
