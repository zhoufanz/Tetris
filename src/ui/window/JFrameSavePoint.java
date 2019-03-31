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
	 * ȷ����ť
	 */
	private JButton bOk = null;
	/**
	 * ��ʾ����
	 */
	private JLabel lScore = null;
	/**
	 * ����
	 */
	private JTextField txName = null;
	/**
	 * ��ʾ������Ϣ
	 */
	private JLabel errorMessage;
	/**
	 * ����������
	 */
	private GameControl gameControl;
	
	public JFrameSavePoint(GameControl gameControl) {
		//��ÿ���������
		this.gameControl = gameControl;
		//���ñ���
		this.setTitle("�����¼");
		//�߽粼��
		this.setLayout(new BorderLayout());
		//���ô�С
		this.setSize(256, 128);
		//������ʾ
		FrameUtil.setFrameCenter(this);
		//�������ɱ任��С
		this.setResizable(false);
		//�����ڲ����
		createConfirm();
		//����¼�
		createAction();
	}
	/**
	 * ��ʾ��ǰ����
	 */
	public void showPoint(String point) {
		//��������
		this.txName.setText("");
		//��ճ�����Ϣ
		this.errorMessage.setText("");
		//��ʾ��ǰ����
		this.lScore.setText("����õķ���Ϊ��" + point);
	}
	/**
	 * ��Ӱ�ť�¼�
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
	 * �ж��ı����е�����������Ƿ�Ϸ�
	 * @param text
	 * @return
	 */
	private boolean isRight(String name) {
		if(name==null || name.equals("")) {
			this.errorMessage.setText("������������");
			return false;
		} else if(name.length() > 10) {
			this.errorMessage.setText("���������������");
			return false;
		}
		this.errorMessage.setText("");
		return true;
	}
	/**
	 * �����ڲ����
	 */
	private void createConfirm() {
		//����ȷ����壨��ʽ���֣�������ʾ��
		JPanel pOk = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//����������Ϣ��ǩ
		this.errorMessage = new JLabel();
		//����ǰ��ɫΪ��ɫ
		this.errorMessage.setForeground(Color.RED);
		//ȷ����ť
		this.bOk = new JButton("ȷ��");
		//��ȷ����ť��ӵ�ȷ�����
		pOk.add(bOk);
		//��������Ϣ��ǩ���뵽ȷ�����
		pOk.add(errorMessage);
		//��ȷ�������ӵ�����壨�߽粼��south��
		this.add(pOk, BorderLayout.SOUTH);
		//������ʾ���������
		JPanel pSorce = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//��ʾ����
		this.lScore = new JLabel();
		//��������ǩ������ʾ�������
		pSorce.add(lScore);
		//����ʾ���������������
		this.add(pSorce, BorderLayout.NORTH);
		//�����������ֵ����
		JPanel pName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//������ʾ���ֵı�ǩ
		JLabel lShowText = new JLabel("����������������");
		//���������
		this.txName = new JTextField(10);
		//����ʾ���ֵı�ǩ���������������������
		pName.add(lShowText);
		pName.add(txName);
		//���������ֵ������뵽�����
		this.add(pName, BorderLayout.CENTER);
	}
}
