package LLDNewsAggregator.modals;

import java.time.Instant;
import java.util.Set;

public class Article {
    private String articleId;
    private String normalizedURL;
    private String title;
    private String content;
    private String source;
    private Instant publishedAt;
    private Set<String> tags;
    private String hash;

    public Article(String articleId, String normalizedURL, String title, String content, String source, Instant publishedAt, Set<String> tags, String hash) {
        this.articleId = articleId;
        this.normalizedURL = normalizedURL;
        this.title = title;
        this.content = content;
        this.source = source;
        this.publishedAt = publishedAt;
        this.tags = tags;
        this.hash = hash;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getNormalizedURL() {
        return normalizedURL;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", normalizedURL='" + normalizedURL + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", publishedAt=" + publishedAt +
                ", tags=" + tags +
                ", hash='" + hash + '\'' +
                '}';
    }

}
