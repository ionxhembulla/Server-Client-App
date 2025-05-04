import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/// Client class for a multi-threaded chat application.
/// This client connects to the server, sends messages from the user,
/// and displays messages received from the server.
///
public class Client {
    private static final String SERVER_ADDRESS = "192.168.1.181";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Setup connection to server
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Process messages from the server in a separate thread
        Thread messageReceiver = new Thread(new MessageReceiver(in));
        messageReceiver.start();

        try {
            // Wait for the server to request a name
            String serverMessage = in.readLine();
            if (serverMessage.equals("SUBMITNAME")) {
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();
                out.println(name);
            }

            // Main loop to send messages
            System.out.println("Start typing messages (type '/quit' to exit):");
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
                if (userInput.toLowerCase().equals("/quit")) {
                    break;
                }
            }
        } finally {
            // Clean up
            socket.close();
            scanner.close();
            System.exit(0);
        }
    }

    /**
     * A thread that receives messages from the server and displays them to the user.
     */
    private static class MessageReceiver implements Runnable {
        private BufferedReader in;

        public MessageReceiver(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println("Connection to server lost: " + e.getMessage());
            }
        }
    }
}