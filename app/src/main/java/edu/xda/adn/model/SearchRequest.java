package edu.xda.adn.model;

public class SearchRequest {
    private String key;

    public SearchRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
