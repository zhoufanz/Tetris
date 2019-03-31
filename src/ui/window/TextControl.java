package ui.window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TextControl extends JTextField {

	private String methodName;
	
	private int keyCode;
	
	public TextControl(int x, int y, int width, int height, String methodName) {
		//�����ı����λ��
		this.setBounds(x, y, width, height);
		this.methodName = methodName;
		this.addKeyListener(new KeyAdapter() {
			//�ͷ�ĳ����ʱ���ô˷���
			@Override
			public void keyReleased(KeyEvent e) {
				setKeyCode(e.getKeyCode());
			}
		});
	}

	public String getMethodName() {
		return methodName;
	}

	public int getKeyCode() {
		return keyCode;
	}
	
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		//��ʾ���ܼ�
		this.setText(KeyEvent.getKeyText(this.keyCode));
	}
	
}
