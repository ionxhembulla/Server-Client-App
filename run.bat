@echo off
mkdir bin 2>nul

echo Compiling Java files...
javac -d bin src\*.java

if %ERRORLEVEL% == 0 (
    echo Compilation successful!
    echo.
    echo To test the application:
    echo 1. Open a terminal window and run: java -cp bin Server
    echo 2. Open another terminal window and run: java -cp bin Client
    echo 3. Open a third terminal window and run: java -cp bin Client
    echo 4. Follow the prompts in each client window to enter a name and start chatting
    echo 5. Type messages in either client window and observe them appearing in both clients
    echo 6. Type '/quit' in a client window to disconnect that client
    echo.
    echo To run the server: java -cp bin Server
    echo To run a client: java -cp bin Client
) else (
    echo Compilation failed!
)
