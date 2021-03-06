package hacker_typer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.security.auth.Refreshable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * It's dangerous to go alone! Take this. 
 * 		 /| ________________ 
 * O|===|* >________________> 
 * 		 \|
 *
 * 
 * @author ecford
 *
 */
public class Main extends JPanel implements MouseListener, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 561173046829833968L;
	private Tabs tabbedPane;
	private ImageIcon monitorIcon, settingsIcon, shopIcon;
	private JTextArea bottomBar;

	public Main() throws IOException, FontFormatException {
		super(new BorderLayout());
		initialize();
		Thread thread = new Thread(this);
		thread.start();

	}

	private void initialize() throws IOException, FontFormatException {

		tabbedPane = new Tabs(new Color(153, 153, 255, 255));
		monitorIcon = createImageIcon("images/computer.png");
		settingsIcon = createImageIcon("images/settings.png");
		shopIcon = createImageIcon("images/shop.png");

		Monitor panel1 = new Monitor("images/computer.png", "font/3-bit.ttf",
				"images/power.png", "images/powerComputer.png",
				"images/cdFlasher.png", "images/floppyFlasher.png", 1024,
				tabbedPane.getBGColor(), "code/Monitor"); // same text as the
															// Monitor class.
															// just in txt form
		panel1.setBounds(0, 0, 1024, 1024);

		Shop panel2 = new Shop(tabbedPane.getBGColor());

		Settings panel3 = new Settings(tabbedPane.getBGColor());

		tabbedPane.addTab("Computer", monitorIcon, panel1,
				"TYPE TYPE TYPE TYPE");
		tabbedPane.addTab("Shop", shopIcon, panel2, "Every game needs a shop");
		tabbedPane.addTab("Settings", settingsIcon, panel3, "Pretty Colors!");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		// and now for something completely different
		bottomBar = new JTextArea("Lines: " + getAmountOfLines()
				+ " | LinesPerSec: " + getLinesPerSec() + "LPS");
		bottomBar.setSize(WIDTH, 20);
		bottomBar.setEditable(true);

		// Add the tabbed pane to this panel
		add(tabbedPane, BorderLayout.NORTH);
		add(bottomBar, BorderLayout.SOUTH);
		tabbedPane.addMouseListener(this);
	}

	private double getLinesPerSec() {
		return tabbedPane.getLinesPerSec();
	}

	private int getAmountOfLines() {
		return tabbedPane.getAmountOfLines();
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @throws IOException
	 */
	protected static ImageIcon createImageIcon(String path) throws IOException {
		File img = new File(path);
		return new ImageIcon(ImageIO.read(img).getScaledInstance(20, -1, 0));
	}

	public void eventOutput(String eventDescription, MouseEvent e) {
		tabbedPane.eventInput(e);

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		eventOutput("Mouse pressed (# of clicks: " + e.getClickCount() + ")", e);
	}

	public void mouseReleased(MouseEvent e) {
		eventOutput("Mouse released (# of clicks: " + e.getClickCount() + ")",
				e);
	}

	public void mouseEntered(MouseEvent e) {
		eventOutput("Mouse entered", e);
	}

	public void mouseExited(MouseEvent e) {
		eventOutput("Mouse exited", e);
	}

	public void mouseClicked(MouseEvent e) {
		eventOutput("Mouse clicked (# of clicks: " + e.getClickCount() + ")", e);
		eventOutput("Mouse click location (" + e.getX() + "," + e.getY() + ")",
				e);
		tabbedPane.mouseClick(e);

	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() /* Forest, run! */{
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					createAndShowGUI();
				} catch (IOException | FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private static void createAndShowGUI() throws IOException,
			FontFormatException {
		// Create and set up the window.
		JFrame frame = new JFrame("Hacker Typer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add content to the window.
		frame.add(new Main(), BorderLayout.CENTER);

		// Display the window.
		frame.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void run() /* Forest, run! */{
		while (true) {
			refresh();
			try {
				Thread.sleep(50); // night night!
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void refresh() {
		bottomBar.setText("Lines: " + getAmountOfLines() + " | LinesPerSec: "
				+ getLinesPerSec() + "LPS");

	}

}
