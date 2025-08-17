#!/bin/bash

# RMI Calculator Automated Test Script
echo "=== RMI Calculator Test Automation ==="

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven not found. Please install Maven first."
    exit 1
fi

# Compile the project
echo "1. Compiling project..."
mvn clean compile
if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

# Check if RMI registry is running
echo "2. Checking RMI registry..."
if ! nc -z localhost 1099 2>/dev/null; then
    echo "Warning: RMI registry not detected on port 1099"
    echo "Please ensure your RMI server is running before continuing"
    read -p "Press Enter to continue or Ctrl+C to exit..."
fi

# Run single client tests
echo "3. Running single client tests..."
mvn test -Dtest=CalculatorTest
SINGLE_RESULT=$?

# Run multiple client tests (sequential with stack cleanup)
echo "4. Running multiple client tests with proper isolation..."

# Function to run individual test methods
run_isolated_test() {
    local client_num=$1
    local test_method=$2
    echo "  Client $client_num: Testing $test_method"
    mvn test -Dtest=CalculatorTest#$test_method -q
    return $?
}

# Tests each method individually to avoid shared state issues
MULTI_CLIENT_PASSED=0
MULTI_CLIENT_TOTAL=0

echo "  Testing concurrent push/pop operations..."
for i in {1..3}; do
    (
        run_isolated_test $i "testPushAndPopSingleClient"
        exit $?
    ) &
    PIDS[$i]=$!
done

for i in {1..3}; do
    wait ${PIDS[$i]}
    if [ $? -eq 0 ]; then
        ((MULTI_CLIENT_PASSED++))
    fi
    ((MULTI_CLIENT_TOTAL++))
done

echo "  Testing concurrent delay operations..."
for i in {1..2}; do
    (
        run_isolated_test $i "testDelayPop"
        exit $?
    ) &
    DELAY_PIDS[$i]=$!
done

# Wait for delay tests
for i in {1..2}; do
    wait ${DELAY_PIDS[$i]}
    if [ $? -eq 0 ]; then
        ((MULTI_CLIENT_PASSED++))
    fi
    ((MULTI_CLIENT_TOTAL++))
done

# Report results
echo ""
echo "=== TEST RESULTS ==="
echo "Single Client Test: $([ $SINGLE_RESULT -eq 0 ] && echo "PASSED" || echo "FAILED")"
echo "Multi Client Tests: $MULTI_CLIENT_PASSED/$MULTI_CLIENT_TOTAL passed"

# Final status
if [ $SINGLE_RESULT -eq 0 ] && [ $MULTI_CLIENT_PASSED -eq $MULTI_CLIENT_TOTAL ]; then
    echo "Overall: ALL TESTS PASSED ✓"
    exit 0
else
    echo "Overall: SOME TESTS FAILED ✗"
    exit 1
fi