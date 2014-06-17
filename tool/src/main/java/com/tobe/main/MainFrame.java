package com.tobe.main;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.tobe.ui.menu.TopMenu;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final Image img = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("bird48.png"));
	 public MainFrame() {
	        initComponents();
//	        designer.newPanel();
	        setIconImage(img);
	    }

	    /** This method is called from within the constructor to
	     * initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is
	     * always regenerated by the Form Editor.
	     */
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	    private void initComponents() {

//	        designer = new dyno.swing.designer.main.DesigningPanel();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("游戏辅助工具");
	        TopMenu menu = new TopMenu();
	        setJMenuBar(menu);
	        getContentPane().add(new MainPanel(), java.awt.BorderLayout.WEST);

	        pack();
	    }// </editor-fold>//GEN-END:initComponents
	    
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
//	        Introspector.setBeanInfoSearchPath(new String[]{"dyno.swing.designer.beaninfo"});
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception ex) {
	        }
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                MainFrame frame = new MainFrame();
	                frame.setSize(1146, 832);
	                frame.setLocationRelativeTo(null);//居中
	                frame.setVisible(true);
	            }
	        });
	    }

}
