package milad.hamisystem.interactors.remote;



import io.reactivex.Observable;
import milad.hamisystem.models.QuestionList;
import retrofit2.http.GET;


public interface ApiInterface {

        //localhost:8082/questions
        @GET("questions")
        Observable<QuestionList> getQuestions();
}
