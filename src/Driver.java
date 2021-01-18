
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver {
	private static final GameWorld world = new GameWorld();
	private static long time = System.nanoTime();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PacmanWindow win = new PacmanWindow();
		
		System.out.println(new File("./assets/inky.jpg").exists());

		while (true) {
			long dt = System.nanoTime() - time;
			if (dt >= GameWorld.NANOSECONDS_PER_TICK) {
				time += dt;
				world.tick();
				win.repaint();
			}
		}
	}

	private static class PacmanWindow extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final int w = 600, h = 600;
		private JFrame jf;
		
		public PacmanWindow() {
			super();
			
			jf = new JFrame("Pac-Man");
			jf.setSize(w, h);
		    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set close button to exit
		    
		    jf.add(this);
		    
		    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

				@Override
				public boolean dispatchKeyEvent(KeyEvent e) {
					world.handleInput(e);
					return false;
				}
		    	
		    });
		    
		    jf.setVisible(true);
		    this.setVisible(true);
		}
		
		@Override
		public void paint(Graphics g) {
			this.setSize(jf.getSize().width, jf.getSize().height);
			
			DrawingVisitor drawing = new DrawingVisitor(g, world, this.getSize().width, this.getSize().height);
			
			for (Entity e : world.getEntities()) {
				e.accept(drawing);
			}
			
			for (int y = 1; y <= world.mazeHeight(); y++) for (int x = 1; x <= world.mazeWidth(); x++) {
				world.mazeTileAt(new MazePos(x, y)).accept(drawing);
			}
		}
	}

	
}
