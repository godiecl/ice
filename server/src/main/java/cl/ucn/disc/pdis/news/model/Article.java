/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.news.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.threeten.bp.ZonedDateTime;

/**
 * Article from NewsApi.org.
 *
 * @author Diego Urrutia-Astorga.
 * @version 0.0.2
 */
@ToString
public final class Article {

    /**
     * Author
     */
    @Getter
    @Setter
    private String author;

    /**
     * Title
     */
    @Getter
    @Setter
    private String title;

    /**
     * Source
     */
    @Getter
    @Setter
    private String source;

    /**
     * Description
     */
    @Getter
    @Setter
    private String description;

    /**
     * URL
     */
    @Getter
    @Setter
    private String url;

    /**
     * URL to image
     */
    @Getter
    @Setter
    private String urlToImage;

    /**
     * Date
     */
    @Getter
    @Setter
    private ZonedDateTime publishedAt;

    /**
     * Content
     */
    @Getter
    @Setter
    private String content;

}
