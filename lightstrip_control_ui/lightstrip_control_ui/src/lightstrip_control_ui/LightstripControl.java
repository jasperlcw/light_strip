package lightstrip_control_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import javax.swing.JColorChooser;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LightstripControl {
	
	private JFrame frmLightstripController;
	private JColorChooser colorChooser;
	private JButton applyBtn, onBtn, offBtn;
	private JTextField textField;
	private JSlider brightnessSlider;
	
	private Color currentColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LightstripControl window = new LightstripControl();
					window.frmLightstripController.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LightstripControl() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLightstripController = new JFrame();
		frmLightstripController.setTitle("Lightstrip Controller");
		frmLightstripController.setBounds(100, 100, 770, 466);
		frmLightstripController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLightstripController.getContentPane().setLayout(null);
		
		colorChooser = new JColorChooser();
		colorChooser.setBounds(0, -14, 645, 345);
		AbstractColorChooserPanel[] colorPanels = colorChooser.getChooserPanels();
		for (AbstractColorChooserPanel toRemove : colorPanels) {
			if (!toRemove.getDisplayName().contentEquals("RGB")) {
				colorChooser.removeChooserPanel(toRemove);
			}
		}
		frmLightstripController.getContentPane().add(colorChooser);
		
//		JButton calibrateButton = new JButton("Calibrate");
//		calibrateButton.setBounds(655, 30, 89, 23);
//		calibrateButton.addActionListener(new CalibrateListener());
//		frmLightstripController.getContentPane().add(calibrateButton);
		
		brightnessSlider = new JSlider();
		brightnessSlider.setBounds(6, 350, 306, 14);
		frmLightstripController.getContentPane().add(brightnessSlider);
		
		JLabel lblBrightness = new JLabel("Brightness");
		lblBrightness.setBounds(8, 330, 57, 14);
		frmLightstripController.getContentPane().add(lblBrightness);
		
		textField = new JTextField();
		textField.setBounds(316, 344, 44, 20);
		frmLightstripController.getContentPane().add(textField);
		textField.setColumns(10);
		
		applyBtn = new JButton("Apply");
		applyBtn.setBounds(655, 23, 89, 23);
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = getSelectedColor();
				ButtonMethods.changeColor(currentColor);
			}
		});
		frmLightstripController.getContentPane().add(applyBtn);
		
		onBtn = new JButton("Turn On");
		onBtn.setBounds(655, 57, 89, 23);
		onBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonMethods.turnOnLeds(currentColor);
			}
		});
		frmLightstripController.getContentPane().add(onBtn);
		
		offBtn = new JButton("Turn Off");
		offBtn.setBounds(655, 91, 89, 23);
		offBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonMethods.turnOffLeds(currentColor);
			}
		});
		frmLightstripController.getContentPane().add(offBtn);
	}
	
	private Color getSelectedColor() {
		return colorChooser.getColor();
	}

}
