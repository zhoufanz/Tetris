package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;

import util.FrameUtil;

public class JFrameSavePoint extends JFrame {
	/**
	 * 确定按钮
	 */
	private JButton bOk = null;
	/**
	 * 显示分数
	 */
	private JLabel lScore = null;
	/**
	 * 名字
	 */
	private JTextField txName = null;
	/**
	 * 显示出错信息
	 */
	private JLabel errorMessage;
	/**
	 * 控制器对象
	 */
	private GameControl gameControl;
	
	public JFrameSavePoint(GameControl gameControl) {
		//获得控制器对象
		this.gameControl = gameControl;
		//设置标题
		this.setTitle("保存记录");
		//边界布局
		this.setLayout(new BorderLayout());
		//设置大小
		this.setSize(256, 128);
		//居中显示
		FrameUtil.setFrameCenter(this);
		//不能自由变换大小
		this.setResizable(false);
		//创建内部面板
		createConfirm();
		//添加事件
		createAction();
	}
	/**
	 * 显示当前分数
	 */
	public void showPoint(String point) {
		//清空输入框
		this.txName.setText("");
		//清空出错信息
		this.errorMessage.setText("");
		//显示当前分数
		this.lScore.setText("您获得的分数为：" + point);
	}
	/**
	 * 添加按钮事件
	 */
	private void createAction() {
		this.bOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isRight(txName.getText())) {
					gameControl.savePoint(txName.getText());
					setVisible(false);
				}
			}
		});
	}
	/**
	 * 判断文本框中的输入的姓名是否合法
	 * @param text
	 * @return
	 */
	private boolean isRight(String name) {
		if(name==null || name.equals("")) {
			this.errorMessage.setText("请输入姓名！");
			return false;
		} else if(name.length() > 10) {
			this.errorMessage.setText("输入的姓名过长！");
			return false;
		}
		this.errorMessage.setText("");
		return true;
	}
	/**
	 * 创建内部面板
	 */
	private void createConfirm() {
		//创建确认面板（流式布局，居中显示）
		JPanel pOk = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//创建出错信息标签
		this.errorMessage = new JLabel();
		//设置前景色为红色
		this.errorMessage.setForeground(Color.RED);
		//确定按钮
		this.bOk = new JButton("确认");
		//将确定按钮添加到确认面板
		pOk.add(bOk);
		//将出错信息标签加入到确认面板
		pOk.add(errorMessage);
		//将确认面板添加到主面板（边界布局south）
		this.add(pOk, BorderLayout.SOUTH);
		//创建显示分数的面板
		JPanel pSorce = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//显示分数
		this.lScore = new JLabel();
		//将分数标签加入显示分数面板
		pSorce.add(lScore);
		//将显示分数面板加入主面板
		this.add(pSorce, BorderLayout.NORTH);
		//创建输入名字的面板
		JPanel pName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//创建显示文字的标签
		JLabel lShowText = new JLabel("请输入您的姓名：");
		//创建输入框
		this.txName = new JTextField(10);
		//将显示文字的标签与输入框加入输入名字面板
		pName.add(lShowText);
		pName.add(txName);
		//将输入名字的面板加入到主面板
		this.add(pName, BorderLayout.CENTER);
	}
}
