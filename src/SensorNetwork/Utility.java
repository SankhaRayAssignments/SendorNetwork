/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorNetwork;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Saheb
 */
public class Utility {

    public static boolean isNum(String text) {

        if (text.length() == 0) {
            return false;
        }
        char ctext[] = text.toCharArray();
        for (char c : ctext) {
            if (!(c >= 48 && c <= 57)) {
                return false;
            }
        }
        return true;
    }

    public static void SetFramePosition(JFrame frame) {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int xPos = (screenSize.width - frame.getWidth())/4;
        int yPos = (screenSize.height - frame.getHeight())/4;
        frame.setLocation(xPos, yPos);
        //frame.setLocation(screenSize.width/4, screenSize.height/4);        
        
    }
    private static File getAbsoluteFilePath(JFrame frame) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public static void SaveData(JFrame frame) {
        try {
            File outFile = null;
            while (true) {
                // Choose a File to Store Data
                outFile = getAbsoluteFilePath(frame);
                if (outFile.exists()) {
                    if (JOptionPane.showConfirmDialog(frame, "Over Write Existing : " + outFile.getName() + " ?",
                            "Confirm Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        return;
                    } else {
                        break;
                    }
                } else {
                    outFile.createNewFile();
                    break;
                }
            }

            String key, val;
            Properties prop = new Properties();
            key = "NUMNODES";
            val = "" + Globals.sNodeList.size();
            prop.setProperty(key, val);
            prop.store(new FileOutputStream(outFile), "");

            key = "NODE_RANGE";
            val = "" + Globals.sNodeMaxSensingRange;
            prop.setProperty(key, val);
            prop.store(new FileOutputStream(outFile), "");

            int iNode = 1;
            for (Node n : Globals.sNodeList) {
                key = "Node" + iNode;
                val = n.getX() + ":" + n.getY();
                prop.setProperty(key, val);
                prop.store(new FileOutputStream(outFile), "");
                iNode++;
            }
            JOptionPane.showMessageDialog(frame, "Writing file successful.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    public static void LoadData(JFrame frame) {

        try {
            String key, val;
            Properties prop = new Properties();
            prop.load(new FileInputStream(getAbsoluteFilePath(frame)));

            key = "NUMNODES";
            int iMaxNodes = Integer.parseInt(prop.getProperty(key));

            if (iMaxNodes != 0) {
                Globals.sNodeList = new ArrayList<Node>();
            } else {
                throw new IOException();
            }

            key = "NODE_RANGE";
            Globals.sNodeMaxSensingRange = Integer.parseInt(prop.getProperty(key));

            StringTokenizer st;
            for (int i = 0; i < iMaxNodes; i++) {
                key = "Node" + (i + 1);
                val = prop.getProperty(key);
                st = new StringTokenizer(val, ":");
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                Globals.sNodeList.add(new Node(x, y, (i + 1)));
            }
            JOptionPane.showMessageDialog(frame, "Loading from file successful.");
            //System.out.println("Writing file successful.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    public static void WriteMessageToFile(JFrame frame, ArrayList<String> messages) {
        try {
            File outFile = null;
            while (true) {
                // Choose a File to Store Data
                outFile = getAbsoluteFilePath(frame);
                if (outFile.exists()) {
                    if (JOptionPane.showConfirmDialog(frame, "Over Write Existing : " + outFile.getName() + " ?",
                            "Confirm Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        return;
                    } else {
                        break;
                    }
                } else {
                    outFile.createNewFile();
                    break;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile.getPath()));
            for (String eachMsg : messages) {
                writer.write(eachMsg);
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(frame, "Writing file successful.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    public static void GenerateNodes(int numNodes, int range, int w, int h) {
        Globals.sNodeMaxSensingRange = range;

        Globals.sNodeList = new ArrayList<Node>();
        for (int i = 0; i < numNodes; i++) {
            Dimension dim = new Dimension(w, h);
            Globals.sNodeList.add(new Node(dim, i + 1));
        }
    }

    public static void CalculateEdges() {
        Globals.sEdges = new ArrayList<Edge>();
        int dx, dy;
        float distance;
        for (Node n1 : Globals.sNodeList) {
            for (Node n2 : Globals.sNodeList) {
                // Skip it is a same node
                if (n1.equals(n2)) {
                    continue;
                }
                dx = Math.abs(n1.getX() - n2.getX());
                dy = Math.abs(n1.getY() - n2.getY());
                distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                if (distance <= Globals.sNodeMaxSensingRange && distance != 0) {
                    Globals.sEdges.add(new Edge(n1, n2, distance));
                }
            }
        }
    }
}
