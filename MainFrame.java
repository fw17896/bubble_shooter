package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
	
	private SettingsPanel rightPanel;
	private Canvas leftPanel;
	private boolean uiVisible = true; // Track UI state
	private boolean isGameOnlyMode = false;


	
	// constructor, initiates the components, and sets the properties of the frame
	public MainFrame() {
		setLayout(new BorderLayout());
		
		rightPanel = new SettingsPanel(this);
		rightPanel.initComponents();
		
		leftPanel = new Canvas();
				
		add(leftPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.CENTER);
	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bubble Shooter");	
		setSize(getWidth(), getHeight());
		setResizable(true);
		 pack();
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setVisible(true);
		// setMinimumSize(new Dimension(800, 600)); // adjust as needed

	}
	
	/**
	 * makes the left panel display the highscores
	 */
	public void init(){
		leftPanel.displayHighscore(0, true);
	}
	
	/**
	 * can be called when the game is won, displays the highscores
	 * @param score the achieved score
	 */
	public void gameWon(long score){
		rightPanel.updateScore(score);
		leftPanel.displayHighscore(score, true);
	}
	
	/**
	 * can be called when the game is lost, displays the highscores
	 * @param score the achieved score
	 */
	public void gameLost(long score){
		rightPanel.updateScore(score);
		leftPanel.displayHighscore(score, false);
	}
	
	/**
	 * makes the right panel update the score counter to the current one
	 * @param score the currently achieved score
	 */
	public void updateScore(long score){
		rightPanel.updateScore(score);
	}

	/**
	 * {@inheritDoc}
	 */
// 	@Override
// 	public void actionPerformed(ActionEvent e) {
// 		// if(e.getActionCommand().equals("play")){
// 		// 	leftPanel.play(rightPanel.getRow(), rightPanel.getColor());
// 		// 	leftPanel.getGame().setMainFrame(this);
// 		// }
// 	String cmd = e.getActionCommand();
// 	if ("play".equals(cmd)) {
// 		// Start new game
// 		leftPanel.play(rightPanel.getRow(), rightPanel.getColor());
// 		leftPanel.getGame().setMainFrame(this);

// 		// Toggle settings panel visibility
// 		toggleUI();
// 	}
// }

	// @Override
	// public void actionPerformed(ActionEvent e) {
	// 	if (e.getActionCommand().equals("play")) {
	// 		if (!isGameOnlyMode) {
	// 			// Switch to Game-Only Mode
	// 			isGameOnlyMode = true;

	// 			remove(rightPanel);
	// 			leftPanel.setPreferredSize(null); // Let it expand
	// 			add(leftPanel, BorderLayout.CENTER);

	// 			pack(); // Resize to fit new layout
	// 			setLocationRelativeTo(null); // Center on screen

	// 			leftPanel.play(rightPanel.getRow(), rightPanel.getColor());
	// 			leftPanel.getGame().setMainFrame(this);
	// 		} else {
	// 			// Switch back to Menu + Game Mode
	// 			isGameOnlyMode = false;

	// 			remove(leftPanel);
	// 			remove(rightPanel);

	// 			leftPanel.setPreferredSize(new Dimension(Constants.FIELD_SIZE_X, Constants.FIELD_SIZE_Y));

	// 			add(rightPanel, BorderLayout.CENTER);

	// 			pack();
	// 			setLocationRelativeTo(null);
				
	// 			leftPanel.play(rightPanel.getRow(), rightPanel.getColor());
	// 			leftPanel.getGame().setMainFrame(this);
	// 		}
	// 	}
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("play")) {
			if (!isGameOnlyMode) {
				isGameOnlyMode = true;

				// Clear everything and make Canvas full screen
				getContentPane().removeAll();
				leftPanel.setPreferredSize(null); // Allow expansion
				add(leftPanel, BorderLayout.CENTER);

				revalidate(); // Trigger layout updates
				repaint();    // Redraw UI
				setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize
				setVisible(true); // Force frame re-paint

				leftPanel.newGame(rightPanel.getRow(), rightPanel.getColor());
				leftPanel.getGame().setMainFrame(this);
			} else {
				// Switch back to UI + Game Mode
				isGameOnlyMode = false;

				getContentPane().removeAll(); // Clear everything

				leftPanel.setPreferredSize(new Dimension(Constants.FIELD_SIZE_X, Constants.FIELD_SIZE_Y));
				add(leftPanel, BorderLayout.WEST);
				add(rightPanel, BorderLayout.CENTER);

				revalidate();
				repaint();
				pack();
				setLocationRelativeTo(null);

				leftPanel.newGame(rightPanel.getRow(), rightPanel.getColor());
				leftPanel.getGame().setMainFrame(this);
			}
		}
}



	public void toggleUI() {
		uiVisible = !uiVisible;
		rightPanel.setVisible(uiVisible);
		revalidate(); // Refresh layout
		pack();       // Resize window to fit new layout
}


}











// package bubble_shooter;

// import java.awt.BorderLayout;
// import java.awt.GridBagLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import javax.swing.*;

// public class MainFrame extends JFrame implements ActionListener{
	
// 	private SettingsPanel rightPanel;
// 	private Canvas leftPanel;

// 	private JLabel gameOverLabel;
// 	private JButton playAgainButton;

	
// 	/**
// 	 * constructor, initiates the components, and sets the
// 	 * properties of the frame
// 	 */
// 	public MainFrame() {
// 		JPanel mainPanel = new JPanel();
// 		mainPanel.setLayout(new BorderLayout());
		
// 		rightPanel = new SettingsPanel(this);
// 		rightPanel.initComponents();
		
// 		leftPanel = new Canvas();
// 		leftPanel.setVisible(false);

// 	// Game over label and Play Again button setup
// 		gameOverLabel = new JLabel("");
// 		gameOverLabel.setVisible(false);

// 		playAgainButton = new JButton("Play Again");
// 		playAgainButton.setVisible(false);

		
// 		rightPanel.add(gameOverLabel);
// 		rightPanel.add(playAgainButton);

// 		JPanel centerPanel = new JPanel();                    // ✅ wrapper
// 		centerPanel.setLayout(new GridBagLayout());           // ✅ center alignment
// 		centerPanel.setOpaque(false);                         // ✅ for background image
// 		centerPanel.add(rightPanel);                          // ✅ add your panel

// add(centerPanel, BorderLayout.CENTER);


// 		 setSize(2 * Constants.WINDOW_SIZE_X - getContentPane().getSize().width, 2 * Constants.WINDOW_SIZE_Y - getContentPane().getSize().height);

// 		playAgainButton.addActionListener(e -> {
// 			gameOverLabel.setVisible(false);
// 			playAgainButton.setVisible(false);
// 			leftPanel.setVisible(false);
// 			rightPanel.updateScore(0); 
// 			rightPanel.setInputsEnabled(true);
// 		});

// 		// playAgainButton.addActionListener(e -> {
// 		// 	gameOverLabel.setVisible(false);
// 		// 	playAgainButton.setVisible(false);
// 		// 	leftPanel.setVisible(false);

// 		// 	// Reset settings panel
// 		// 	rightPanel.setVisible(true);
// 		// 	rightPanel.setInputsEnabled(true);
// 		// 	rightPanel.updateScore(0); // Optionally reset score
// 		// });

				
// 		mainPanel.add(leftPanel, BorderLayout.WEST);
// 		add(rightPanel, BorderLayout.CENTER);
	
// 		setDefaultCloseOperation(EXIT_ON_CLOSE);
// 		setTitle("Bubble Shooter");	
// 		setSize(Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y);
// 		setResizable(true);
// 		setVisible(true);
// 		setSize(2*Constants.WINDOW_SIZE_X-getContentPane().getSize().width,
// 				2*Constants.WINDOW_SIZE_Y-getContentPane().getSize().height);
		 
// 	}

// 	/**
// 	 * makes the left panel display the highscores
// 	 */
// 	public void init(){
// 		leftPanel.displayHighscore(0, true);
// 	}
	
// 	/**
// 	 * can be called when the game is won, displays the highscores
// 	 * @param score the achieved score
// 	 */
// 	public void gameWon(long score){
// 		rightPanel.updateScore(score);
// 		leftPanel.displayHighscore(score, true);
// 		showGameOver("You Won!");
// 	}
	
// 	/**
// 	 * can be called when the game is lost, displays the highscores
// 	 * @param score the achieved score
// 	 */
// 	public void gameLost(long score){
// 		rightPanel.updateScore(score);
// 		leftPanel.displayHighscore(score, false);
// 		showGameOver("Game Over!");
// 	}

// 	private void showGameOver(String message) {
// 		gameOverLabel.setText(message);
// 		gameOverLabel.setVisible(true);
// 		playAgainButton.setVisible(true);
// 	}
	
	
// 	/**
// 	 * makes the right panel update the score counter to the current one
// 	 * @param score the currently achieved score
// 	 */
// 	public void updateScore(long score){
// 		rightPanel.updateScore(score);
// 	}


// 		@Override
// 	public void actionPerformed(ActionEvent e) {
// 		if (e.getActionCommand().equals("play")) {
// 			leftPanel.play(rightPanel.getRow(), rightPanel.getColor());
// 			leftPanel.getGame().setMainFrame(this);
// 			startGame();
// 		}
// 	}

// 	// public void startGame() {
// 	// 	leftPanel.setVisible(true);
// 	// 	rightPanel.setInputsEnabled(false);
// 	// }

// 	// public void startGame() {
// 	// 	// Hide right panel controls
// 	// 	rightPanel.setInputsEnabled(false);
// 	// 	rightPanel.setVisible(false);

// 	// 	// Start Game
// 	// 	leftPanel.setVisible(true);
// 	// 	leftPanel.requestFocusInWindow(); // To capture key events if needed
// 	// }

// // 	public void startGame() {
// // 		// Remove the settings panel from the frame
// // 		remove(rightPanel);

// // 		// Enable and show game panel
// // 		leftPanel.setVisible(true);
// // 		leftPanel.requestFocusInWindow();

// // 		// Revalidate and resize the window
// // 		revalidate(); // re-layout the frame
// // 		repaint();    // repaint frame
// // 		pack();       // resize window based on new component sizes
// // 		setExtendedState(JFrame.MAXIMIZED_BOTH); // optional: make full screen
// // }

// 	public void startGame() {
// 		// ✅ Remove settings panel to avoid duplicate controls
// 		remove(rightPanel);

// 		// Enable and show game panel
// 		leftPanel.setVisible(true);
// 		leftPanel.requestFocusInWindow();

// 		revalidate();
// 		repaint();
// 		pack();
// 		setExtendedState(JFrame.MAXIMIZED_BOTH);
// 	}




// }


