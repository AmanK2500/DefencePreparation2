package exam.defencepreparation.Login_SetUp;

/**
 * Created by AkshayeJH on 19/06/17.
 */

public class Users {

    public String name;
    public String image;
    public String status;
    public String thumb_image;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Users(String mobile, String address) {
        this.mobile = mobile;
        this.address = address;
    }

    public String mobile,address;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Users(String view) {
        this.view = view;
    }

    public  String view;




    public Users(){

    }

    public Users(String name, String image, String status, String thumb_image) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

}
