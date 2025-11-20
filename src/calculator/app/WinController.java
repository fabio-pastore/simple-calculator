package calculator.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import calculator.gui.MyWindow;

public class WinController implements ActionListener {
	
	private final MyWindow win;
	
	private boolean number_inserted; // flag used to check if a number has been input in order to be able to apply operators
	private boolean dot_used; // keeps track of decimal point utilisation
	
	private static enum Operator {ADD, SUB, MULT, DIV, POW, DEF};
	private Operator status;
	
	public static final String NUM_PRESSED = "NUM_PRESSED";
	public static final String PLUS_PRESSED = "PLUS_PRESSED";
	public static final String MINUS_PRESSED = "MINUS_PRESSED";
	public static final String TIMES_PRESSED = "TIMES_PRESSED";
	public static final String SQRT_PRESSED = "SQRT_PRESSED";
	public static final String POW_PRESSED = "POW_PRESSED";
	public static final String ABS_PRESSED = "ABS_PRESSED";
	public static final String NEG_PRESSED = "NEG_PRESSED";
	public static final String DIV_PRESSED = "DIV_PRESSED";
	public static final String DOT_PRESSED = "DOT_PRESSED";
	public static final String BACKSPACE_PRESSED = "BACKSPACE_PRESSED";
	public static final String CLEAR_PRESSED = "CLEAR_PRESSED";
	public static final String EQUALS_PRESSED = "EQUALS_PRESSED";
	
	
	public WinController(MyWindow win) {
		this.win = win;
		this.status = WinController.Operator.DEF;
		this.number_inserted = false;
		this.dot_used = false;
	}
	
	private void resetOperationFlags() { // called after each successful operation, that is, successful "equals" input
		this.number_inserted = true;
		this.status = WinController.Operator.DEF;
		this.dot_used = true;
	}
	
	private void clearFlags() { // completely reset flags to default values
		this.number_inserted = false;
		this.status = WinController.Operator.DEF;
		this.dot_used = false;
	}
	
	private void calculateAddition() {
		double a, b;
		String input = this.win.getTextArea();
		String[] split_in = input.split("\\+");
		a = Double.parseDouble(split_in[0]);
		if (split_in.length == 1) {
			this.win.setTextArea(Double.toString(a));
			resetOperationFlags();
			return;
		}
		b = Double.parseDouble(split_in[1]);
		this.win.setTextArea(Double.toString(a + b));
		resetOperationFlags();
	}
	
	private void calculateSubtraction() {
		double a, b;
		boolean is_a_neg = false;
		String input = this.win.getTextArea();
		if (input.charAt(0) == '-') {
			is_a_neg = true;
			input = input.substring(1);
		}
		String[] split_in = input.split("-");
		a = Double.parseDouble(split_in[0]);
		if (is_a_neg) a = -a;
		if (split_in.length == 1) {
			this.win.setTextArea(Double.toString(a));
			resetOperationFlags();
			return;
		}
		b = Double.parseDouble(split_in[1]);
		this.win.setTextArea(Double.toString(a - b));
		resetOperationFlags();
	}
	
	private void calculateMultiplication() {
		double a, b;
		String input = this.win.getTextArea();
		String[] split_in = input.split("x");
		a = Double.parseDouble(split_in[0]);
		if (split_in.length == 1) {
			this.win.setTextArea(Double.toString(a));
			resetOperationFlags();
			return;
		}
		b = Double.parseDouble(split_in[1]);
		this.win.setTextArea(Double.toString(a * b));
		resetOperationFlags();
	}
	
	private void calculateDivision() {
		double a, b;
		String input = this.win.getTextArea();
		String[] split_in = input.split("÷");
		a = Double.parseDouble(split_in[0]);
		if (split_in.length == 1) {
			this.win.setTextArea(Double.toString(a));
			resetOperationFlags();
			return;
		}
		b = Double.parseDouble(split_in[1]);
		if (b == 0) {
			JOptionPane.showInternalMessageDialog(this.win.getParent(), "CANNOT DIVIDE BY ZERO!", "[ERROR]", JOptionPane.ERROR_MESSAGE, null);
			this.actionPerformed(new ActionEvent(this, 0, WinController.CLEAR_PRESSED));
			return;
		}
		this.win.setTextArea(Double.toString(a / b));
		resetOperationFlags();
	}
	
	private void calculatePower() {
		double a, b;
		String input = this.win.getTextArea();
		String[] split_in = input.split("\\^");
		a = Double.parseDouble(split_in[0]);
		if (split_in.length == 1) {
			this.win.setTextArea(Double.toString(a));
			resetOperationFlags();
			return;
		}
		b = Double.parseDouble(split_in[1]);
		this.win.setTextArea(Double.toString(Math.pow(a, b)));
		resetOperationFlags();
	}
	
	private void calculateSqrt() {
		double a;
		a = Double.parseDouble(this.win.getTextArea());
		if (a < 0) {
			JOptionPane.showInternalMessageDialog(this.win.getParent(), "CANNOT TAKE THE SQUARE ROOT OF A NEGATIVE NUMBER!", "[ERROR]", JOptionPane.ERROR_MESSAGE, null);
			this.actionPerformed(new ActionEvent(this, 0, WinController.CLEAR_PRESSED));
			return;
		}
		this.win.setTextArea(Double.toString(Math.sqrt(a)));
		resetOperationFlags();
	}
	
	private void calculateAbs() {
		double a;
		a = Double.parseDouble(this.win.getTextArea());
		if (a >= 0) {
			resetOperationFlags();
			return;
		}
		a = -a;
		this.win.setTextArea(Double.toString(a));
		resetOperationFlags();
	}
	
	private void calculateNeg() {
		double a;
		a = Double.parseDouble(this.win.getTextArea());
		a = -a;
		this.win.setTextArea(Double.toString(a));
		resetOperationFlags();
	}
	
	private void setOperator(Operator op, String sym) {
		if (this.status.equals(WinController.Operator.DEF) && this.number_inserted) {
			win.appendTextToArea(sym);
			this.status = op;
			this.dot_used = false;
			this.number_inserted = false;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			
			case WinController.NUM_PRESSED:
				win.appendTextToArea(((JButton)e.getSource()).getText());
				this.number_inserted = true;
				break;
				
			case WinController.BACKSPACE_PRESSED:
				String prev_input = this.win.getTextArea();
				if (this.win.delChar() == -1) return; // returns -1 if input is already empty
				int last_pos = prev_input.length() - 1;
				char deleted = prev_input.charAt(last_pos);
				String rem_input = prev_input.substring(0, last_pos);
				if (rem_input.isEmpty()) {
					clearFlags();
					return;
				}
				
				else if (deleted == '+' || deleted == '-' || deleted == 'x' || deleted == '÷' || deleted == '^') {
					this.number_inserted = true;
					this.status = WinController.Operator.DEF;
				}
				
				else if (deleted == '.') {
					this.dot_used = false;
				}
				
				if (last_pos - 1 >= 0)
					if (prev_input.charAt(last_pos - 1) == '+' || prev_input.charAt(last_pos - 1) == '-'
					|| prev_input.charAt(last_pos - 1) == 'x' || prev_input.charAt(last_pos - 1) == '÷' ||
					   prev_input.charAt(last_pos - 1) == '^') {
						this.number_inserted = false;
					}
				
				break;
				
			case WinController.CLEAR_PRESSED:
				this.win.clearTextArea();
				clearFlags();
				break;
				
			case WinController.PLUS_PRESSED:
				this.setOperator(WinController.Operator.ADD, "+");
				break;
			
			case WinController.MINUS_PRESSED:
				this.setOperator(WinController.Operator.SUB, "-");
				break;
					
			case WinController.TIMES_PRESSED:
				this.setOperator(WinController.Operator.MULT, "x");
				break;
				
			case WinController.DIV_PRESSED:
				this.setOperator(WinController.Operator.DIV, "÷");
				break;
				
			case WinController.POW_PRESSED:
				this.setOperator(WinController.Operator.POW, "^");
				break;
				
			case WinController.DOT_PRESSED:
				if (!dot_used && number_inserted) {
					win.appendTextToArea(".");
					this.dot_used = true;
				}
				break;
				
			case WinController.SQRT_PRESSED:
				if (this.status.equals(WinController.Operator.DEF) && number_inserted) {
					calculateSqrt();
				}
				break;
				
			case WinController.ABS_PRESSED:
				if (this.status.equals(WinController.Operator.DEF) && number_inserted) {
					calculateAbs();
				}
				break;
				
			case WinController.NEG_PRESSED:
				if (this.status.equals(WinController.Operator.DEF) && number_inserted) {
					calculateNeg();
				}
				break;
				
			case WinController.EQUALS_PRESSED:
				
				switch (this.status) {
				
				case WinController.Operator.ADD:
					calculateAddition();
					break;
					
				case WinController.Operator.SUB:
					calculateSubtraction();
					break;
					
				case WinController.Operator.MULT:
					calculateMultiplication();
					break;
					
				case WinController.Operator.DIV:
					calculateDivision();
					break;
					
				case WinController.Operator.POW:
					calculatePower();
					break;
					
				default:
					return; // no operation selected, we simply ignore the event
				
				}
				
				break;
				
			default:
				return;
				
		}
		
	}

}
