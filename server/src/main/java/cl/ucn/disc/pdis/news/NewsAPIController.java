/*
 * Copyright (c) 2019 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.news;

import cl.ucn.disc.pdis.news.model.Article;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Access to NewsAPI.org.
 *
 * @author Diego Urrutia-Astorga
 * @version 0.0.3
 */
@Slf4j
public final class NewsAPIController {

    /**
     * Service API
     */
    public interface NewsAPIService {

        @Headers({"X-Api-Key: 8dd673e94a9e4086a41b4cde0b6aa1c5"})
        @GET("top-headlines?sources=techcrunch,ars-technica,engadget,buzzfeed,wired&pageSize=100")
        Call<List<Article>> getTopArticles();

    }

    /**
     * The Service
     */
    private static final NewsAPIService NEWS_API_SERVICE;

    /*
     * Inicialization
     */
    static {

        // The logging
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> log.debug(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // The Client
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        // Gson
        final Gson gson = new GsonBuilder()
                // .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                //.serializeNulls()
                //.setPrettyPrinting()

                // DateTime des-serializer
                .registerTypeAdapter(ZonedDateTime.class, (JsonDeserializer<ZonedDateTime>) (json, typeOfT, context)
                        -> ZonedDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_ZONED_DATE_TIME))
                //.registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>) (date, typeOfT, context)
                //        -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_INSTANT)))

                // List<Article> adapter
                .registerTypeAdapter(new TypeToken<List<Article>>() {
                }.getType(), (JsonDeserializer<List<Article>>) (json, typeOfT, context) -> {

                    // Articles to send
                    final List<Article> articles = new ArrayList<>();

                    // Array of articles
                    final JsonArray jsonArray = json.getAsJsonObject().getAsJsonArray("articles");

                    // Json -> Article
                    for (JsonElement je : jsonArray) {
                        // log.debug("JE: {}", je);

                        final String source = je.getAsJsonObject().get("source").getAsJsonObject().get("name").getAsString();
                        je.getAsJsonObject().remove("source");
                        // log.debug("Source: {}", source);

                        final Article article = context.deserialize(je, Article.class);
                        article.setSource(source);

                        articles.add(article);
                    }

                    return articles;
                })

                .create();

        // Retrofit
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NEWS_API_SERVICE = retrofit.create(NewsAPIService.class);

    }


    /**
     * @return the List of {@link Article}.
     */
    public static List<Article> getTopArticles() {

        // The Call.
        final Call<List<Article>> listCall = NEWS_API_SERVICE.getTopArticles();
        try {

            return listCall.execute().body();

        } catch (IOException ex) {
            log.error("Error", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param args
     */
    public static void main(final String[] args) throws IOException {

        log.debug("Starting ..");

        final List<Article> articles = NewsAPIController.getTopArticles();
        log.debug("Articles: {}", articles);

        log.debug("End.");
    }

}
