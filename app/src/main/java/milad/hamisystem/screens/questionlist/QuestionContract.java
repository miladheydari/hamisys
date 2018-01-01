package milad.hamisystem.screens.questionlist;

import java.util.List;

import milad.hamisystem.models.Answer;
import milad.hamisystem.models.Question;
import milad.hamisystem.bases.IPresenter;
import milad.hamisystem.bases.IView;


public interface QuestionContract {

    interface View extends IView<Presenter> {

        void showQuestions(List<Question> tasks);


        void hideLoadingForQuestion();

        void answerOnChange(List<Question> questions);

        void onError();
    }

    interface Presenter extends IPresenter<View> {

        void onLoadQuestion();

        void assignAnswer(Question question, List<Answer> answers);
    }
}
