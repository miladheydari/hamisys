package milad.hamisystem.utils;

import android.content.res.Resources;

import java.util.ArrayList;

import milad.hamisystem.models.Answer;

public class Utility {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static boolean isNotNullOrEmpty(String string) {
        return !(string == null || string.length() == 0);
    }

    public static Answer copy(Answer answer) {
        Answer res = new Answer();
        res.value = answer.value;
        if (answer.answerType != null) {
            res.subAnswers = new ArrayList<>();
            res.subAnswers.addAll(answer.subAnswers);
            res.answerType = answer.answerType;
        }
        res.id = answer.id;
        return res;
    }
}
