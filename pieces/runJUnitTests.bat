@rem This file runs the test file with JUnit.
@set /p id="Enter test file name: "
javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore "%id%"
@PAUSE