package Example;

public class Event {

    private String title;
    private String address;
    private String description;

    public Event(String title, String address, String description) {
        this.title = title;
        this.address = address;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

}
