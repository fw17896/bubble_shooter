package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.Clip;
import javax.swing.*;


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

		JPanel blur = new JPanel();
		blur.setBackground(new Color(255, 255, 255, 120));
		blur.setBounds(0,0,Constants.FIELD_SIZE_X,Constants.FIELD_SIZE_Y);
		
		highscorePanel = new JPanel();	
		highscorePanel.setBackground(new Color(highscorePanel.getBackground().getRed(),
									           highscorePanel.getBackground().getGreen(),
									           highscorePanel.getBackground().getRed(),
									           120));
		highscorePanel.setBounds(40, 20, Constants.FIELD_SIZE_X-2*40, Constants.FIELD_SIZE_Y-2*30);
		highscorePanel.setLayout(new BorderLayout());
		
		highscores = new Highscores();
		
		highscoreTable = new JTable();
		highscoreTable.setFillsViewportHeight(true);
		highscoreTable.setModel(highscores);
		highscoreTable.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(highscoreTable);
		highscorePanel.add(scrollPane,BorderLayout.CENTER);
		
		lPane.add(blur,JLayeredPane.DEFAULT_LAYER);
		lPane.add(highscorePanel,JLayeredPane.PALETTE_LAYER);
		
		namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.setBounds(80, 60, Constants.FIELD_SIZE_X-2*80,185);
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
		
	}

	public void styleGameButton(JButton button) {
		button.setBackground(new Color(255, 140, 0));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
}

	
	/**
	 * triggers the repainting of the panel with the highscores
	 * displayed. optionally adds a dialog for asking the name
	 * of the player. if the parameter score is unequal zero then
	 * the player has reached the toplist. if it equals zero, the
	 * dialog isn't displayed.
	 */
	public void displayHighscore(long score, boolean win){
		gameStarted = false;
		resultText.setText(win ? "You win!" : "You lose");
		if(score != 0){
			lPane.add(namePanel, JLayeredPane.DRAG_LAYER);
		}
		add(lPane);
		loadHighscores();
		highscoreTable.setModel(highscores);
		repaint();

	}
	
	// instantiates a new game object 
	public void newGame(int row, int color){
		game = new Game(row, color, this);
		gameStarted = true;
		lPane.remove(namePanel);
		remove(lPane);
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
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHints(new RenderingHints(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON));

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
		repaint();
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
		if (!textField.getText().equals("")){
			highscores.addEntry(new HighscoreEntry(textField.getText(),
					game.getScore(), game.getInitialRows(), game.getColors()));
			saveHighscores();
			lPane.remove(namePanel);
			displayHighscore(0, true);
		}
	}
	
	public void setMainFrame(MainFrame frame) {
    	this.mainFrame = frame;
}

}
