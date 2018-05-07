package by.cascade.chatcot.jsonmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhraseOutJson {
    @JsonProperty("type")
    private int type;
    @JsonProperty("answer")
    private String answer;

    public PhraseOutJson(int type, String answer) {
        this.type = type;
        this.answer = answer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
