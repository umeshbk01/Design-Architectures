package LLDNewsAggregator.services;

import java.security.KeyStore.Entry;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import LLDNewsAggregator.Store.ArticleStore;
import LLDNewsAggregator.modals.Article;

public class FeedService {
    private ArticleStore store;
    private ConcurrentMap<String, Set<String>> userPrefs = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Deque<Article>> userFeed = new ConcurrentHashMap<>();

    public FeedService(ArticleStore store) {
        this.store = store;
    }

    public void registerUser(String userId, Set<String> pref){
        userPrefs.put(userId, pref);
        userFeed.putIfAbsent(userId, new ArrayDeque<>());
    }

    public void pushArticleToUser(Article article){
        for(Map.Entry<String,Set<String>> entry: userPrefs.entrySet()){
            String userId = entry.getKey();
            Set<String> prefs = entry.getValue();
            int score = scoreArticleForUser(article, prefs);
            if(score > 0){
                Deque<Article> dq = userFeed.get(userId);
                synchronized (dq) {
                    dq.addFirst(article);
                    if(dq.size()> 200) dq.removeLast();
                }
            }
        }
    }
    int scoreArticleForUser(Article article, Set<String> prefs){
        int score = 0;
        for(String tag: article.getTags()){
            if(prefs.contains(tag)) score++;
        }
        long age = Instant.now().getEpochSecond() - article.getPublishedAt().getEpochSecond();
        int recency = age <60 ? 5 : age <3600 ? 3 : 1;

        return score*recency;
    }

    public List<Article> getFeed(String userId, int limit){
        Deque<Article> dq = userFeed.get(userId);
        if(dq == null) return List.of();
        synchronized (dq) {
            return dq.stream().limit(limit).collect(Collectors.toList());
        }
    }
}
