# Multi-threaded Chat Application

This is a simple chat application that demonstrates multi-threading in a client/server model. The server can handle multiple clients simultaneously, with each client being managed in a separate thread.

## Overview

The application consists of two main components:

1. **Server**: Accepts connections from multiple clients and handles each client in a separate thread. It broadcasts messages from any client to all connected clients.

2. **Client**: Connects to the server, sends messages from the user, and displays messages received from the server.

> **Note**: The Client.java file has a hardcoded IP address (192.168.1.181). You may need to change this to your server's IP address or to "localhost" for local testing.

## How to Compile

To compile the application, navigate to the project directory and run:

```bash
javac -d bin src/*.java
```

This will compile all Java files and place the compiled classes in the `bin` directory.

## How to Run

### Running the Server

```bash
java -cp bin Server
```

The server will start and listen for client connections on port 8080.

### Running the Client

```bash
java -cp bin Client
```

You can run multiple instances of the client to simulate multiple users chatting.

## How to Use

1. Start the server first.
2. Start one or more client instances.
3. When prompted, enter your name in each client.
4. Start typing messages in any client. All messages will be broadcast to all connected clients.
5. To exit, type `/quit` in the client.

## Multi-threading Implementation

This application demonstrates several key concepts of multi-threading in a client/server model:

1. **Server-side Multi-threading**:
   - The server creates a new thread for each client connection.
   - Each client thread (ClientHandler) handles communication with a specific client.
   - The server maintains a collection of all client output streams for broadcasting messages.
   - Synchronization is used to ensure thread safety when accessing shared resources.

2. **Client-side Multi-threading**:
   - The client uses a separate thread (MessageReceiver) to continuously receive and display messages from the server.
   - The main thread handles user input and sends messages to the server.

This design allows the server to handle multiple clients simultaneously without blocking, and each client can send and receive messages concurrently.

## GitHub Repository

### Cloning the Repository

To clone this repository to your local machine, use the following command:

```bash
git clone https://github.com/YOUR_USERNAME/multi-threaded-chat-app.git
cd multi-threaded-chat-app
```

### Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some feature'`)
5. Push to the branch (`git push origin feature/your-feature`)
6. Open a Pull Request
