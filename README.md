# Random Graph Explorer

Random Graph Explorer is a Java application developed as a final project for a Data Structures course. The project explores the implementation and analysis of **directed, weighted graphs**, focusing on graph traversal, cycle detection, and cost evaluation. To generate graphs, the program uses a cryptographically-inspired pseudorandom number generator (Blum–Blum–Shub) instead of Java’s built-in RNG.

## Features

- Generates random directed, weighted graphs
- Uses an adjacency-list representation
- Depth-first search (DFS) pathfinding between nodes
- Computes total cost of a discovered path
- Detects directed cycles using DFS and recursion stack tracking
- Saves graphs, paths, and cycles to disk for later inspection

## Technologies & Concepts

**Language:** Java  
**Data Structures:** adjacency lists, lists, maps  
**Algorithms:** DFS, cycle detection in directed graphs  
**Mathematical Concepts:** modular arithmetic, prime numbers, pseudorandom number generation  
**Software Design:** object-oriented design, file I/O, separation of concerns 

## Project Structure
```
src/
├── Main.java            # Command-line interface and program entry point
├── Graph.java           # Graph representation and algorithms
├── Edge.java            # Directed, weighted edge abstraction
├── FileManager.java     # Graph persistence utilities
└── BlumBlumShub.java    # Cryptographically-inspired RNG
```

## How to Run

### Prerequisites

- Java 11+

### Steps

1. Compile
   ```
   javac src/*.java
   ```
2. Run
   ```
   java -cp src Main
   ```

## Example Usage
```
1. Generate New Graph
2. Show Graph
3. Find a Path
4. Detect Cycle
5. Exit
```

### Example Output
```
Directed Weighted Graph: nodes=6, edges=12
  0: -> 2(w=5), -> 4(w=9)
  1: -> 3(w=7)
Path: [0, 2, 5]
Cost: 14
Cycle: [1, 3, 4, 1]
```

## Design Notes

- DFS is used for both pathfinding and cycle detection.
- Pathfinding returns the first valid path discovered, not necessarily the shortest or minimum-cost path.
- The random graph generator is educational, not intended for cryptographic use.
- Graphs, paths, and cycles are persisted to text files using `FileManager`.

## Future Improvements

- Implement shortest-path algorithms (BFS, Dijkstra)
- Add unit tests for graph operations
- Visualize graphs using a GUI
- Improve input validation and error handling
