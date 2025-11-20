package calculator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;

import calculator.app.WinController;

@SuppressWarnings("serial")
public class MyWindow extends JFrame {
	
	public static final Dimension BUTTON_SIZE = new Dimension(60, 60);
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 700;
	private static final boolean DARK_THEME = true; // set to TRUE for dark theme ON
	private final JButton[] nums; // buttons for nums {0, 1, ... , 9}
	private final JButton point_button;
	private final JButton equals_button;
	private final JButton div_button;
	private final JButton mult_button;
	private final JButton sub_button;
	private final JButton add_button;
	private final JButton del_button;
	private final JButton clear_button;
	private final JButton pow_button;
	private final JButton sqrt_button;
	private final JButton abs_button;
	private final JButton neg_button;
	private final JTextArea resArea;
	
	private void initializeButtons(JPanel p_center, JPanel p_right, JPanel p_left, WinController c) {
		
		int[] init_order = {7, 8, 9, 4, 5, 6, 1, 2, 3, 0}; // specify initialization order
		
		for (int k : init_order) {
			if (k == 0) p_center.add(point_button);
			nums[k] = new JButton(Integer.toString(k));
			nums[k].setPreferredSize(BUTTON_SIZE);
			nums[k].setActionCommand(WinController.NUM_PRESSED);
			nums[k].addActionListener(c);
			nums[k].setFocusable(false);
			p_center.add(nums[k]);
		}
		
		p_center.add(equals_button);
		p_right.add(div_button);
		p_right.add(mult_button);
		p_right.add(sub_button);
		p_right.add(add_button);
		div_button.setPreferredSize(BUTTON_SIZE);
		mult_button.setPreferredSize(BUTTON_SIZE);
		sub_button.setPreferredSize(BUTTON_SIZE);
		add_button.setPreferredSize(BUTTON_SIZE);
		div_button.setFocusable(false);
		mult_button.setFocusable(false);
		add_button.setFocusable(false);
		sub_button.setFocusable(false);
		
		add_button.setActionCommand(WinController.PLUS_PRESSED);
		add_button.addActionListener(c);
		sub_button.setActionCommand(WinController.MINUS_PRESSED);
		sub_button.addActionListener(c);
		mult_button.setActionCommand(WinController.TIMES_PRESSED);
		mult_button.addActionListener(c);
		div_button.setActionCommand(WinController.DIV_PRESSED);
		div_button.addActionListener(c);
		point_button.setActionCommand(WinController.DOT_PRESSED);
		point_button.addActionListener(c);
		equals_button.setActionCommand(WinController.EQUALS_PRESSED);
		equals_button.addActionListener(c);
		point_button.setFocusable(false);
		equals_button.setFocusable(false);
		
		p_left.add(sqrt_button);
		p_left.add(pow_button);
		p_left.add(abs_button);
		p_left.add(neg_button);
		sqrt_button.setPreferredSize(BUTTON_SIZE);
		pow_button.setPreferredSize(BUTTON_SIZE);
		abs_button.setPreferredSize(BUTTON_SIZE);
		neg_button.setPreferredSize(BUTTON_SIZE);
		
		sqrt_button.setActionCommand(WinController.SQRT_PRESSED);
		sqrt_button.addActionListener(c);
		pow_button.setActionCommand(WinController.POW_PRESSED);
		pow_button.addActionListener(c);
		abs_button.setActionCommand(WinController.ABS_PRESSED);
		abs_button.addActionListener(c);
		neg_button.setActionCommand(WinController.NEG_PRESSED);
		neg_button.addActionListener(c);
		sqrt_button.setFocusable(false);
		pow_button.setFocusable(false);
		abs_button.setFocusable(false);
		neg_button.setFocusable(false);
		
	}
	
	public void appendTextToArea(String s) {
		resArea.append(s);
	}
	
	public String getTextArea() {
		return resArea.getText();
	}
	
	public void setTextArea(String s) {
		resArea.setText(s);
	}
	
	public int delChar() {
		try {
			if (resArea.getText().length() > 0) {
				resArea.setText(resArea.getText(0, resArea.getText().length()-1));
				return 0;
			}
			else return -1;
				
		} catch (BadLocationException e) {
			return -1;
		}
	}
	
	public void clearTextArea() {
		resArea.setText("");
	}
	
	private void enableDarkTheme(JPanel centerPanel, JPanel eastPanel, JPanel westPanel, JPanel northPanel) {
		
		Color bgMain = new Color(45, 45, 45);
		Color panelDark = new Color(55, 55, 55);
		Color btnDark = new Color(70, 70, 70);
		Color btnOp = new Color(105, 105, 105);
		Color textColor = Color.WHITE;

		this.getContentPane().setBackground(bgMain);
		centerPanel.setBackground(panelDark);
		eastPanel.setBackground(panelDark);
		westPanel.setBackground(panelDark);
		northPanel.setBackground(panelDark);

		resArea.setBackground(new Color(30,30,30));
		resArea.setForeground(Color.WHITE);
		resArea.setCaretColor(Color.BLACK);

		JButton[] allButtons = {
		    point_button, equals_button, div_button, mult_button, sub_button, add_button,
		    del_button, clear_button, pow_button, sqrt_button, abs_button, neg_button};

		for (JButton b : nums) {
			if (b != null) {
		      b.setBackground(btnDark);
		      b.setForeground(textColor);
			}
		}

		for (JButton b : allButtons) {
			if (b != null) {
			    b.setBackground(btnOp);
			    b.setForeground(textColor);
			}
		}

	}
	
	private void buildGUI() {
		
		this.setTitle("Calculator by Fabio - v1.0");
		this.getContentPane().setLayout(new BorderLayout());
		WinController controller = new WinController(this);
		
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JPanel westPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(4, 1));
		centerPanel.setLayout(new GridLayout(4, 3));
		westPanel.setLayout(new GridLayout(4, 1));
		initializeButtons(centerPanel, eastPanel, westPanel, controller);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(eastPanel, BorderLayout.LINE_END);
		this.getContentPane().add(westPanel, BorderLayout.LINE_START);
		
		JPanel northPanel = new JPanel(); // used to display input and results as well as clear and backspace buttons
		northPanel.setLayout(new FlowLayout());
		resArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Font largeFont = new Font(resArea.getFont().getName(), resArea.getFont().getStyle(), 20);
		resArea.setFont(largeFont);
		resArea.setCaretColor(Color.WHITE);
		resArea.setEditable(false);
		JScrollPane scrollBar = new JScrollPane(resArea);
		scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		northPanel.add(scrollBar);
		
		clear_button.setPreferredSize(BUTTON_SIZE);
		del_button.setPreferredSize(BUTTON_SIZE);
		del_button.setActionCommand(WinController.BACKSPACE_PRESSED);
		del_button.addActionListener(controller);
		clear_button.setActionCommand(WinController.CLEAR_PRESSED);
		clear_button.addActionListener(controller);
		clear_button.setFocusable(false);
		del_button.setFocusable(false);
		northPanel.add(clear_button);
		northPanel.add(del_button);
		this.getContentPane().add(northPanel, BorderLayout.PAGE_START);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		
		if (DARK_THEME) {
			enableDarkTheme(centerPanel, eastPanel, westPanel, northPanel);
		}
		
		this.pack();
		return;
		
	}
	
	public MyWindow() {
		
		ImageIcon calculatorImage = new ImageIcon("calculator_icon.jpg");
		this.setIconImage(calculatorImage.getImage());
		
		nums = new JButton[10];
		point_button = new JButton(".");
		equals_button = new JButton("=");
		div_button = new JButton("÷");
		mult_button = new JButton("x");
		sub_button = new JButton("-");
		add_button = new JButton("+");
		del_button = new JButton("⌫");
		clear_button = new JButton("C");
		pow_button = new JButton("^");
		sqrt_button = new JButton("√x");
		abs_button = new JButton("|x|");
		neg_button = new JButton("-x");
		resArea = new JTextArea(2, 15);
		
		this.buildGUI();
		this.setVisible(true);
		
	}

}
