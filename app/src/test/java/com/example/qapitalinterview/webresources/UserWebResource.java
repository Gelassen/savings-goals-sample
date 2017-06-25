package com.example.qapitalinterview.webresources;


public class UserWebResource extends BaseWebResource {

    public String getResponse(Resource resource) {
        return testUtils.readString(resource.getFilePath());
    }

    public enum Resource {
        USER("json/user.json");

        private final String filePath;

        Resource(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
