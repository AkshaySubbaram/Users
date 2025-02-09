package com.microservice.thread.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.thread.entity.UserJsonEntity;

import java.util.List;

public class UserResponse {

    @JsonProperty("results")
    private List<UserJsonEntity> results;

    @JsonProperty("info")
    private Info info;

    public List<UserJsonEntity> getResults() {
        return results;
    }

    public void setResults(List<UserJsonEntity> results) {
        this.results = results;
    }

    public static class Info {
        private String seed;
        private int results;
        private int page;
        private String version;

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public int getResults() {
            return results;
        }

        public void setResults(int results) {
            this.results = results;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
