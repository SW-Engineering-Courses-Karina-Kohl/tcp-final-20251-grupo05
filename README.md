[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/zli9RIbW)
##**TCP-Final-20251**

# PokeClicker

## Initial Configurations in VsCode
1. Downlaod JavaFX version xxx.xx.
2. Extract the downloaded files and copy path to the "lib" folder.
3. Configure the "launch.json" file. To do this, you need to go to the launch file, lcoated at:
   .vscode > launch.json
   In configurations, erase the "projectName" line.
   In "vmArgs", paste the path copied before. At Windows, add another "\" rgiht after the ones already existing in the path.

## How to test with JUnit
0. Update sources.txt: dir /s /b *.java > sources.txt
1. Compile in cmd: javac -cp lib\junit-platform-console-standalone-1.11.0.jar -d out @sources.txt
2. Run: java -jar lib\junit-platform-console-standalone-1.11.0.jar --class-path out --scan-class-path
P.S.: It only works on Windows and it must be compiled with CMD, PowerShell won't work.

## Modo de Uso