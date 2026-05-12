import com.phidget22.*;

public class scanports {
    public static void main(String[] args) throws Exception {

        Net.addServer("", "192.168.100.1", 5661, "", 0);

        Manager manager = new Manager();

        manager.addAttachListener(e -> {
            Phidget p = e.getChannel();

            try {
                System.out.println(
                    "Attached: " +
                    p.getChannelClassName() +
                    " | Hub Port: " +
                    p.getHubPort()
                );
            } catch (PhidgetException ex) {
                ex.printStackTrace();
            }
        });

        manager.open();

        while(true) {
            Thread.sleep(1000);
        }
    }
}