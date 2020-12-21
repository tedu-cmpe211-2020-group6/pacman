import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver {
	private static final GameWorld world = new GameWorld();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PacmanWindow win = new PacmanWindow();
	}
	
	private static class PacmanWindow extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final int w = 400, h = 400;
		public PacmanWindow() {
			super();
			
			JFrame jf = new JFrame("Pac-Man");
		    jf.setSize(w, h);
		    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set close button to exit
		    
		    jf.add(this);
		    
		    jf.setVisible(true);
		    this.setVisible(true);
		}
		
		@Override
		public void paint(Graphics g) {
			DrawingVisitor drawing = new DrawingVisitor(g);
			g.drawRect(10, 10, 360, 100);
			world.toString();
		}
	}

	
}
