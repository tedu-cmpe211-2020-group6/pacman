import java.awt.Graphics;

public class DrawingVisitor implements EntityVisitor {
	private Graphics g;
	
	public DrawingVisitor(Graphics g) {
		this.g = g;
	}

	@Override
	public void visitPacman() {
		// TODO Draw Pac-Man
		
	}

	@Override
	public void visitGhost() {
		// TODO Draw ghost
		
	}

	@Override
	public void visitMazeTile() {
		// TODO Draw maze tile
		
	}

}
