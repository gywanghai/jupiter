package top.ershixiong.jupiter.application;

import top.ershixiong.jupiter.domain.JobScheduler;

public class JobSchedulerApplication {

    private JobScheduler jobScheduler;

    public JobSchedulerApplication(JobScheduler jobScheduler){
        this.jobScheduler = jobScheduler;
    }

    public void start(){
        jobScheduler.start();
    }


}
