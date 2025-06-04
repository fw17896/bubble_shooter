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
	private JButton playButton;
	private JButton stopGameButton;
	private Image backgroundImg;
	
	// constructor, initiates the panel and sets the parameter as its mainframe
	public SettingsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// ----------------image-------------
		backgroundImg = new ImageIcon("bdg.jpg").getImage();

   		setOpaque(false);
    }
    
	
	//initiates the components of the panel and sets the look and the actionlisteners
	 
	public void initComponents(){
 

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
		playButton = new JButton("PLAY");
		playButton.setActionCommand("play");
		playButton.addActionListener(mainFrame);
		buttonPanel.setOpaque(false);
		
		// stopGameButton = new JButton("Stop Game");
		// stopGameButton.setActionCommand("STOPGAME");
		// stopGameButton.addActionListener(mainFrame);
		buttonPanel.add(playButton);
		// buttonPanel.add(stopGameButton);

		//Button Style
		styleButton(playButton, new Color(70, 130, 180)); // Steel blue
        // styleButton(stopGameButton, new Color(220, 20, 60)); // Crimson
		

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
		// scoreLabel.setText((new Long(score).toString()));
	}
	
	// returns the selected number of rows
	public int getRow(){
		return (int) rowsSpinner.getValue();
	}
	
	//returns the selected number of colors
	public int getColor(){
		return (int) colorSpinner.getValue();
	}

	// image
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
}
	
}