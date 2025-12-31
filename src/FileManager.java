import java.io.PrintWriter;
import java.util.List;

/**
 * Utility for persisting graphs and related information to disk.
 *
 * The saved file is plain text and contains:
 * - number of nodes
 * - adjacency list (one line per node)
 * - the last discovered path (or "None")
 * - the last discovered cycle (or "None")
 */
public class FileManager {

    /**
     * Save the given graph, last path, and last cycle to a text file.
     *
     * @param graph the graph to persist
     * @param lastPath last path found (may be null)
     * @param lastCycle last cycle found (may be null)
     * @param filename target filename to write
     */
    public static void saveGraph(Graph graph,
                                 List<Integer> lastPath,
                                 List<Integer> lastCycle,
                                 String filename)
    {
        try (PrintWriter out = new PrintWriter(filename)) {

            out.println("===== Saved Graph =====");
            out.println("Number of nodes: " + graph.getNumNodes());
            out.println();

            out.println("Adjacency List:");
            for (int i = 0; i < graph.getNumNodes(); i++) {
                out.print(i + " -> ");

                // use Graph.getNeighbors(int) (existing API) instead of nonexistent getAdj
                for (Edge e : graph.getNeighbors(i)) {
                    out.print("(" + e.getTo() + ", w=" + e.getWeight() + ") ");
                }
                out.println();
            }

            out.println();
            out.println("===== Last Path =====");
            out.println(lastPath == null ? "None" : lastPath.toString());

            out.println();
            out.println("===== Last Cycle =====");
            out.println(lastCycle == null ? "None" : lastCycle.toString());

            System.out.println("Graph saved to " + filename);

        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
