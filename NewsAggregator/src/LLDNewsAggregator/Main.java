package LLDNewsAggregator;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

import LLDNewsAggregator.Store.ArticleStore;
import LLDNewsAggregator.controllers.PipelineController;
import LLDNewsAggregator.services.DedupeService;
import LLDNewsAggregator.services.FeedService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArticleStore store = new ArticleStore();
        DedupeService deduper = new DedupeService(store);
        FeedService feed = new FeedService(store);
        PipelineController pipeline = new PipelineController(deduper, store, feed);


        // register demo users with simple preferences
        feed.registerUser("alice", new HashSet<>(Arrays.asList("india", "startup", "ai")));
        feed.registerUser("bob", new HashSet<>(Arrays.asList("finance", "markets", "ai")));


    // simulate ingestion
    pipeline.ingestAsync("https://news.example.com/article1", "AI startup raises $10M", "The startup raised ten million dollars to build AI models in India.", "news.example.com", Instant.now().minusSeconds(30));
    pipeline.ingestAsync("https://news.example.com/article2", "Markets rally after earnings", "Stocks rallied after better than expected earnings report.", "news.example.com", Instant.now().minusSeconds(600));
    pipeline.ingestAsync("https://news.example.com/article3?utm=1", "AI startup raises $10M", "The startup raised ten million dollars to build AI models in India.", "news.example.com", Instant.now().minusSeconds(60)); // duplicate by content/url


    // give some time for workers
        Thread.sleep(1000);


        System.out.println("\n=== Alice's feed ===");
        feed.getFeed("alice", 10).forEach(System.out::println);


        System.out.println("\n=== Bob's feed ===");
        feed.getFeed("bob", 10).forEach(System.out::println);


        pipeline.shutdown();
    }
}
