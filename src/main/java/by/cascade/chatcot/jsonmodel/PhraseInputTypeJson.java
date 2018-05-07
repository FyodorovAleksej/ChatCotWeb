package by.cascade.chatcot.jsonmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhraseInputTypeJson {
    @JsonProperty("choiceType")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
