import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.File;

/**
 * Simple command-line driver for the graph system.
 * <p>
 * Provides a small interactive menu allowing the user to:
 * - generate a new random directed weighted graph (uses BlumBlumShub RNG)
 * - display the current graph
 * - find a path between two vertices and display its cost
 * - detect and display a cycle if one exists
 * - exit the program
 *
 * Before generating a new graph the program saves the previous graph and any
 * last-found path/cycle to the `saved_graphs/` directory.
 */
public class Main {

    /**
     * Entry point. Runs the interactive menu loop until the user chooses to exit.
     * User input is read from standard input.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = null;
        List<Integer> lastPath = null;
        List<Integer> lastCycle = null;

        // Create a BBS instance with random p, q, seed
        BlumBlumShub bbs = BlumBlumShub.createRandomBBS(32);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        while (true) {
            System.out.println("\n1. Generate New Graph");
            System.out.println("2. Show Graph");
            System.out.println("3. Find a Path");
            System.out.println("4. Detect Cycle");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (graph != null) {
                        // Ensure dedicated save directory exists
                        String directory = "saved_graphs";
                        File d = new File(directory);
                        if (!d.exists()) {
                            boolean ok = d.mkdirs();
                            if (!ok) System.out.println("Warning: could not create directory '" + directory + "'");
                        }

                        String filename = directory + File.separator + "graph_backup_" + LocalDateTime.now().format(fmt) + ".txt";
                        FileManager.saveGraph(graph, lastPath, lastCycle, filename);
                    }
                    graph = Graph.generateRandomGraph(bbs);
                    lastPath = null;
                    lastCycle = null;
                    System.out.println("New graph generated!");
                    break;

                case 2:
                    if (graph != null) {
                        graph.printGraph();
                    } else {
                        System.out.println("No graph yet.");
                    }
                    break;

                case 3:
                    if (graph == null) {
                        System.out.println("Generate a graph first.");
                        break;
                    }
                    System.out.print("Start: "); int s = sc.nextInt();
                    System.out.print("End: ");   int e = sc.nextInt();

                    List<Integer> path = graph.findAnyPath(s, e);
                    lastPath = path; // track last path (may be null)

                    if (path == null) {
                        System.out.println("No path found.");
                    }
                    else {
                        System.out.println("Path: " + path);
                        System.out.println("Cost: " + graph.calculateCost(path));
                    }
                    break;

                case 4:
                    if (graph == null) {
                        System.out.println("Generate a graph first.");
                        break;
                    }
                    List<Integer> cycle = graph.findCycle();
                    lastCycle = cycle; // track last cycle (may be null)

                    if (cycle == null) {
                        System.out.println("No cycle found.");
                    }
                    else {
                        System.out.println("Cycle: " + cycle);
                    }
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }
}
