import Controller.*;
import models.TypeOfPacket;

public class TestSubscriber {
    public static void main(String[] args) {
        SubscriberController subscriberController = (SubscriberController)
                new Common().getConnectionObject(TypeOfPacket.Subscriber.toString());
    }
}
