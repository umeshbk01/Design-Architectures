package LLDNewsAggregator.services;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import LLDNewsAggregator.Store.ArticleStore;
import LLDNewsAggregator.modals.Article;

public class DedupeService {
    private ArticleStore store;

    public DedupeService(ArticleStore store) {
        this.store = store;
    }

    String normalizeUrl(String url){
        if(url == null) return "";
        String normalized = url.split("\\?")[0].trim().toLowerCase();
        if(normalized.endsWith("/")){
            normalized = normalized.substring(0, normalized.length()-1);
        }
        return normalized;
    }

    public String contentHash(String content){
        try {
            String normalized = content.replaceAll("\\s+", " ").trim().toLowerCase();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(normalized.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }
    
    Set<String> extractTags(String text){
        String[] tokens = text.toLowerCase().replaceAll("[^a-z0-9]", " ").split("\\s+");
        Map<String, Integer> freq = new HashMap<>();
        Set<String> ignore = new HashSet<>(Arrays.asList("the", "is", "a", "and", "of", "to", "in", "for", "on"));
        for(String token: tokens){
            if(token.length() <3) continue;
            if(ignore.contains(token)) continue;
            freq.put(token, freq.getOrDefault(token, 0)+1);
        }
        return freq.entrySet().stream()
.sorted((e1,e2)->Integer.compare(e2.getValue(), e1.getValue()))
.limit(4)
.map(Map.Entry::getKey)
.collect(Collectors.toSet());
    }

    public Optional<Article> dedupeAndBuild(String rawUrl, String title, String content, String source, Instant publishedAt){
        String url = normalizeUrl(rawUrl);
        String contentHash = contentHash(content);
        // Check if an article with the same URL or content hash already exists
        Optional<Article> exists = store.findByUrl(url);
        if(exists.isPresent()){
            return Optional.empty();
        }
        Optional<Article> contentExists = store.findByHash(contentHash);
        if(contentExists.isPresent()){
            return Optional.empty();
        }
        Set<String> tags = extractTags(title + "    "+ content); // Tagging service can be called here to get the tags for the article
        Article article = new Article(
            java.util.UUID.randomUUID().toString(), // Unique article ID
            url,
            title,
            content,
            source,
            publishedAt,
            tags,
            contentHash
        );
        store.save(article);
        return Optional.of(article);
    }
}
