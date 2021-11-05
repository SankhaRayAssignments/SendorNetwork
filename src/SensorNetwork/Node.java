/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

import java.awt.geom.Dimension2D;
import java.util.Random;

/**
 *
 * @author Saheb
 */
public class Node {

    public Node(int x, int y, int nodeNum) {
        this.x_pos = x;
        this.y_pos = y;
        this.m_NodeNumber = nodeNum;
    }

    public Node(Dimension2D dim, int nodeNum) {
        int maxW = (int) dim.getWidth();
        int maxH = (int) dim.getHeight();
        Random RValue = new Random();
        this.x_pos = RValue.nextInt(maxW);
        this.y_pos = RValue.nextInt(maxH);
        this.m_NodeNumber = nodeNum;

        // Adjust Positions
        this.x_pos = Math.max(this.x_pos, 5);
        this.x_pos = Math.min(this.x_pos, maxW - 50);
        this.y_pos = Math.max(this.y_pos, 100);
        this.y_pos = Math.min(this.y_pos, maxH - 50);
    }

    public int getX() {
        return x_pos;
    }

    public int getY() {
        return y_pos;
    }

    public int getNodeNumber() {
        return m_NodeNumber;
    }

    public boolean equals(Node n) {
        return this.x_pos == n.getX() && this.y_pos == n.getY();
    }

    @Override
    public String toString() {
        return ("Node : " + m_NodeNumber + " x=" + x_pos + ",y=" + y_pos);
    }
    // Variables declaration
    private int x_pos, y_pos;
    private int m_NodeNumber;
}
