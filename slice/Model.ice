// Custom package mapping
["java:package:cl.disc.ucn.pdis.news.zeroice"]
module model
{
    // Article model
    class Article
    {
        string source;
        string author;
        string title;
        string description;
        string url;
        string urlToImage;
        string publishedAt;
        string content;
    }

    // List of Articles
    sequence<Article> Articles;

    // News API
    interface NewsAPI
    {
        Articles getTopArticles();
    }

}
