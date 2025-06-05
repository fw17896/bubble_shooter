package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
	
	private SettingsPanel mainPanel;
	private Canvas leftPanel;
	private boolean uiVisible = true; // Track UI state
	private boolean isGameOnlyMode = false;

	
	// constructor, initiates the components, and sets the properties of the frame
	public MainFrame() {
		setLayout(new BorderLayout());
		mainPanel = new SettingsPanel(this);
		mainPanel.initComponents();
		add(mainPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bubble Shooter");	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

	}
	
	// makes the left panel display the highscores
	public void init(){
		leftPanel.displayHighscore(0, true);
	}
	
	// can be called when the game is won, displays the highscores
	public void gameWon(long score){
		mainPanel.updateScore(score);
		leftPanel.displayHighscore(score, true);
	}
	
	//can be called when the game is lost, displays the highscores
	public void gameLost(long score){
		mainPanel.updateScore(score);
		leftPanel.displayHighscore(score, false);
	}
	
	//makes the right panel update the score counter to the current one
	public void updateScore(long score){
		mainPanel.updateScore(score);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			if (!isGameOnlyMode) {
				isGameOnlyMode = true;
				getContentPane().removeAll();
				leftPanel = new Canvas(); 
				leftPanel.setPreferredSize(null);
				add(leftPanel, BorderLayout.CENTER);
				revalidate(); 
				repaint(); 
				setExtendedState(JFrame.MAXIMIZED_BOTH);
				setVisible(true);
				leftPanel.newGame(mainPanel.getRow(), mainPanel.getColor());
				leftPanel.getGame().setMainFrame(this);
			} 
		}
	}


	public void toggleUI() {
		uiVisible = !uiVisible;
		mainPanel.setVisible(uiVisible);
		revalidate();
		pack();     
	}
}

