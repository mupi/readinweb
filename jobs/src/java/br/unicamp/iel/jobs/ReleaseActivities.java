package br.unicamp.iel.jobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import lombok.Setter;
import lombok.extern.java.Log;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.tool.api.Session;

import br.unicamp.iel.logic.ReadInWebCommonLogic;
import br.unicamp.iel.logic.SakaiProxy;
import br.unicamp.iel.model.Property;

public class ReleaseActivities implements Job {
	private static final Logger log = Logger.getLogger(ReleaseActivities.class.getName());

    @Setter
    private ReadInWebCommonLogic common;

    @Setter
    private SakaiProxy sakaiProxy;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sakaiProxy.adminSessionStart();

        List<Site> classes = sakaiProxy.getReadInWebSites(1L);
        log.info(String.format("Looking into %d classes", classes.size()));

        for(Site riwClass : classes){
            Long releaseDate;
            Long today = System.currentTimeMillis();
            String stringDate = sakaiProxy.getStringProperty(riwClass,
                    Property.COURSELASTRELEASEDATE.getName());

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if(stringDate == null){
                releaseDate = System.currentTimeMillis();
            } else {
                try {
                    releaseDate = df.parse(stringDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    releaseDate = System.currentTimeMillis();
                }
            }

            if(today >= common.getStartDate(riwClass).getTime()){
                if(today - releaseDate > week()){
                    // Release activities
                    log.info("Release Activities: " + riwClass.getTitle());
                    common.releaseActivities(riwClass);
//                    sakaiProxy.updateStringProperty(riwClass,
//                            Property.COURSELASTRELEASEDATE.getName(),
//                            df.format(new Date(today)));
                } else {
                    log.info("Do not release Activities! " + riwClass.getTitle());
                }
            }
        }
    }

    private Long week(){
        return new Long(60*60*24*7*1000);
    }
}
