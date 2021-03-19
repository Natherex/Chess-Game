@echo off
rem Compiles all the folders.
javac board/*.java
javac gamestate/*.java
javac pieces/*.java

rem Compiles and runs the main GUI file.
javac GUIMain.java
java GUIMain
PAUSE