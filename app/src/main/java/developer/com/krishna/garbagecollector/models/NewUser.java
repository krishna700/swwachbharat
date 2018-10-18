package developer.com.krishna.garbagecollector.models;

public class NewUser  {

    private String userName, mobNum;

    public NewUser(String userName , String mobNum)
    {
        this.mobNum=mobNum;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
