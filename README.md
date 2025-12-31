DSFinalProject
===============

Team
----
Salvatore Gallizzi 
Yasir Pervaiz
Darius Beckford

(Please replace the placeholders above with your actual names before submission.)

Overview
--------
This project implements a small command-line system that generates directed, weighted graphs using a Blum-Blum-Shub (BBS) pseudorandom generator. Features:
- Random graph generation (nodes in [3,15], edges between numNodes and numNodes*3, weights in [1,20])
- Printed adjacency list on the console
- Find a path from node x to node y (DFS; first path found) and print its total cost
- Detect a directed cycle and print the vertices forming one detected cycle
- Before generating a new graph, the program saves the current graph and any last-found path/cycle to a timestamped text file in `saved_graphs/`

Requirements
------------
- Java JDK 8 or later (installed and configured in IntelliJ IDEA)
- IntelliJ IDEA (Community or Ultimate) recommended for running and testing

Files of interest
-----------------
- src/Main.java           — interactive menu and program flow
- src/Graph.java          — graph representation, generation, path/cycle algorithms
- src/Edge.java           — edge model
- src/BlumBlumShub.java   — Blum-Blum-Shub PRNG
- src/FileManager.java    — saves graph and last path/cycle to file
- saved_graphs/           — program creates this directory and places backups there

Interactive usage (IntelliJ IDEA)
---------------------------------
Follow these steps to open and run in IntelliJ IDEA.

- Open IntelliJ IDEA.
- Create a new project.
- Add .java files into src/ directory.
- Run Main.java
- Follow on-screen prompts to generate graphs, find paths, detect cycles, and save graphs. (graphs are saved automatically before generating a new one)