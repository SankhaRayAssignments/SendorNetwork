/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

import java.util.ArrayList;

/**
 *
 * @author Saheb
 */
public class CommNode {

    public CommNode(int numNdode, int level) {
        mNodeNumber = numNdode;
        mLevelInGraph = level;
    }

    public void addEdge(int edge) {
        if (mEdges == null) {
            mEdges = new ArrayList<Integer>();
        }
        if (!isExist(edge)) {
            mEdges.add(new Integer(edge));
        }
    }

    public int getDegree() {
        if (mEdges != null) {
            return mEdges.size();
        } else {
            return 0;
        }
    }

    public int getEdge(int index) {
        if (index < getDegree()) {
            Integer I = (Integer) mEdges.get(index);
            return I.intValue();
        } else {
            return 0;
        }
    }

    public int getNodeNumber() {
        return mNodeNumber;
    }

    public int getLevel() {
        return mLevelInGraph;
    }

    public void deleteEdge(int edge) {
        mEdges.remove(new Integer(edge));
    }

    @Override
    public String toString() {
        return new String("Node : " + mNodeNumber + " Level : " + mLevelInGraph + " Degree : " + getDegree());
    }

    private boolean isExist(int edge) {
        boolean bExist = false;
        for (Integer edges : mEdges) {
            if (edge == edges.intValue()) {
                bExist = true;
                break;
            }
        }
        return bExist;
    }
    // Variables declaration
    private int mNodeNumber;
    private int mLevelInGraph;
    private ArrayList<Integer> mEdges;
}
