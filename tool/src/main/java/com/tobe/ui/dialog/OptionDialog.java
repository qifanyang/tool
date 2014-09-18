package com.tobe.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tobe.util.IconRes;
import com.tobe.util.UI;

/**
 * 支持多个选项的dialog
 */
public class OptionDialog{
	/**
	 * -1表示没有选择
	 */
	static int selected = -1;
	public static final int QUESTION = 1;

	/**
	 * 
	 * String[] options = {"ok","no","yes to all","cancel"};
	 *	int index = OptionDialog.showOptionDialog(null,OptionDialog.QUESTION, "content", "title", options, 2);
	 * @param parent
	 * @param type
	 * @param content
	 * @param title
	 * @param options
	 * @param initIndex
	 * @return 点击按钮,返回数组中值的索引,从0开始, 点击关闭窗口返回值为-1;
	 */
	public static int showOptionDialog(Component parent, int type, String content, String title, String[] options, int initIndex){
		
		if(options == null || options.length == 0){
			throw new RuntimeException("options length must > 0");
		}
		if(initIndex < 0 || initIndex >= options.length){
			throw new RuntimeException("initIndex  must > 0 and < options.length");
		}
		
		final JDialog dialog = new JDialog(){

			private static final long serialVersionUID = 1L;
			@Override
			protected void processWindowEvent(WindowEvent e) {
				selected = -1;
				super.processWindowEvent(e);
			}
		};
		
		JLabel label = new JLabel(content, JLabel.CENTER);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		final JButton[] btns = new JButton[options.length];
		for(int size = options.length, index = 0; index < size; index++){
			final JButton btn = new JButton(options[index]);
			btns[index] = btn;
			if(index == initIndex){//TODO 默认选中按钮不管用
				btn.setFocusable(true);
//				System.out.println(options[index]);
			}else {
//				btn.setFocusable(false);
			}
			btn.validate();
			btn.putClientProperty("options.select", index);
			btnPanel.add(btn);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object value = btn.getClientProperty("options.select");
					Integer s = (Integer) value;
					selected = s;
					dialog.dispose();
				}
			});
		}
		dialog.add(new JLabel(getIconByType(dialog, type)), BorderLayout.WEST);
		dialog.add(label, BorderLayout.CENTER);
		dialog.add(btnPanel, BorderLayout.SOUTH);
//		dialog.setLocationRelativeTo(parent);
		dialog.setTitle(title);
		dialog.setIconImage(IconRes.IMAGE_APP);
		dialog.setModal(true);
		dialog.setSize(400, 200);
		UI.setCompentToCenter(dialog);
		dialog.setVisible(true);
		return selected;
	}
	
	private static Icon getIconByType(JDialog dialog, int type){
		switch (type) {
		case QUESTION:
			return IconRes.question_blue_icon;
		default:
			break;
		}
		return IconRes.question_blue_icon;
	}
}
