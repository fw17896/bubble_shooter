package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.*;


// the class for providing a canvas for the game and the highscores, and for managing the highscores
public class Canvas extends JPanel implements MouseMotionListener, MouseListener, ActionListener {
	private Arrow arrow;
	private Game game;
	private JLayeredPane lPane;
	private JPanel highscorePanel;
	private JPanel namePanel;
	private JLabel resultText;
	private JTextField textField;
	private Highscores highscores;
	private JTable highscoreTable;
	private JScrollPane scrollPane;
	private static final String fileName = "bubble_shooter_hs.dat";
	private boolean gameStarted = false;
	private MainFrame mainFrame;
	private Clip backgroundMusic;
	private JPanel blur;
	private BufferedImage backgroundImage;

	// constructor for the class. sets of the table that displayes the highscores, a layer to mildly blur the underlying bubbles and a dialog for asking for a name of the player reached the toplist 
	public Canvas(){
		setLayout(new BorderLayout());
		setPreferredSize(null); // Let layout manager decide

		setBorder(BorderFactory.createEmptyBorder());
		addMouseMotionListener(this);
		addMouseListener(this);
		setOpaque(true);
		arrow = new Arrow();
		
		lPane = new JLayeredPane();
		lPane.setBackground(new Color(0,0,0,0));

		blur = new JPanel();
		blur.setBackground(new Color(0, 0, 0, 100));
		
		highscorePanel = new JPanel();	
		highscorePanel.setBackground(new Color(highscorePanel.getBackground().getRed(),
									           highscorePanel.getBackground().getGreen(),
									           highscorePanel.getBackground().getRed(),
									           120));
		highscorePanel.setLayout(new BorderLayout());
		
		highscores = new Highscores();
		
		highscoreTable = new JTable();
		highscoreTable.setOpaque(false);
		((DefaultTableCellRenderer)highscoreTable.getDefaultRenderer(Object.class)).setOpaque(false);

		highscoreTable.setFont(new Font("Verdana", Font.BOLD, 16));
		highscoreTable.setForeground(Color.WHITE);
		highscoreTable.setBackground(new Color(0, 0, 0, 0)); // transparent

		highscoreTable.setRowHeight(32);
		highscoreTable.setGridColor(new Color(255, 255, 255, 60));

		JTableHeader header = highscoreTable.getTableHeader();
		header.setFont(new Font("Verdana", Font.BOLD, 18));
		header.setForeground(Color.YELLOW);
		header.setBackground(new Color(0, 0, 0, 180));
		header.setOpaque(false);

        scrollPane = new JScrollPane(highscoreTable);
        scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

        highscorePanel.add(scrollPane,BorderLayout.CENTER);

		lPane.add(blur,JLayeredPane.DEFAULT_LAYER);
		lPane.add(highscorePanel,JLayeredPane.PALETTE_LAYER);
		
		namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		
		JPanel subNamePanel = new JPanel();	
		subNamePanel.setLayout(new BoxLayout(subNamePanel, BoxLayout.Y_AXIS));
		subNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		resultText = new JLabel("Kecskeeee");
		resultText.setFont(new Font(resultText.getFont().getName(), Font.ITALIC, 30));
		resultText.setAlignmentX(CENTER_ALIGNMENT);
		resultText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		JLabel please = new JLabel("<html><div style=\"text-align: center;\">You made it to the toplist, please enter your name and click the button to proceed!</html>");
		please.setFont(new Font(please.getFont().getName(), Font.PLAIN, 13));
		please.setAlignmentX(CENTER_ALIGNMENT);
		please.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		textField = new JTextField(20);
		JButton button = new JButton("Enter");
		button.addActionListener(this);
		
		JPanel formContainer = new JPanel();
		formContainer.add(textField);
		formContainer.add(button);
		
		subNamePanel.add(resultText);
		subNamePanel.add(please);
		subNamePanel.add(formContainer);
		namePanel.add(subNamePanel,BorderLayout.CENTER);

		setLayout(null); // allow absolute positioning


		try {
            backgroundImage = ImageIO.read(new File("C:\\\\Users\\\\FATIMA WASEEM\\\\OneDrive - Higher Education Commission\\\\Desktop\\\\bubble_shooter-master1\\\\bubble_shooter\\\\bdg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading background image! Please check the file path: C:\\\\Users\\\\FATIMA WASEEM\\\\OneDrive - Higher Education Commission\\\\Desktop\\\\bubble_shooter-master1\\\\bubble_shooter\\\\bdg.jpg");
        }
		
	}

	public void styleGameButton(JButton button) {
		button.setBackground(new Color(255, 140, 0));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
}

	

	public void displayHighscore(long score, boolean win){
		gameStarted = false;
		resultText.setText(win ? "You win!" : "You lose");

		int currentCanvasWidth = getWidth();
        int currentCanvasHeight = getHeight();

		lPane.setBounds(0, 0, currentCanvasWidth, currentCanvasHeight);        
        blur.setBounds(0, 0, currentCanvasWidth, currentCanvasHeight); 

        int hsPanelPreferredWidth = (int) (currentCanvasWidth * 0.8);
        int hsPanelPreferredHeight = (int) (currentCanvasHeight * 0.8);
        int hsPanelX = (currentCanvasWidth - hsPanelPreferredWidth) / 2;
        int hsPanelY = (currentCanvasHeight - hsPanelPreferredHeight) / 2;
        highscorePanel.setBounds(hsPanelX, hsPanelY, hsPanelPreferredWidth, hsPanelPreferredHeight);

		lPane.remove(namePanel);

		if(score != 0){
			int namePanelPreferredWidth = (int) (currentCanvasWidth * 0.6);
            int namePanelPreferredHeight = 185;
            int namePanelX = (currentCanvasWidth - namePanelPreferredWidth) / 2;
            int namePanelY = (currentCanvasHeight - namePanelPreferredHeight) / 2;
            namePanel.setBounds(namePanelX, namePanelY, namePanelPreferredWidth, namePanelPreferredHeight);
			lPane.add(namePanel, JLayeredPane.DRAG_LAYER);

			
		}else {
            lPane.remove(namePanel);
        }

		Component[] components = lPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER);
		for (Component comp : components) {
			lPane.remove(comp);
		}

		JButton backButton = new JButton("Back");
		styleGameButton(backButton);
		backButton.setBounds(hsPanelX + hsPanelPreferredWidth - 200, hsPanelY + hsPanelPreferredHeight + 10, 150, 40);
		//-------------back to game ---------------
		backButton.addActionListener(e -> {
			remove(lPane);
			repaint();
			gameStarted = true;
		});

		//---------------back to main menu option----------
		// backButton.addActionListener(e -> {
		// 	if (mainFrame != null) {
		// 		SwingUtilities.getWindowAncestor(this).dispose(); // Closes the game window
		// 		mainFrame.showMainMenu(); // Shows the original main menu
		// 	}
		// });

		lPane.add(backButton, JLayeredPane.MODAL_LAYER);

		remove(lPane);
        add(lPane);

        loadHighscores(); 
        highscoreTable.setModel(highscores);
        highscoreTable.revalidate();
        highscoreTable.repaint();
        
        revalidate();
        repaint();
	}
	
	// instantiates a new game object 
	public void newGame(int row, int color){
		game = new Game(row, color, this);
		gameStarted = true;
		remove(lPane);
		revalidate(); 
		repaint();

	}
	
	// getter for the game object
	public Game getGame(){
		return game;
	}
	
	// writes the highscores to a file named "bubble_shooter_hs.dat"
	private void saveHighscores(){
		try{
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
			os.writeObject(highscores);
			os.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// reads the highscores from the fie named "bubble_shooter_hs.dat"
	private void loadHighscores(){
		try{
			File f = new File("bubble_shooter_hs.dat");
			if(f.exists()){
				ObjectInputStream os = new ObjectInputStream(new FileInputStream(fileName));
				highscores = (Highscores) os.readObject();
				os.close();
			}else {
                highscores = new Highscores(); 
            }
		}
		catch(Exception e){
			e.printStackTrace();
			 highscores = new Highscores();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHints(new RenderingHints(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON));

		if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.LIGHT_GRAY); 
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

		if (gameStarted && game != null){
			game.paintBubbles(g2d);
			arrow.paintComponent(g2d, getLocationOnScreen());
		}
	};
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseMoved(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (gameStarted) {
            repaint();
        }
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(game !=null){
			if(!game.isStopped()){
				game.fire(MouseInfo.getPointerInfo().getLocation(),getLocationOnScreen());
				repaint();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		 String playerName = textField.getText().trim();
        if (!playerName.isEmpty()){
            textField.setEnabled(false);
            JButton sourceButton = (JButton)e.getSource();
            sourceButton.setEnabled(false);

            final long currentScore = (game != null) ? game.getScore() : 0;
            final int initialRows = (game != null) ? game.getInitialRows() : 0;
            final int colors = (game != null) ? game.getColors() : 0;

			try {
				if (game != null && currentScore != 0) {
					highscores.addEntry(new HighscoreEntry(playerName, currentScore, initialRows, colors));
					saveHighscores();
				}

				// Reload updated highscores
				loadHighscores();

				// Clear previous canvas and create a new one
				lPane.removeAll();
				lPane.revalidate();
				lPane.repaint();

				// Display the updated highscore panel
				displayHighscore(0, true);

			} catch (Exception ex) {
				ex.printStackTrace(); // Log any exceptions during saving
			} finally {
				// Re-enable the UI components (not needed if namePanel is gone)
				textField.setEnabled(true);
				sourceButton.setEnabled(true);
			}
		}
	}
	
	public void setMainFrame(MainFrame frame) {
    	this.mainFrame = frame;
}

}
