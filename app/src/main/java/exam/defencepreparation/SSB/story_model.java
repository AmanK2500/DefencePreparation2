package exam.defencepreparation.SSB;

public class story_model {

    public story_model(String ID, String story) {
        this.ID = ID;
        Story = story;
    }
    public story_model(){}
    private String  ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStory() {
        return Story;
    }

    public void setStory(String story) {
        Story = story;
    }

    private String  Story;
}
