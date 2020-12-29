import java.awt.Color;
import java.awt.Graphics;

public class DrawingVisitor implements EntityVisitor {
	private Graphics g;
	
	public DrawingVisitor(Graphics g) {
		this.g = g;
	}

	@Override
	public void visitPacman(Pacman p) {
		// TODO Draw Pac-Man
		MazePos pos = p.getPosition();
		
		g.setColor(Color.yellow);
		g.fillArc(pos.getX() * 10, pos.getY() * 10, 20, 20, 45, 270);

		g.setColor(Color.black);
		g.drawString("pacman", pos.getX() * 10, pos.getY() * 10);
	}

	@Override
	public void visitGhost(Ghost g) {
		// TODO Draw ghost
		
	}

	@Override
	public void visitMazeTile(MazeTile mt) {
		// TODO Draw maze tile
		
	}

}
