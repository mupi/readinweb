package br.unicamp.iel.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/* this is a test Quartz job to show that we can inject jobs into the jobscheduler from an external location */


public class ReleaseActivities implements Job {

    private static final Log LOG = LogFactory.getLog(ReleaseActivities.class);
    private String configMessage;


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("ReleaseActivities! " + getConfigMessage());
        System.out.println("ReleaseActivities! " + getConfigMessage());
    }

    public String getConfigMessage() {
        return configMessage;
    }

    public void setConfigMessage(String configMessage) {
        this.configMessage = configMessage;
    }


}
