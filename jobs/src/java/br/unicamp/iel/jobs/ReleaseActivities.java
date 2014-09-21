package br.unicamp.iel.jobs;

import java.util.List;

import javax.xml.ws.ServiceMode;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.site.api.Site;

import br.unicamp.iel.logic.ReadInWebCommonLogic;
import br.unicamp.iel.logic.SakaiProxy;


/* this is a test Quartz job to show that we can inject jobs into the jobscheduler from an external location */


public class ReleaseActivities implements Job {

    private static final Log LOG = LogFactory.getLog(ReleaseActivities.class);

    @Setter
    private ReadInWebCommonLogic common;

    @Setter
    private SakaiProxy sakaiProxy;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Site> classes = sakaiProxy.getReadInWebSites(1L);
        for(Site riwClass : classes){
            System.out.println("ReleaseActivities! " + riwClass.getTitle());
        }
    }

}
