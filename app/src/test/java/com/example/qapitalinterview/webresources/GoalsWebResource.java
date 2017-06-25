package com.example.qapitalinterview.webresources;


public class GoalsWebResource extends BaseWebResource {

    public String getResponse(Resource resource) {
        return testUtils.readString(resource.getFileName());
    }

    public enum Resource {
        GOALS("json/goals.json"),
        GOALS_EMPTY("json/goals_empty.json");

        private final String fileName;

        Resource(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
