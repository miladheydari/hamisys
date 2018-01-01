
package milad.hamisystem.bases;

public interface IPresenter<T> {

    void subscribe();

    void unsubscribe();


    void onViewAttached(T view);

}
