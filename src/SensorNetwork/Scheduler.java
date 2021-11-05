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
public class Scheduler {

    public Scheduler(DefaultTreeModel tree) {
        mSpanningTree = tree;
        mRoot = (DefaultMutableTreeNode) mSpanningTree.getRoot();
    }

    public ArrayList<String> getMessages() {
        return mMessages;
    }

    public void PerformSchedulingAlgo() {
        int iRound = 1;
        mMessages = new ArrayList<String>();
        while (mRoot.getChildCount() != 0) {
            mMessages.add(new String("Starting Round : " + iRound));

            InitCommunicationGraph();
            InitMaximalIndependentSet();
            SendData();

            mMessages.add(new String("Completed Round : " + iRound));
            iRound++;
        }
    }

    private void InitCommunicationGraph() {
        // Find Leaves
        mLeafNodesTemp = new ArrayList<DefaultMutableTreeNode>();
        DefaultMutableTreeNode leaf = mRoot.getFirstLeaf();
        mLeafNodesTemp.add(leaf);
        while ((leaf = leaf.getNextLeaf()) != null) {
            mLeafNodesTemp.add(leaf);
        }

        mCommGraph = new ArrayList<CommNode>();
        for (DefaultMutableTreeNode eachLeaf : mLeafNodesTemp) {
            int nodeNum = ((Node) eachLeaf.getUserObject()).getNodeNumber();
            CommNode commNode = new CommNode(nodeNum, eachLeaf.getLevel());

            // Find Other Nodes that have same parents from Graphs
            ArrayList<Integer> adjNodes = getAdjNodes(eachLeaf);
            for (Integer i : adjNodes) {
                commNode.addEdge(i.intValue());
                // Update Existing Other Node Edge Also.
                for (CommNode existingNode : mCommGraph) {
                    int existingNodeNum = existingNode.getNodeNumber();
                    if (existingNodeNum == i.intValue()) {
                        existingNode.addEdge(nodeNum);
                    }
                }
            }
            mCommGraph.add(commNode);
        }
    }

    // Check here.
    private void InitMaximalIndependentSet() {
        HashSet<Integer> ignoreList = new HashSet<Integer>();
        mMaxIndependentSet = new ArrayList<CommNode>();
        for (CommNode eachComm : mCommGraph) {
            int node = eachComm.getNodeNumber();
            boolean bIgnore = false;
            for (Integer i : ignoreList) {
                if (i.intValue() == node) {
                    bIgnore = true;
                    break;
                }
            }
            if (true == bIgnore) {
                continue;
            }
            // Add this node
            mMaxIndependentSet.add(eachComm);
            // Add its edges to Ignore list
            for (int edgeindex = 0; edgeindex < eachComm.getDegree(); edgeindex++) {
                ignoreList.add(new Integer(eachComm.getEdge(edgeindex)));
            }
        }
    }

    private void SendData() {
        int iNumIter = 1;
        while (!mMaxIndependentSet.isEmpty()) {
            // getHighestLevel & getLowestDegree will send data from leaf nodes
            DefaultMutableTreeNode SendingNode = getSendingNode();

            // Print in console
            mMessages.add(new String("Sending Node :" + ((Node) (SendingNode.getUserObject())).getNodeNumber()));

            // Update Communication Graph
            UpdateCommGraph(SendingNode);

            // Update Spanning Tree as Well
            mSpanningTree.removeNodeFromParent(SendingNode);
            iNumIter++;
        }
    }

    private ArrayList<Integer> getAdjNodes(DefaultMutableTreeNode node) {
        int currNode = ((Node) ((DefaultMutableTreeNode) node).getUserObject()).getNodeNumber();
        int parentCurrNode = ((Node) ((DefaultMutableTreeNode) node.getParent()).getUserObject()).getNodeNumber();
        ArrayList<Integer> nodes = new ArrayList<Integer>();

        for (DefaultMutableTreeNode eachNode : mLeafNodesTemp) {
            int otherNode = ((Node) eachNode.getUserObject()).getNodeNumber();
            for (Edge eachEdge : Globals.sSpanningTreeEdges) {
                if (otherNode == currNode) {
                    continue;
                }
                if ((eachEdge.getV1().getNodeNumber() == otherNode && eachEdge.getV2().getNodeNumber() == parentCurrNode) ||
                        (eachEdge.getV2().getNodeNumber() == otherNode && eachEdge.getV1().getNodeNumber() == parentCurrNode)) {
                    nodes.add(new Integer(otherNode));
                    break;
                }
            }
        }
        return nodes;
    }

    private DefaultMutableTreeNode getSendingNode() {
        // Find Highest Level Amoung Leaf Nodes
        int HL = 0;
        for (CommNode eachNode : mMaxIndependentSet) {
            HL = Math.max(HL, eachNode.getLevel());
        }

        // Find Lowest Degree Amoung Leaf Nodes
        int LD = 9999;
        for (CommNode eachNode : mMaxIndependentSet) {
            if (HL == eachNode.getLevel()) {
                LD = Math.min(LD, eachNode.getDegree());
            }
        }

        // HighestLevel & LowestDegree will send data from leaf nodes
        //DefaultMutableTreeNode sel_edge = null;
        for (CommNode eachNode : mMaxIndependentSet) {
            if (eachNode.getLevel() == HL && eachNode.getDegree() == LD) {
                // Search the node in Spanning Tree
                for (DefaultMutableTreeNode node : mLeafNodesTemp) {
                    if (eachNode.getNodeNumber() == ((Node) node.getUserObject()).getNodeNumber()) {
                        return node;
                    }
                }
            }
        }
        return null;
    }

    private void UpdateCommGraph(DefaultMutableTreeNode node) {
        // Remove sending node from Independent Set
        for (CommNode eachNode : mMaxIndependentSet) {
            if (eachNode.getNodeNumber() == ((Node) node.getUserObject()).getNodeNumber()) {
                mMaxIndependentSet.remove(eachNode);
                break;
            }
        }

        // Remove Sending node from leaf nodes as well
        for (DefaultMutableTreeNode eachNode : mLeafNodesTemp) {
            if (((Node) eachNode.getUserObject()).getNodeNumber() == ((Node) node.getUserObject()).getNodeNumber()) {
                mLeafNodesTemp.remove(eachNode);
                break;
            }
        }
    }
    // Variables declaration
    private DefaultTreeModel mSpanningTree;
    private DefaultMutableTreeNode mRoot;
    private ArrayList<DefaultMutableTreeNode> mLeafNodesTemp;
    private ArrayList<CommNode> mCommGraph;
    private ArrayList<CommNode> mMaxIndependentSet;
    private ArrayList<String> mMessages;
}
