import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Server class for a multithreaded chat application.
 * This server accepts multiple client connections and handles each client in a separate thread.
 */
public class Server {
    private static final int PORT = 8080;
    private static HashSet<PrintWriter> writers = new HashSet<>();
    
    public static void main(String[] args) throws Exception {
        System.out.println("Chat Server is running...");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                // Accept a new client connection
                Socket socket = listener.accept();
                System.out.println("New client connected: " + socket);

                // Create a new thread to handle this client
                Thread handlerThread = new Thread(new ClientHandler(socket));
                handlerThread.start();
            }
        }
    }
    
    /**
     * A handler thread class. Handlers are spawned from the listening loop
     * and are responsible for dealing with a single client and broadcasting
     * its messages.
     */
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private String name;
        private BufferedReader in;
        private PrintWriter out;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            try {
                // Create input and output streams for the client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Request a name from this client
                out.println("SUBMITNAME");
                name = in.readLine();
                
                // Add this client to the broadcast list
                synchronized (writers) {
                    writers.add(out);
                }
                
                // Announce the new client
                System.out.println(name + " has joined the chat");
                broadcast(name + " has joined the chat");
                
                // Accept messages from this client and broadcast them
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.toLowerCase().equals("/quit")) {
                        break;
                    }
                    broadcast(name + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // Client is leaving
                if (name != null) {
                    System.out.println(name + " is leaving");
                    broadcast(name + " has left the chat");
                }
                
                // Remove client from the broadcast list
                if (out != null) {
                    synchronized (writers) {
                        writers.remove(out);
                    }
                }
                
                // Close the socket
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        
        /**
         * Broadcasts a message to all clients
         */
        private void broadcast(String message) {
            synchronized (writers) {
                for (PrintWriter writer : writers) {
                    writer.println(message);
                }
            }
        }
    }
}