package DI;

import java.util.HashSet;

public class Graph {

    static class Node{
        public final String name;
        public final HashSet<Edge> inEdges;
        public final HashSet<Edge> outEdges;
        public Node(String name) {
            this.name = name;
            inEdges = new HashSet<Edge>();
            outEdges = new HashSet<Edge>();
        }
        public Node addEdge(Node node){
            Edge e = new Edge(this, node);
            outEdges.add(e);
            node.inEdges.add(e);
            return this;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    static class Edge{
        public final Node from;
        public final Node to;
        public Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
        @Override
        public boolean equals(Object obj) {
            Edge e = (Edge)obj;
            return e.from == from && e.to == to;
        }
    }
}
