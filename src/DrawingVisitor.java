import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DrawingVisitor implements EntityVisitor {
	private Graphics g;
	private static int TILE_SIZE = 40;
	private GameWorld world;
	private int w, h;
	public DrawingVisitor(Graphics g, GameWorld world, int w, int h) {
		this.g = g;
		this.world = world;
		this.w = w;
		this.h = h;
		
		black();
		g.fillRect(0, 0, w, h);
		
		white();
		if (world.isInBlueGhostMode()) {
			g.drawString("BLUE GHOST MODE", 100, 20);
		}
		
		g.drawString("pellet count:"+world.debugNumPellets(), 20, 20);
		
		System.out.println("SCORE: "+world.getScore());
	}

	@Override
	public void visitPacman(Pacman p) {
		// TODO Draw Pac-Man
		MazePos pos = p.getPosition();
		int startAngle = 45 + 90 * p.getDirection().ordinal();
		yellow();
		g.fillArc(
				pos.getX() * TILE_SIZE + TILE_SIZE/4,
				pos.getY() * TILE_SIZE + TILE_SIZE/4,
				TILE_SIZE/2, TILE_SIZE/2,
				startAngle, 
				world.ticks() % 4 < 2 ? 270 : 360
		);

		white();
		normalFont();
		g.drawString("pacman", pos.getX() * TILE_SIZE, pos.getY() * TILE_SIZE);
	}

	@Override
	public void visitGhost(Ghost g) {
		// TODO Draw ghost
		
	}

	@Override
	public void visitMazeTile(MazeTile mt) {
		MazePos pos = mt.getPosition();
		int x = pos.getX() * TILE_SIZE, y = pos.getY() * TILE_SIZE;
		
		g.setColor(Color.blue);
		if (mt.hasWall(Direction.up)) g.drawLine(x, y, x + TILE_SIZE, y); 
		if (mt.hasWall(Direction.right)) g.drawLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE); 
		if (mt.hasWall(Direction.down)) g.drawLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE); 
		if (mt.hasWall(Direction.left)) g.drawLine(x, y, x, y + TILE_SIZE); 

		normalFont();
		switch (mt.item()) {
		case pellet:
			white();
			g.drawString("pellet", x, y+20);
			break;
		case powerPellet:
			yellow();
			g.drawString("power\npellet", x, y+20);
		default:
		}
	}

	@Override
	public void visitWinnerCelebration(WinnerCelebration wc) {
		if (world.ticks() % 4 < 2) {
			bigFont();
			g.drawString("YOU WIN", w / 2, h / 2);
		}
	}
	
	private void bigFont() {
		g.setFont(new Font("Consolas", Font.PLAIN, 36));
	}

	private void normalFont() {
		g.setFont(new Font("Consolas", Font.PLAIN, 10));
	}
	
	private void white() {
		g.setColor(Color.white);
	}
	
	private void yellow() {
		g.setColor(Color.yellow);
	}
	
	private void black() {
		g.setColor(Color.black);
	}
}
