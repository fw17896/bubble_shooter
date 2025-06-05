package bubble_shooter;

import java.awt.*;
import javax.swing.*;

//   a class to display the panel of settings and scores on the right and handle the settings of the game
public class SettingsPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JLabel scoreLabel;
	private JPanel lowerPanel;
	private JSpinner rowsSpinner;
	private JSpinner colorSpinner;
	private JButton startButton;
	private JButton settingButton;
	private Image backgroundImg;
	private int selectedRows = 5;
    private int selectedColors = 4;
	
	// constructor, initiates the panel and sets the parameter as its mainframe
	public SettingsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		backgroundImg = new ImageIcon("C:\\Users\\FATIMA WASEEM\\OneDrive - Higher Education Commission\\Desktop\\bubble_shooter-master1\\bubble_shooter\\bdg.jpg").getImage();
    }
    
	
	//initiates the components of the panel and sets the look and the actionlisteners
	 
	public void initComponents(){
		//Title Label
		JLabel titleLabel = new JLabel("Bubble Shooter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(titleLabel);

		//Score Label
		scoreLabel = new JLabel("0", SwingConstants.RIGHT);
		scoreLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.white);
        add(scoreLabel, BorderLayout.SOUTH);

		 // Lower Panel
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setOpaque(false);

		// Rows Spinner
        lowerPanel.add(createLabeledSpinner("Initial rows", 5, 3, 10));
        rowsSpinner = (JSpinner) ((JPanel) lowerPanel.getComponent(lowerPanel.getComponentCount() - 1)).getComponent(1);
	
		// Color Spinner
        lowerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lowerPanel.add(createLabeledSpinner("Initial colors", 4, 3, 8));
        colorSpinner = (JSpinner) ((JPanel) lowerPanel.getComponent(lowerPanel.getComponentCount() - 1)).getComponent(1);
		((JSpinner.DefaultEditor) colorSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
	
		// Button Panel
		JPanel buttonPanel = new JPanel();
		startButton = new JButton("Start Game");
		settingButton = new JButton("Settings");

		startButton.setActionCommand("start");
		startButton.addActionListener(mainFrame);
		buttonPanel.setOpaque(false);

		buttonPanel.add(startButton);
		buttonPanel.add(settingButton);

		//Button Style
		styleButton(startButton, new Color(255, 140, 0)); // Steel blue
        styleButton(settingButton, new Color(255, 140, 0));
		
		settingButton.addActionListener(e -> openSettingsDialog());

		lowerPanel.add(buttonPanel);
		add(lowerPanel, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	//Button Style
	public void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
		button.setSize(180,40);
    }

	public void openSettingsDialog() {
        JDialog settingsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Game Settings", true);
        settingsDialog.setSize(320, 220);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel messageLabel = new JLabel("Enter initial settings:");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        settingsDialog.add(messageLabel, gbc);

        // Color Spinner
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        settingsDialog.add(new JLabel("Colors:"), gbc);
        SpinnerNumberModel colorModel = new SpinnerNumberModel(selectedColors, 3, 8, 1);
        JSpinner colorSpinner = new JSpinner(colorModel);
		colorSpinner.setFont(new Font("Arial", Font.BOLD, 16));
        colorSpinner.setBackground(Color.WHITE);
        gbc.gridx = 1;
        settingsDialog.add(colorSpinner, gbc);

        // // Row Spinner
        gbc.gridx = 0; gbc.gridy = 2;
        settingsDialog.add(new JLabel("Rows:"), gbc);
        SpinnerNumberModel rowModel = new SpinnerNumberModel(selectedRows, 3, 20, 1);
        JSpinner rowSpinner = new JSpinner(rowModel);
		rowSpinner.setFont(new Font("Arial", Font.BOLD, 16));
        rowSpinner.setBackground(Color.WHITE);
        gbc.gridx = 1;
        settingsDialog.add(rowSpinner, gbc);

        // OK Button
        JButton okButton = new JButton("OK");
		okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setFocusPainted(false);
		okButton.setSize(180,40);
		okButton.setBackground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        settingsDialog.add(okButton, gbc);

        okButton.addActionListener(e -> {
            selectedColors = (int) colorSpinner.getValue();
            selectedRows = (int) rowSpinner.getValue();
            settingsDialog.dispose();
        });

        settingsDialog.setVisible(true);
    }

	public int getRow() {
        return selectedRows;
    }

    public int getColor() {
        return selectedColors;
    }

	public JPanel createLabeledSpinner(String labelText, int initial, int min, int max) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.white);

        SpinnerModel model = new SpinnerNumberModel(initial, min, max, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Arial", Font.BOLD, 16));
        spinner.setBackground(Color.WHITE);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);

        panel.add(label, BorderLayout.WEST);
        panel.add(spinner, BorderLayout.EAST);
        return panel;
    }


	
	//updates the score in the score counter field
	public void updateScore(long score){
		scoreLabel.setText(String.valueOf(score));
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
}
	
}

