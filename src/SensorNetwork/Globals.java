/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

import java.util.ArrayList;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Saheb
 */
public class Globals {

    // Static Variables declaration
    public static ArrayList<Node> sNodeList;
    public static ArrayList<Edge> sEdges;
    public static ArrayList<Edge> sSpanningTreeEdges;
    public static DefaultTreeModel sSpanningTree;
    public static int sNodeMaxSensingRange;
    public static Prims sPrims;
    public static Scheduler sScheduler;
    public static int sWidth = 1024, sHeight = 768;
}
