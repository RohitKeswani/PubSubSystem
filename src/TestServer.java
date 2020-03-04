import Controller.ServerController;

public class TestServer {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(5000);
        serverController.waitForClientConnection();

    }
}
