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

## Testing the Java RMI

1. As this project was developed in maven, firstly check for the maven is installed if not install maven with following command
```bash
maven -version

# Command to install on linux
sudo apt-get install maven
```

2. Compile the maven environment in the root folder
```
mvn clean install
mvn clean compile
mvn clean compile-test
```

3. Run the following command for the single-client test
```
mvn test
```

4. For the automated testing (multiple clients included), run the following commands
```bash
chmod +x test_script.sh
./test_script.sh
```
