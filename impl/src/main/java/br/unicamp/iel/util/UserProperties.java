package br.unicamp.iel.util;

import java.util.Date;

import com.eclipsesource.json.JsonObject;

public class UserProperties {
    private JsonObject userProperties;

    public UserProperties(JsonObject userProperties) {
        this.userProperties = userProperties;
    }

    public boolean isUserBlocked(String siteId){
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        return status.get("blocked").asBoolean();
    }

    public void setUserBlocked(String siteId, boolean block) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        if(block){
            status.set("blocked", true);
            status.set("date", System.currentTimeMillis());
            status.set("blocks", status.get("blocks").asInt() + 1);
        } else {
            status.set("blocked", false);
            status.set("date", System.currentTimeMillis());
            status.set("date_sent", (String) null);
        }
    }

    public void setDate(String siteId, Long date) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        status.set("date", date);
    }

    public void setDateSent(String siteId, Date date){
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        status.set("date_sent", date.getTime());
    }

    public boolean hasUserData(String siteId) {
        return userProperties.get("sites").asObject().get(siteId) != null;
    }

    public void addUserData(String siteId, String data) {
        JsonObject jo = userProperties.get("sites").asObject();
        jo.add(siteId, JsonObject.readFrom(data));
    }

    public void cleanExpireDate(String siteId) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();

        status.set("date", (String) null);
    }


    public Long getBlockingDate(String siteId) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        return status.get("date").isNull() ? null : status.get("date").asLong();
    }

    @Override
    public String toString() {
        return userProperties.toString();
    }

    public Integer getNumBlocks(String siteId) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        return status.get("blocks").asInt();
    }

    public boolean hasSentJustification(String siteId) {
        JsonObject status = userProperties.get("sites").asObject()
                .get(siteId).asObject()
                .get("status").asObject();
        return !status.get("date_sent").isNull();
    }
}
