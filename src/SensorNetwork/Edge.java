/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

/**
 *
 * @author Saheb
 */
public class Edge {

    public Edge(Edge e) {
        this.v1 = e.getV1();
        this.v2 = e.getV2();
        this.Weight = e.getWeight();
        this.Status = e.getStatus();
    }

    public Edge(Node n1, Node n2, float w) {
        this.v1 = n1;
        this.v2 = n2;
        this.Weight = w;
        this.Status = UNUSED;
    }

    public boolean equals(Edge e) {
        return v1.equals(e.getV1()) && v2.equals(e.getV2()) && Weight == e.getWeight();
    }

    public Node getV1() {
        return this.v1;
    }

    public Node getV2() {
        return this.v2;
    }

    public boolean isUsed() {
        return this.Status == UNUSED ? false : true;
    }

    public float getWeight() {
        return Weight;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public int getStatus() {
        return this.Status;
    }

    @Override
    public String toString() {
        return ("\nNode 1:" + v1 + "\nNode 2:" + v2 + "\nWeight=" + Weight + "\nStatus=" + Status);
    }
    // Variables declaration
    private Node v1;
    private Node v2;
    private float Weight;
    private int Status;
    public static final int UNUSED = 0;
    public static final int CONSIDERED = 1;
    public static final int CHOSEN = 2;
}
