import java.util.*;

/**
 * Directed, weighted graph representation.
 *
 * Nodes are indexed 0..numNodes-1. The adjacency list is stored as a map
 * from node index to a list of outgoing {@link Edge} objects. The class
 * provides utilities to generate a random graph, print it, find a path
 * between two nodes, compute path cost, and detect a cycle.
 */
public class Graph {

    private int numNodes;
    private Map<Integer, List<Edge>> adj;

    /**
     * Construct an empty graph with the specified number of nodes.
     * Initially there are no edges.
     *
     * @param numNodes number of nodes (indexed 0..numNodes-1)
     */
    public Graph(int numNodes) {
        this.numNodes = numNodes;
        adj = new HashMap<>();

        for (int i = 0; i < numNodes; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    /**
     * Return the number of nodes in the graph.
     *
     * @return number of nodes
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Add a directed edge from `from` to `to` with the specified weight.
     *
     * @param from source node index
     * @param to destination node index
     * @param weight edge weight (cost)
     */
    public void addEdge(int from, int to, int weight) {
        adj.get(from).add(new Edge(to, weight));
    }

    /**
     * Get the outgoing edges (neighbors) for a given node.
     *
     * @param node node index
     * @return list of outgoing edges (may be empty)
     */
    public List<Edge> getNeighbors(int node) {
        return adj.get(node);
    }

    /**
     * Generate a random directed weighted graph using the provided Blum-Blum-Shub
     * pseudorandom generator. The number of nodes is chosen in [3,15], the number
     * of edges between numNodes and numNodes*3, and edge weights in [1,20].
     *
     * @param bbs BlumBlumShub instance used for pseudorandom numbers
     * @return newly created Graph
     */
    public static Graph generateRandomGraph(BlumBlumShub bbs) {
        int numNodes = bbs.nextInt(3, 15);
        Graph g = new Graph(numNodes);
        int numEdges = bbs.nextInt(numNodes, numNodes * 3);

        for (int i = 0; i < numEdges; i++) {
            int from = bbs.nextInt(0, numNodes - 1);
            int to   = bbs.nextInt(0, numNodes - 1);
            int w    = bbs.nextInt(1, 20);
            g.addEdge(from, to, w);
        }
        return g;
    }

    /**
     * Print the graph to standard output in a readable adjacency-list format.
     * Includes node and edge counts and lists outgoing edges per node.
     */
    public void printGraph() {
        // Formatted adjacency list with counts
        int edgeCount = 0;
        for (List<Edge> list : adj.values()) edgeCount += list.size();

        System.out.println("Directed Weighted Graph: nodes=" + numNodes + ", edges=" + edgeCount);
        System.out.println("----------------------------------------");

        for (int i = 0; i < numNodes; i++) {
            List<Edge> neighbors = adj.get(i);
            // Align node numbers in a 3-char field
            System.out.print(String.format("%3d:", i));

            if (neighbors == null || neighbors.isEmpty()) {
                System.out.println("  (no outgoing edges)");
                continue;
            }

            System.out.print(" ");
            for (int j = 0; j < neighbors.size(); j++) {
                Edge e = neighbors.get(j);
                System.out.print(String.format("-> %d(w=%d)", e.getTo(), e.getWeight()));
                if (j < neighbors.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------");
    }


    /**
     * Find any path from start to end using depth-first search (DFS).
     * Note: this returns the first path found by DFS and is not guaranteed
     * to be the shortest or minimum-cost path.
     *
     * @param start start node index
     * @param end destination node index
     * @return list of node indices forming the path, or null if none exists
     */
    public List<Integer> findAnyPath(int start, int end) {
        boolean[] visited = new boolean[numNodes];
        List<Integer> path = new ArrayList<>();

        if (dfsFind(start, end, visited, path)) {
            return path;
        }

        return null; // no path found
    }

    // DFS helper for path search
    private boolean dfsFind(int current, int end, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        // If we've reached the destination, return true
        if (current == end) return true;

        // Explore neighbors in order
        for (Edge e : adj.get(current)) {
            int next = e.getTo();
            if (!visited[next]) {
                if (dfsFind(next, end, visited, path)) return true;
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }


    /**
     * Calculate the total cost (sum of weights) for the given path.
     *
     * @param path list of node indices in traversal order
     * @return total edge weight sum for the path
     */
    public int calculateCost(List<Integer> path) {
        int cost = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);

            for (Edge e : adj.get(from)) {
                if (e.getTo() == to) {
                    cost += e.getWeight();
                    break;
                }
            }
        }

        return cost;
    }


    /**
     * Detect any directed cycle in the graph. Returns one cycle if found; if
     * multiple cycles exist, only a single cycle (the first discovered) is returned.
     *
     * @return list of node indices forming a cycle, or null if the graph is acyclic
     */
    public List<Integer> findCycle() {
        boolean[] visited = new boolean[numNodes];
        boolean[] inStack = new boolean[numNodes];
        List<Integer> cycle = new ArrayList<>();

        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                if (dfsCycle(i, visited, inStack, cycle)) {
                    return cycle;  // return the first found cycle
                }
            }
        }

        return null; // no cycle
    }

    // DFS helper for cycle detection
    private boolean dfsCycle(int current, boolean[] visited, boolean[] inStack, List<Integer> cycle) {
        visited[current] = true;
        inStack[current] = true;
        cycle.add(current);

        for (Edge e : adj.get(current)) {
            int next = e.getTo();

            if (!visited[next]) {
                if (dfsCycle(next, visited, inStack, cycle)) return true;
            } else if (inStack[next]) {
                // Cycle found — trim the cycle to start at 'next'
                int index = cycle.indexOf(next);
                List<Integer> sub = new ArrayList<>(cycle.subList(index, cycle.size()));
                cycle.clear();
                cycle.addAll(sub);
                return true;
            }
        }

        inStack[current] = false;
        cycle.remove(cycle.size() - 1);
        return false;
    }
}
