package com.tobe.actions;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.tobe.handler.CodeBuilderHandler;
import com.tobe.util.IconRes;
import com.tobe.util.UI;

/**
 * 生成代码
 *
 */
public class GenCodeDialogAction implements ActionListener {

	private static JDialog dialog;
	private static ActionContext context;
	
	private static OptionPanel serverOpPanel;
	private static OptionPanel gateOpPanel;
	private static OptionPanel worldOpPanel;
	private static JTabbedPane tabbedPane;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(dialog != null){
			dialog.setVisible(true);
		}else {
			dialog = new JDialog(){
				private static final long serialVersionUID = 1L;
				@Override
				protected void processWindowEvent(WindowEvent e) {
					super.processWindowEvent(e);
					closeDialogReset();
				}
			};
			
			JPanel serverPanel = createServerJPanel();
			JPanel gatePanel = createGateJPanel();
			JPanel worldPanel = createWorldJPanel();
			
			tabbedPane = new JTabbedPane();
			tabbedPane.add("Server Files", serverPanel);
			tabbedPane.add("Gate Files", gatePanel);
			tabbedPane.add("World Files", worldPanel);
			
			dialog.setTitle(UI.translate("gencode"));
			dialog.add(tabbedPane);
			dialog.setIconImage(IconRes.IMAGE_APP);
			dialog.setSize(500, 250);
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			
			context.setAttachComponent(dialog);
		}
	}
	
	private JPanel createServerJPanel(){
		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(new BorderLayout());
		serverOpPanel = new OptionPanel();
		serverPanel.add(serverOpPanel);
		return serverPanel;
	}
	private JPanel createGateJPanel(){
		JPanel gatePanel = new JPanel();
		gatePanel.setLayout(new BorderLayout());
		gateOpPanel = new OptionPanel();
		gatePanel.add(gateOpPanel);
		return gatePanel;
	}
	private JPanel createWorldJPanel(){
		JPanel worldPanel = new JPanel();
		worldPanel.setLayout(new BorderLayout());
		worldOpPanel = new OptionPanel();
		worldPanel.add(worldOpPanel);
		return worldPanel;
	}

	
	public ActionContext getContext() {
		return context;
	}

	public void setContext(ActionContext context) {
		GenCodeDialogAction.context = context;
	}

	/**
	 * 关闭对话框或点击取消需要重置勾选的代码生成checkbox
	 * 
	 */
	private static void closeDialogReset() {
		tabbedPane.setSelectedIndex(0);
		
		serverOpPanel.clearCheckBox();
		gateOpPanel.clearCheckBox();
		worldOpPanel.clearCheckBox();

	}

	static class OptionPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		TitledBorder border;
		private JCheckBox msgCB;
		private JCheckBox handlerCB;
		private JCheckBox beanCB;
		
		public OptionPanel(){
			setLayout(new BorderLayout());
			JPanel selectPanel = new JPanel();
			border = BorderFactory.createTitledBorder("自动生成");
		    selectPanel.setBorder(border);
		    selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
		    
		    beanCB = new JCheckBox(UI.NAME_AUTO_MAKE + "bean");
		    msgCB = new JCheckBox(UI.NAME_AUTO_MAKE + "message");
			handlerCB = new JCheckBox(UI.NAME_AUTO_MAKE + "handler");
			selectPanel.add(beanCB);
		    selectPanel.add(msgCB);
		    selectPanel.add(handlerCB);
		    
		    
		    JButton ok = new JButton(UI.NAME_OK);
		    JButton cancel = new JButton(UI.NAME_CANEL);
		    JPanel btnPanel = new JPanel();
//		    btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		    btnPanel.add(ok);
		    btnPanel.add(cancel);
		
		    
		    JLabel label = new JLabel(UI.translate("messagepool") + "		com.game.message.pool.MessagePool");
		    add(label, BorderLayout.NORTH);
		    add(selectPanel, BorderLayout.CENTER);
		    add(btnPanel, BorderLayout.SOUTH);
		    
		    ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					CodeBuilderHandler handler = new CodeBuilderHandler();
					handler.action(context, beanCB.isSelected(), msgCB.isSelected(), handlerCB.isSelected());
					dialog.dispose();
					closeDialogReset();
				}
			});
		    
		    cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					closeDialogReset();
				}
			});
		}
		
		private void clearCheckBox(){
			beanCB.setSelected(false);
			msgCB.setSelected(false);
			handlerCB.setSelected(false);
		}
	}
}
