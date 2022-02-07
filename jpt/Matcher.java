/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpt;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import com.github.javaparser.utils.Pair;

/**
 *
 * @author ridhi
 */
public class Matcher extends javax.swing.JFrame {

    /**
     * Creates new form Matcher
     */
    String folder1, folder2, filePath1,filePath2;
    static ParseCluster pc = new ParseCluster();
	static Test t = new Test();
	
    public Matcher() throws IOException  {
        initComponents();
        initialize();
        
        jButton1.addActionListener(e -> {
            getClusters();
        });
        jButton2.addActionListener(e -> {
            selectFolder2();
        });
        jButton3.addActionListener(e -> {
            try {
                appendChoice();
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jList1.addListSelectionListener(e -> {
            try {
                showNodes();
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jList2.addListSelectionListener(e -> {
            try {
                showFile2();
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jList3.addListSelectionListener(e -> {
            try {
                showOutgoingEdges();
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jList1.setModel(new DefaultListModel<>());
        jList2.setModel(new DefaultListModel<>());
        jList3.setModel(new DefaultListModel<>());

    }
    public void initialize()throws IOException
    {
       
        
    }
    public void appendChoice() throws IOException {
       
    }
    @SuppressWarnings("unchecked")
	public void showOutgoingEdges() throws IOException
    {
    	((DefaultListModel) jList2.getModel()).clear();
    	String classname = jList3.getSelectedValue();
    	Iterator<NeededByClasses> n = t.needed_by_classes.iterator();
    	while (n.hasNext())
    	{
    		
    		NeededByClasses iter = n.next();
    		String c_name = iter.classname;
    		if (c_name != null)
    		{
	    		if (c_name.equals(classname))
	    		{
	    			for (String key : iter.external_called_by_M.keySet())
					{
	    				for (Pair<String,String> entry : iter.external_called_by_M.get(key))
						//for (HashMap.Entry<String,String> entry : iter.external_called_by_M.get(key).entrySet())
						{
							((DefaultListModel) jList2.getModel()).addElement(entry.a);
						}
					}
	    		}
    		}	
    	}
    }
    
    public void showNodes() throws IOException {
        String cluster = jList1.getSelectedValue();
        ((DefaultListModel) jList3.getModel()).clear();
        HashSet<String> set = pc.listClusterNodes(cluster);
        for (String s : set)
        { 
        	 ((DefaultListModel) jList3.getModel()).addElement(s);
        }
    }

    public void showFile2()throws IOException  {
        String file2 = jList2.getSelectedValue();
        if (folder2 != null) {
            file2 = folder2 + "/" + file2;
        }
        filePath2 = file2;
        BufferedReader br = new BufferedReader(new FileReader(file2));

        String st, content = "";
        while ((st = br.readLine()) != null) {
            content = content + st + "\n";
        }
        jTextArea2.setText(content);
    }

    public void selectFolder2() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // optionally set chooser options ...
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fD = chooser.getSelectedFile();
            //File fD = chooser.getCurrentDirectory();
            folder2 = fD.getAbsolutePath();
            if (fD != null && fD.isDirectory()) {
                File[] subFiles = fD.listFiles();
                //System.out.print(folder1 + subFiles.length);
                for (int i = 0; i < subFiles.length; i++) {
                    // jList1.add(subFiles[i].getName(), this);

                    ((DefaultListModel) jList2.getModel()).addElement(subFiles[i].getName());

                }
                //String file1 = jList2.getSelectedValue(); 

                //System.out.print(file1);
            }
            // read  and/or display the file somehow. ....
        } else {
            // user changed their mind
        }
    }

    public void getClusters() {
    	HashSet<String> set = new HashSet<String>();
    	((DefaultListModel) jList1.getModel()).clear();
    	set = pc.listClusters();
    	for (String s : set)
    	{
    		 ((DefaultListModel) jList1.getModel()).addElement(s);
    	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        //jTextArea1 = new javax.swing.JTextArea();
        jList3 = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Get all clusters");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Folder2");

        jScrollPane1.setViewportView(jList1);
        jScrollPane4.setViewportView(jList3);

        jScrollPane2.setViewportView(jList2);

//        jTextArea1.setColumns(20);
//        jTextArea1.setRows(5);
 //       jScrollPane4.setViewportView(jTextArea1);

        jScrollPane3.setViewportView(jScrollPane4);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane5.setViewportView(jTextArea2);

        jButton3.setText("Select");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Reset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(34, 34, 34))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(97, 97, 97)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     * @throws ParseException 
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException, ParseException {
        
    	String[] s = new String[2];
    	t.mainAnalysis(s);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
    	pc.parseClusters(args[2]);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Matcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Matcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Matcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Matcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Matcher().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList3;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    //private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

}
