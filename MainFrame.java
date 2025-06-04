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
		revalidate(); 
		pack();       
}


}