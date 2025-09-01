package LLDNewsAggregator.controllers;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import LLDNewsAggregator.Store.ArticleStore;
import LLDNewsAggregator.modals.Article;
import LLDNewsAggregator.services.DedupeService;
import LLDNewsAggregator.services.FeedService;

public class PipelineController {
    private DedupeService dedupeService;
    private ArticleStore store;
    private FeedService feedService;
    final ExecutorService ingestWorkers = Executors.newFixedThreadPool(4);

    public PipelineController(DedupeService dedupeService, ArticleStore store, FeedService feedService) {
        this.dedupeService = dedupeService;
        this.store = store;
        this.feedService = feedService;
    }

    public void ingestAsync(String rawURL, String title, String content, String source, Instant publishedAt){
        ingestWorkers.submit(() -> {
            Optional<Article> exists = dedupeService.dedupeAndBuild(rawURL, title, content, source, publishedAt);
            if(!exists.isPresent()){
                System.out.println("[Ingest] duplicate or already present: " + rawURL);
                return;
            }
            Article article = exists.get();
            store.save(article);
            feedService.pushArticleToUser(article);
            System.out.println("[Ingest] saved & pushed: " + article.getArticleId());
        });
    }

    public void shutdown(){
        ingestWorkers.shutdown();
    }
}
