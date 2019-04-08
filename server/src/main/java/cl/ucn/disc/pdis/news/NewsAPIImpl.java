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
import com.zeroc.Ice.Current;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Access to NewsAPI.org.
 *
 * @author Diego Urrutia-Astorga
 * @version 0.0.3
 */
@Slf4j
public final class NewsAPIImpl implements NewsAPI {

    /**
     * @param current
     * @return the Array of {@link Article}
     */
    @Override
    public Article[] getTopArticles(final Current current) {

        return getTopArticles();

    }

    /**
     * @return the List of {@link Article}.
     */
    private static Article[] getTopArticles() {

        final List<cl.ucn.disc.pdis.news.model.Article> articleList = NewsAPIController.getTopArticles();
        final Article[] articles = new Article[articleList.size()];

        for (int i = 0; i < articleList.size(); i++) {
            articles[i] = build(articleList.get(i));
        }

        return articles;
    }

    /**
     * @param article to adapt
     * @return the article.
     */
    private static Article build(final cl.ucn.disc.pdis.news.model.Article article) {

        final Article a = new Article();
        a.author = article.getAuthor();
        a.content = article.getContent();
        a.description = article.getDescription();
        a.publishedAt = article.getPublishedAt().toString();
        a.source = article.getSource();
        a.title = article.getTitle();
        a.url = article.getUrl();
        a.urlToImage = article.getUrlToImage();
        return a;

    }

}
