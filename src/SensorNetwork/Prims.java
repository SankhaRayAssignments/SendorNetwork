/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Saheb
 */
public class Prims {

    public Prims(ArrayList<Edge> graphEdges, int numNodes) {

        miNumNodes = numNodes;
        m_GraphEdges = (ArrayList<Edge>) graphEdges.clone();
    }

    public ArrayList<Edge> getSpanningTreeEdges() {
        return m_GraphEdges;
    }

    public DefaultTreeModel getTree() {
        return SpanningTree;
    }

    public DefaultMutableTreeNode getRoot() {
        return (DefaultMutableTreeNode) SpanningTree.getRoot();
    }

    public void startAlgo() {

        SetNodeUsed = new HashSet<String>();
        bAlgoFinished = false;
        // Find Lightest Edge
        Edge lightest = findLighestEdge(m_GraphEdges);
        SetNodeUsed.add(new String(lightest.getV1().getNodeNumber() + ""));
        while (SetNodeUsed.size() != miNumNodes && !bAlgoFinished) {
            PrimStep();
        }

        // Construct Spanning Tree with CHOSEN Edges, stats Node 1 as Root
        SetNodeUsed = new HashSet<String>();
        SetNodeUsed.add(new String("1"));
        root = new DefaultMutableTreeNode(Globals.sNodeList.get(0));
        ConstructSpanningTree(root, 1);
        SpanningTree = new DefaultTreeModel(root);
        SpanningTree.setRoot(root);
    }

    private void PrimStep() {

        ArrayList<Edge> adjacent_edges = new ArrayList<Edge>();
        for (String sCurNode : SetNodeUsed) {
            int currNode = Integer.parseInt(sCurNode);
            ArrayList<Edge> edges = findAdjacentEdges(currNode);
            for (Edge e : edges) {
                e.setStatus(Edge.CONSIDERED);
                adjacent_edges.add(e);
                //System.out.print(e);
            }
        }

        Edge sel_edge = findLighestEdge(adjacent_edges);
        if (null == sel_edge) {
            bAlgoFinished = true;
            return;
        }

        int iNode = sel_edge.getV2().getNodeNumber();
        Edge edge_search = new Edge(sel_edge.getV2(), sel_edge.getV1(), sel_edge.getWeight());
        Edge temp_store = null;
        for (Edge e : m_GraphEdges) {
            if (e.equals(edge_search)) {
                temp_store = e;
                break;
            }
        }

        sel_edge.setStatus(Edge.CHOSEN);
        temp_store.setStatus(Edge.CHOSEN);
        SetNodeUsed.add(new String("" + iNode));
        //System.out.println("iNode=" + iNode );
        //System.out.println(sel_edge + "" + temp_store);
    }

    private Edge findLighestEdge(ArrayList<Edge> graphEdges) {
        float minWeight = 99999;
        Edge sel_edge = null;
        for (Edge e : graphEdges) {
            if (e.getStatus() != Edge.CHOSEN && !SetNodeUsed.contains(new String(e.getV2().getNodeNumber() + ""))) {
                if (e.getWeight() < minWeight) {
                    minWeight = e.getWeight();
                    sel_edge = e;
                    //System.out.print(sel_edge);
                }
            }
        }
        return sel_edge;
    }

    private ArrayList<Edge> findAdjacentEdges(int numNode) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Edge e : m_GraphEdges) {
            if (e.getV1().getNodeNumber() == numNode && e.getStatus() != Edge.CHOSEN) {
                edges.add(e);
            }
        }
        return edges;
    }

    private void ConstructSpanningTree(DefaultMutableTreeNode parent, int parentNodeNum) {

        // Check whether all node covered
        if (SetNodeUsed.size() == miNumNodes) {
            return;
        }

        ArrayList<Edge> chosen_edges = getChosenEdges(parentNodeNum);
        int child_index = 0;
        for (Edge e : chosen_edges) {
            int child = e.getV2().getNodeNumber();
            if (!SetNodeUsed.contains(new String("" + child))) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(e.getV2());
                parent.insert(childNode, child_index);
                SetNodeUsed.add(new String("" + child));
                ConstructSpanningTree(childNode, child);
                child_index++;
            }
        }
    }

    private ArrayList<Edge> getChosenEdges(int node) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Edge e : m_GraphEdges) {

            if (e.getStatus() == Edge.CHOSEN && e.getV1().getNodeNumber() == node) {
                edges.add(e);
                //System.out.println(e);
            }
        }

        return edges;
    }
    // Variables declaration
    private int miNumNodes;
    private boolean bAlgoFinished;
    private HashSet<String> SetNodeUsed;
    private ArrayList<Edge> m_GraphEdges;
    DefaultMutableTreeNode root;
    DefaultTreeModel SpanningTree;
}
