package developer.com.krishna.garbagecollector.models;

import java.util.ArrayList;

public class ComplainPojo {

    private String message,name,location,imageUrl;
    private Boolean status;


    public ComplainPojo(String message,String name,String location,String imageUrl,Boolean status)
    {
        this.message=message;
        this.name=name;
        this.location=location;
        this.imageUrl=imageUrl;
        this.status=status;
    }
    public ComplainPojo(){}

    public String getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
