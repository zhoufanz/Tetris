package ui.window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TextControl extends JTextField {

	private String methodName;
	
	private int keyCode;
	
	public TextControl(int x, int y, int width, int height, String methodName) {
		//设置文本框的位置
		this.setBounds(x, y, width, height);
		this.methodName = methodName;
		this.addKeyListener(new KeyAdapter() {
			//释放某个键时调用此方法
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
		//显示功能键
		this.setText(KeyEvent.getKeyText(this.keyCode));
	}
	
}
