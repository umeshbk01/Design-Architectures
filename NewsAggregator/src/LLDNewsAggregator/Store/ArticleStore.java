package LLDNewsAggregator.Store;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import LLDNewsAggregator.modals.Article;

public class ArticleStore {
    private ConcurrentMap<String, Article> byId = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String> urlToId = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String> hashToId = new ConcurrentHashMap<>();

    public void save(Article article){
        byId.put(article.getArticleId(), article);
        urlToId.put(article.getNormalizedURL(), article.getArticleId());
        hashToId.put(article.getHash(), article.getArticleId());
    }

    public Optional<Article> findByUrl(String url){
        String articleId = urlToId.getOrDefault(url, "");
        return Optional.ofNullable(byId.get(articleId));
    }

    public Optional<Article> findByHash(String hash){
        String articleId = hashToId.getOrDefault(hash, "");
        return Optional.ofNullable(byId.get(articleId));
    }

    public Collection<Article> all(){
        return byId.values();
    }
}
