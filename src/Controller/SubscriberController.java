package Controller;

public class SubscriberController implements Controller{
    private int port;
    private String address;

    public SubscriberController(String address, int port)
    {
        this.port = port;
        this.address = address;
    }
}
