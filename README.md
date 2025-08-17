# Distributed Systems Assignment - 1 (Java RMI Calculator)

**Files:**
- `Calculator.java` - Remote Interface
- `CalculatorImplementation.java` - Server Implementation
- `CalculatorServer.java` - Starts RMI Server
- `CalculatorClient.java` - Interactive Client
- `CalculatorTest.java` - JUnit test file

## Compiling Files

1. Change the working directory to following
```
cd src/main/java
```

2. Compile the files with following command
```
javac *.java
```

3. Start the server file by running the following file
```
java CalculatorServer
```
> Note: Don't run the client file before running the server file. Server runs on port 1099

4. Start the client by running the following file
```
java CalculatorClient
```
> Note: This will prompt you with interactive menu. To view the result of any operations like min, max, etc., you need to run the pop option as the result of the operation is inputed into the stack.

