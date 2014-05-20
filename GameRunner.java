import java.awt.*;
import javax.swing.*;

public class GameRunner extends JApplet {

	private JPanel panel = new JPanel(new BorderLayout());

	public void init() {
		Board b = new Board();
		panel.add(b, BorderLayout.CENTER);
		this.add(panel);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}