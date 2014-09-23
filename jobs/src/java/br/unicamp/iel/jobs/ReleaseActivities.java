package br.unicamp.iel.jobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Setter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.tool.api.Session;

import br.unicamp.iel.logic.ReadInWebCommonLogic;
import br.unicamp.iel.logic.SakaiProxy;
import br.unicamp.iel.model.Property;

public class ReleaseActivities implements Job {

    @Setter
    private ReadInWebCommonLogic common;

    @Setter
    private SakaiProxy sakaiProxy;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sakaiProxy.adminSessionStart();

        List<Site> classes = sakaiProxy.getReadInWebSites(1L);

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

            System.out.println("Today: " + today + ", last: " + releaseDate + ", Week size  " + week());
            if(today >= common.getStartDate(riwClass).getTime()){
                if(today - releaseDate > week()){
                    // Release activities
                    System.out.println("Release Activities! " + riwClass.getTitle());
                    common.releaseActivities(riwClass);
                    sakaiProxy.updateStringProperty(riwClass,
                            Property.COURSELASTRELEASEDATE.getName(),
                            df.format(new Date(today)));
                } else {
                    System.out.println("Do not release Activities! " + riwClass.getTitle());
                    common.releaseActivities(riwClass);
                    sakaiProxy.updateStringProperty(riwClass,
                            Property.COURSELASTRELEASEDATE.getName(),
                            df.format(new Date(today)));
                }
            } else {
                common.releaseActivities(riwClass);
                sakaiProxy.updateStringProperty(riwClass,
                        Property.COURSELASTRELEASEDATE.getName(),
                        df.format(new Date(today)));
            }
        }
    }

    private Long week(){
        // return new Long(60*60*24*7*1000);
        return new Long(60*5);
    }
}
