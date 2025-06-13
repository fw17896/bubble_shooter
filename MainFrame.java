package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
	
	private SettingsPanel mainPanel;
	private Canvas canvas;
	private Clip backgroundMusic;
	private JFrame gameWindow;
	
	// constructor, initiates the components, and sets the properties of the frame
	public MainFrame() {
		setLayout(new BorderLayout());
		mainPanel = new SettingsPanel(this);
		mainPanel.initComponents();
		add(mainPanel, BorderLayout.CENTER);
		playBackgroundMusic("C:\\Users\\FATIMA WASEEM\\OneDrive - Higher Education Commission\\Desktop\\bubble_shooter-master1\\bubble_shooter\\background.wav"); // use your path
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Bubble Shooter");	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

	}

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

			// Re-initialize everything like in actionPerformed()
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

        playMusicItem.addActionListener(e -> playBackgroundMusic("C:\\Users\\FATIMA WASEEM\\OneDrive - Higher Education Commission\\Desktop\\bubble_shooter-master1\\bubble_shooter\\background.wav")); 
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
	
	// makes the left panel display the highscores
	public void init(){
		if (canvas != null) {
            canvas.displayHighscore(0, true);
        }
	}
	
	// can be called when the game is won, displays the highscores
	public void gameWon(long score){
		mainPanel.updateScore(score);
        if (canvas != null) {
            canvas.displayHighscore(score, true);
        }
	}
	
	//can be called when the game is lost, displays the highscores
	public void gameLost(long score){
		mainPanel.updateScore(score);
        if (canvas != null) { 
            canvas.displayHighscore(score, false);
        }
	}
	
	//makes the right panel update the score counter to the current one
	public void updateScore(long score){
		mainPanel.updateScore(score);
	}


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


	public void showMainMenu() {
		getContentPane().removeAll();
		playBackgroundMusic("C:\\Users\\FATIMA WASEEM\\OneDrive - Higher Education Commission\\Desktop\\bubble_shooter-master1\\bubble_shooter\\background.wav");
		mainPanel = new SettingsPanel(this);
		mainPanel.initComponents();
		add(mainPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public SettingsPanel getMainPanel() {
		return mainPanel;
	}

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

	public void stopBackgroundMusic() {
		if (backgroundMusic != null && backgroundMusic.isRunning()) {
			backgroundMusic.stop();
			backgroundMusic.close();
		}
	}

}

