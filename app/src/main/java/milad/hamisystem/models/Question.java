package milad.hamisystem.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    public String  id;
    public String title;
    public List<Answer> answers;
    @SerializedName("answer_type")
    public AnswerType answerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        return id != null ? id.equals(question.id) : question.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
