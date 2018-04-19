package dev18.noobs.com.noobsdev18.model;

public class UserModel {

    String uid;
    String screenName;
    String skill;
    String favoriteNet;

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getFavoriteNet() {
        return favoriteNet;
    }

    public void setFavoriteNet(String favoriteNet) {
        this.favoriteNet = favoriteNet;
    }

    public UserModel(String uid, String screenName, String skill, String favoriteNet) {
        this.uid = uid;
        this.screenName = screenName;
        this.skill = skill;
        this.favoriteNet = favoriteNet;
    }
}
