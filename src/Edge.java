/**
 * Represents a directed, weighted edge in the graph.
 * Stores the destination node index and the edge weight.
 */
public class Edge {
    private int to;
    private int weight;

    /**
     * Create a new Edge from current node to the given destination with the specified weight.
     *
     * @param to destination node index
     * @param weight weight (cost) of the edge
     */
    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    /**
     * Get the destination node index for this edge.
     *
     * @return destination node index
     */
    public int getTo() {
        return to;
    }

    /**
     * Get the weight (cost) for this edge.
     *
     * @return edge weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * String representation used in printing/logging.
     * Example: (3, w=10)
     */
    @Override
    public String toString() {
        return "(" + to + ", w=" + weight + ")";
    }
}
