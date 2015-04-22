package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The Menu. This houses all menu items. 
 * @author heshamsalman
 *
 */
public class Menu extends JPanel {
	private static final long serialVersionUID = -6758351920150177724L;
	private MenuItem start;
	private MenuItem hiScores;
	private MenuItem instructions;
	Window window;

	public Menu(Window window) {
		this.window = window;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(0.3f);
		start = new MenuItem(window, "Start");
		start.setSize(150, 120);
		start.setAlignmentX(0.5f);
		hiScores = new MenuItem(window, "Hi-Scores");
		hiScores.setSize(150, 120);
		hiScores.setAlignmentX(0.5f);
		instructions = new MenuItem(window, "Instructions");
		instructions.setSize(150, 120);
		instructions.setAlignmentX(0.5f);
		attachListeners();
		add(start);
		add(Box.createRigidArea(new Dimension(5, 25)));
		add(hiScores);
		add(Box.createRigidArea(new Dimension(5, 25)));
		add(instructions);
	}
	
	/**
	 * Attaches action listeners to buttons.
	 * This is what allows switching from panel to panel on click.
	 */
	private void attachListeners() {
		start.addActionListener(new ActionListener() {

			@Override
            public void actionPerformed(ActionEvent e) {
				System.out.println("Start");
                window.switchToGame();
            }
        });
		
		hiScores.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				window.switchToInstructions();
			}
		});
		hiScores.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				window.switchToHiScores();
			}
		});
	}
}
