package milad.hamisystem.screens.questionlist;


import dagger.Subcomponent;


@Subcomponent(modules = {
        QuestionPresenterModule.class
})
public interface QuestionComponent {

    void inject(QuestionListActivity questionListActivity);
}