package br.unicamp.iel.model;
// Generated Jul 25, 2014 3:29:38 PM by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * ReadInWebAccess generated by hbm2java
 */
public class ReadInWebAccess  implements java.io.Serializable {


     private Long id;
     private String user;
     private Date time;
     private String action;
     private String place;
     private Activity activity;

    public ReadInWebAccess() {
    }

	
    public ReadInWebAccess(Date time, String place, Activity activity) {
        this.time = time;
        this.place = place;
        this.activity = activity;
    }
    public ReadInWebAccess(String user, Date time, String action, String place, Activity activity) {
       this.user = user;
       this.time = time;
       this.action = action;
       this.place = place;
       this.activity = activity;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    public String getAction() {
        return this.action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    public String getPlace() {
        return this.place;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    public Activity getActivity() {
        return this.activity;
    }
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }




}


