package milad.hamisystem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by milad on 12/31/2017.
 */

public class Answer {
    public String id;
    public String value;
    @SerializedName("answers")
    public List<Answer> subAnswers;
    @SerializedName("answer_type")
    public AnswerType answerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (!id.equals(answer.id)) return false;
        if (value != null ? !value.equals(answer.value) : answer.value != null) return false;
        if (subAnswers != null ? !subAnswers.equals(answer.subAnswers) : answer.subAnswers != null)
            return false;
        return answerType == answer.answerType;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (subAnswers != null ? subAnswers.hashCode() : 0);
        result = 31 * result + (answerType != null ? answerType.hashCode() : 0);
        return result;
    }
}
