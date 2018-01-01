package milad.hamisystem.utils;


import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
