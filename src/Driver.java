import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver {
	private static final GameWorld world = new GameWorld();
	private static long time = System.nanoTime();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PacmanWindow win = new PacmanWindow();

		while (true) {
			long dt = System.nanoTime() - time;
			if (dt >= GameWorld.NANOSECONDS_PER_TICK) {
				time += dt;
				world.tick();
				win.repaint();
			}
		}

	}

	class TAdapter extends KeyAdapter {
		public void keypressed(KeyEvent e) {
			
		}
	}

	private static class PacmanWindow extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final int w = 600, h = 600;
		
		public PacmanWindow() {
			super();
			
			JFrame jf = new JFrame("Pac-Man");
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
			
			DrawingVisitor drawing = new DrawingVisitor(g, world, w, h);
			
			for (Entity e : world.getEntities()) {
				e.accept(drawing);
			}
			
			for (int y = 1; y <= world.mazeHeight(); y++) for (int x = 1; x <= world.mazeWidth(); x++) {
				world.mazeTileAt(new MazePos(x, y)).accept(drawing);
			}
			world.toString();
		}
	}

	
}
