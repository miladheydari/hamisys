package milad.hamisystem.interactors.remote;


import io.reactivex.Observable;
import milad.hamisystem.models.QuestionList;

public interface GetQuestionsApiService {

    Observable<QuestionList> getQuestions();

}
