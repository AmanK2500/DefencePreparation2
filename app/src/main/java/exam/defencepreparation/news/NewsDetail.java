package exam.defencepreparation.news;

/**
 * Created by hp on 5/29/2018.
 */

public class NewsDetail {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NewsDetail(String date) {
        this.date = date;
    }

    private   String date;

    public String getTopic() {
        return topic;
    }

    public NewsDetail(){}
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public NewsDetail(String topic, String detail, String image) {
        this.topic = topic;
        this.detail = detail;
        this.image = image;
    }

    private String  topic;
    private String detail;
    private String image;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    private String view;

}
