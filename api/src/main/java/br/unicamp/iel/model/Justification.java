package br.unicamp.iel.model;
// Generated Aug 12, 2014 10:58:07 AM by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * Justification generated by hbm2java
 */
public class Justification  implements java.io.Serializable {


     private Long id;
     private Date sentDate;
     private Date evaluatedDate;
     private String explanation;
     private byte state;
     private String user;
     private String site;

    public Justification() {
    }

	
    public Justification(Date sentDate) {
        this.sentDate = sentDate;
    }
    public Justification(Date sentDate, Date evaluatedDate, String explanation, byte state, String user, String site) {
       this.sentDate = sentDate;
       this.evaluatedDate = evaluatedDate;
       this.explanation = explanation;
       this.state = state;
       this.user = user;
       this.site = site;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Date getSentDate() {
        return this.sentDate;
    }
    
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
    public Date getEvaluatedDate() {
        return this.evaluatedDate;
    }
    
    public void setEvaluatedDate(Date evaluatedDate) {
        this.evaluatedDate = evaluatedDate;
    }
    public String getExplanation() {
        return this.explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public byte getState() {
        return this.state;
    }
    
    public void setState(byte state) {
        this.state = state;
    }
    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public String getSite() {
        return this.site;
    }
    
    public void setSite(String site) {
        this.site = site;
    }




}


