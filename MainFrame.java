package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

// Main application frame for the Bubble Shooter game
public class MainFrame extends JFrame implements ActionListener{
	
	private SettingsPanel mainPanel;
	private Canvas canvas;
	private Clip backgroundMusic;
	private JFrame gameWindow;
	
	// Constructor, initializes main frame, panel, and starts background music
	public MainFrame() {
		setLayout(new BorderLayout());
		mainPanel = new SettingsPanel(this);
		mainPanel.initComponents();
		add(mainPanel, BorderLayout.CENTER);

		playBackgroundMusic("background.wav");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bubble Shooter");	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

	}

	 // Creates and returns a custom menu bar
	private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
		JMenuItem mainMenuItem = new JMenuItem("Main Menu");
        JMenuItem restartItem = new JMenuItem("Restart");
		JMenuItem highscoresItem = new JMenuItem("Highscores");
        JMenuItem exitItem = new JMenuItem("Exit");
		
		mainMenuItem.addActionListener(e -> {
			if (gameWindow != null) {
				gameWindow.dispose();
			}
			showMainMenu();
		});

        restartItem.addActionListener(e -> {
			if (gameWindow != null) {
				gameWindow.dispose();
			}

			gameWindow = new JFrame("Bubble Shooter - Game");
			gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

			canvas = new Canvas();
			canvas.setMainFrame(this);
			canvas.newGame(mainPanel.getRow(), mainPanel.getColor());
			canvas.getGame().setMainFrame(this);

			gameWindow.setJMenuBar(createMenuBar());
			gameWindow.add(canvas);
			gameWindow.setVisible(true);
		});

		highscoresItem.addActionListener(e -> {
            if (canvas != null) {
                canvas.displayHighscore(0, false);
            } else {
            }
        });

        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(restartItem);
		gameMenu.addSeparator();
		gameMenu.add(mainMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(highscoresItem);
		gameMenu.addSeparator();
        gameMenu.add(exitItem);

        JMenu soundMenu = new JMenu("Sound");
        JMenuItem playMusicItem = new JMenuItem("Play Background Music");
        JMenuItem stopMusicItem = new JMenuItem("Stop Music");

        playMusicItem.addActionListener(e -> playBackgroundMusic("background.wav")); 
        stopMusicItem.addActionListener(e -> stopBackgroundMusic());

        soundMenu.add(playMusicItem);
        soundMenu.add(stopMusicItem);

		menuBar.setBackground(new Color(255, 140, 0));

		for (MenuElement menuElement : menuBar.getSubElements()) {
			JMenu menu = (JMenu) menuElement.getComponent();
			menu.setForeground(Color.WHITE);
			menu.setFont(new Font("Arial", Font.BOLD, 16));
		}

        menuBar.add(gameMenu);
        menuBar.add(soundMenu);

        return menuBar;
    }
	
	 // Displays highscores when game starts
	public void init(){
		if (canvas != null) {
            canvas.displayHighscore(0, true);
        }
	}
	
	// Displays highscores when game is won and updates score
	public void gameWon(long score){
		mainPanel.updateScore(score);
        if (canvas != null) {
            canvas.displayHighscore(score, true);
        }
	}
	
	// Displays highscores when game is lost and updates score
	public void gameLost(long score){
		mainPanel.updateScore(score);
        if (canvas != null) { 
            canvas.displayHighscore(score, false);
        }
	}
	
	// Updates the current score display
	public void updateScore(long score){
		mainPanel.updateScore(score);
	}

	// Handles start button press to launch the game window and initialize game
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
				stopBackgroundMusic();
				gameWindow = new JFrame("Bubble Shooter - Game");
				gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

				canvas = new Canvas();
				canvas.setMainFrame(this);
				canvas.newGame(mainPanel.getRow(), mainPanel.getColor());
				canvas.getGame().setMainFrame(this);

				gameWindow.setJMenuBar(createMenuBar());
				gameWindow.add(canvas);
				gameWindow.setVisible(true);
			}
		}

	 // Shows the main menu panel with background music
	public void showMainMenu() {
		getContentPane().removeAll();
		playBackgroundMusic("background.wav");	
		mainPanel = new SettingsPanel(this);
		mainPanel.initComponents();
		add(mainPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	// Returns the main settings panel
	public SettingsPanel getMainPanel() {
		return mainPanel;
	}

	// Plays background music
	public void playBackgroundMusic(String filepath) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(audioStream);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Stops the background music 
	public void stopBackgroundMusic() {
		if (backgroundMusic != null && backgroundMusic.isRunning()) {
			backgroundMusic.stop();
			backgroundMusic.close();
		}
	}

}

