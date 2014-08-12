package br.unicamp.iel.model;
// Generated Aug 12, 2014 10:58:07 AM by Hibernate Tools 3.2.2.GA



/**
 * Strategy generated by hbm2java
 */
public class Strategy  implements java.io.Serializable {


     private Long id;
     private Integer position;
     private Integer type;
     private Activity activity;
     private String body;

    public Strategy() {
    }

	
    public Strategy(Activity activity) {
        this.activity = activity;
    }
    public Strategy(Integer position, Integer type, Activity activity, String body) {
       this.position = position;
       this.type = type;
       this.activity = activity;
       this.body = body;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getPosition() {
        return this.position;
    }
    
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    public Activity getActivity() {
        return this.activity;
    }
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }




}


