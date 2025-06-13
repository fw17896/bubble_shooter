package bubble_shooter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//   a class to display the panel of settings and scores on the right and handle the settings of the game
public class SettingsPanel extends JPanel {
	
	private final MainFrame mainFrame;
	private JLabel scoreLabel;
	private JPanel lowerPanel;
	private JButton startButton;
	private JButton settingButton;
    private JButton highscoreButton;
    private JButton exitButton;
	private final Image backgroundImg;
	private int selectedRows = 5;
    private int selectedColors = 4;
    private Canvas canvas;
	
	// constructor, initiates the panel and sets the parameter as its mainframe
	public SettingsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		backgroundImg = new ImageIcon("C:\\Users\\LENOVO\\Desktop\\STUDY UNI\\Sems3\\OOP\\OOP LAB\\Final Project\\bubble_shooter\\bubble_shooter\\bdg.jpg").getImage();
    }


	//initiates the components of the panel and sets the look and the actionlisteners
	 
	public void initComponents(){
		//Title Label
		JLabel titleLabel = new JLabel("Bubble Shooter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(100));
		add(titleLabel);
        add(Box.createVerticalStrut(50));
////////
		scoreLabel = new JLabel();
//////
		 // Lower Panel
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setOpaque(false);
	
		// Buttons
		startButton = new JButton("Start Game");
		settingButton = new JButton("Settings");
        highscoreButton = new JButton("High Scores");
        exitButton = new JButton("Exit");

		startButton.setActionCommand("start");
		startButton.addActionListener(mainFrame);
        exitButton.addActionListener(e -> System.exit(0));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		lowerPanel.add(startButton);
        lowerPanel.add(Box.createVerticalStrut(20));
		lowerPanel.add(settingButton);
        lowerPanel.add(Box.createVerticalStrut(20));
        lowerPanel.add(highscoreButton);
        lowerPanel.add(Box.createVerticalStrut(20));
        lowerPanel.add(exitButton);
        lowerPanel.add(Box.createVerticalStrut(20));

		//Button Style
		styleButton(startButton, new Color(255, 140, 0));
        styleButton(settingButton, new Color(255, 140, 0));
        styleButton(highscoreButton, new Color(255, 140, 0));
        styleButton(exitButton, new Color(255, 140, 0));

		settingButton.addActionListener(e -> openSettingsDialog());

        highscoreButton.addActionListener(e -> {
        // Create and display the highscore window
        JFrame hsFrame = new JFrame("High Scores");
        hsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hsFrame.add(new HighscoreDisplayPanel());
        hsFrame.setSize(500, 400);
        hsFrame.setLocationRelativeTo(null); // Center the window
        hsFrame.setVisible(true);
    });


		add(lowerPanel);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

 	//Button Style
	public void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
		button.setSize(180,40);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(background.darker(), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        Dimension buttonSize = new Dimension(200, 45);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(background.brighter());
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(background);
            }
        });
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

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
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
