package com.example.qapitalinterview.webresources;


import com.example.qapitalinterview.utils.Params;

public class FeedWebResource extends BaseWebResource {

    public String getResponse(Resource resource) {
        return testUtils.readString(resource.getFilePath());
    }

    public enum Resource {
        FEED("json/feed.json");

        private final String filePath;

        Resource(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
