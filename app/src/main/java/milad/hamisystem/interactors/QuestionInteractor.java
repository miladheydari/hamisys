package milad.hamisystem.interactors;

import java.util.List;

import io.reactivex.Observable;
import milad.hamisystem.models.Question;

public interface QuestionInteractor {

    Observable<List<Question>> getQuestions();
}
