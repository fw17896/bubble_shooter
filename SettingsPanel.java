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
		backgroundImg = new ImageIcon("abc.png").getImage();
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










//-----------------original-------------------


// // package bubble_shooter;
// import java.awt.*;
// import javax.swing.*;

// /**
//  * a class to display the panel of settings and scores on the right
//  * and handle the settings of the game
//  * @author Barni
//  *
//  */
// public class SettingsPanel extends JPanel {
	
// 	private MainFrame mainFrame;
// 	private JLabel scoreLabel;
// 	private JPanel lowerPanel;
// 	private JSpinner rowsSpinner;
// 	private JSpinner colorSpinner;
// 	private JButton playButton;
// 	private JButton stopGameButton;
	
// 	/**
// 	 * constructor, initiates the panel and sets the parameter
// 	 * as its mainframe
// 	 * @param m mainframe to be set
// 	 */

//     public SettingsPanel(MainFrame mainFrame) {
//         this.mainFrame = mainFrame;
//         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
// 		setBorder(BorderFactory.createTitledBorder("Game Settings"));
//         initComponents();
//     }
	
// 	/**
// 	 * initiates the components of the panel and sets the look
// 	 * and the actionlisteners
// 	 */
// 	public void initComponents(){
// 		scoreLabel = new JLabel("0", SwingConstants.RIGHT);
// 		scoreLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
// 		scoreLabel.setPreferredSize(new Dimension(Constants.WINDOW_SIZE_X-Constants.FIELD_SIZE_X-5,50));
// 		scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.PLAIN, 34));
		
// 		lowerPanel = new JPanel();
// 		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		
// 		JPanel rowsPanel = new JPanel();
// 		rowsPanel.setPreferredSize(new Dimension(Constants.WINDOW_SIZE_X-Constants.FIELD_SIZE_X-5,50));
// 		rowsPanel.setLayout(new BorderLayout());
// 		SpinnerModel rowsModell = new SpinnerNumberModel(7, 3, 15, 1);
// 		rowsSpinner = new JSpinner(rowsModell);
// 		JLabel rowsLabel = new JLabel("Initial rows");
// 		rowsLabel.setFont(new Font(rowsLabel.getFont().getName(), Font.PLAIN, 14));
// 		rowsLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
// 		rowsPanel.add(rowsLabel, BorderLayout.WEST);
// 		rowsPanel.add(rowsSpinner, BorderLayout.EAST);
// 		rowsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		
// 		JPanel colorPanel = new JPanel();
// 		colorPanel.setPreferredSize(new Dimension(Constants.WINDOW_SIZE_X-Constants.FIELD_SIZE_X-5,50));
// 		colorPanel.setLayout(new BorderLayout());
// 		SpinnerModel colorModell = new SpinnerNumberModel(4, 3, 8, 1);
// 		colorSpinner = new JSpinner(colorModell);
// 		JLabel colorLabel = new JLabel("Initial colors");
// 		colorLabel.setFont(new Font(colorLabel.getFont().getName(), Font.PLAIN, 14));
// 		colorLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
// 		colorPanel.add(colorLabel, BorderLayout.WEST);
// 		colorPanel.add(colorSpinner, BorderLayout.EAST);
// 		colorPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		
// 		JPanel buttonPanel = new JPanel();
// 		playButton = new JButton("New game");
// 		playButton.setActionCommand("play");
// 		playButton.addActionListener(mainFrame);
		
// 		stopGameButton = new JButton("Stop game");
// 		stopGameButton.setActionCommand("STOPGAME");
// 		stopGameButton.addActionListener(mainFrame);
// 		buttonPanel.add(playButton);
// 		buttonPanel.add(stopGameButton);
		
// 		lowerPanel.add(rowsPanel);
// 		lowerPanel.add(colorPanel);
// 		lowerPanel.add(buttonPanel);
// 		JPanel spaceholder = new JPanel();
// 		spaceholder.setPreferredSize(new Dimension(Constants.WINDOW_SIZE_X-Constants.FIELD_SIZE_X-5,340));
// 		lowerPanel.add(spaceholder);
// 		add(scoreLabel, BorderLayout.NORTH);
// 		add(lowerPanel, BorderLayout.CENTER);

// 		// add(new JLabel("Rows:"));
//         // rowsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
//         // add(rowsSpinner);

//         // add(new JLabel("Colors:"));
//         // colorSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
//         // add(colorSpinner);

//         // playButton = new JButton("New Game");
//         // playButton.setActionCommand("play");
//         // playButton.addActionListener(mainFrame);
//         // add(playButton);

//         // scoreLabel = new JLabel("Score: 0");
//         // scoreLabel.setVisible(false);  // Hide initially
//         // add(scoreLabel);

// 		// // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

// 		// // JLabel rowsLabel = new JLabel("Rows:");
// 		// // rowsLabel.setAlignmentX(CENTER_ALIGNMENT);
// 		// // add(rowsLabel);

// 		// // rowsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
// 		// // rowsSpinner.setMaximumSize(new Dimension(100, 30)); // ✅ fixed size
// 		// // rowsSpinner.setAlignmentX(CENTER_ALIGNMENT);
// 		// // add(rowsSpinner);

// 		// // JLabel colorsLabel = new JLabel("Colors:");
// 		// // colorsLabel.setAlignmentX(CENTER_ALIGNMENT);
// 		// // add(colorsLabel);

// 		// // colorSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
// 		// // colorSpinner.setMaximumSize(new Dimension(100, 30)); // ✅ fixed size
// 		// // colorSpinner.setAlignmentX(CENTER_ALIGNMENT);
// 		// // add(colorSpinner);

// 		// // playButton = new JButton("New Game");
// 		// // playButton.setMaximumSize(new Dimension(120, 40)); // ✅ fixed size
// 		// // playButton.setAlignmentX(CENTER_ALIGNMENT);
// 		// // playButton.setActionCommand("play");
// 		// // playButton.addActionListener(mainFrame);
// 		// // add(playButton);

// 		// // scoreLabel = new JLabel("Score: 0");
// 		// // scoreLabel.setVisible(false);
// 		// // scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
// 		// // add(scoreLabel);


// 	}
	
// 	/**
// 	 * updates the score in the score counter field
// 	 * @param score the score to be set
// 	 */

// 	public void updateScore(long score) {
//         scoreLabel.setText("Score: " + score);
//         scoreLabel.setVisible(true);
//     }
	
// 	/**
// 	 * returns the selected number of rows
// 	 * @return the selected number of rows on the spinner
// 	 */
// 	public int getRow(){
// 		return (int) rowsSpinner.getValue();
// 	}
	
// 	/**
// 	 * returns the selected number of colors
// 	 * @return the selected number of colors on the spinner
// 	 */
// 	public int getColor(){
// 		return (int) colorSpinner.getValue();
// 	}
	

// 	public void setInputsEnabled(boolean enabled) {
// 		rowsSpinner.setEnabled(enabled);
// 		colorSpinner.setEnabled(enabled);
// 		playButton.setEnabled(enabled);

// }

// }












