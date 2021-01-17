import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawingVisitor implements EntityVisitor {
	private Graphics g;
	private static int BLOCKSIZE = 40;
	private GameWorld world;
	private int w, h, mazeXOffset, mazeYOffset;

	public DrawingVisitor(Graphics g, GameWorld world, int w, int h) {
		if (!imagesLoaded) try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load images: "+e.getMessage());
		}
		
		this.g = g;
		this.world = world;
		this.w = w;
		this.h = h;
		
		mazeXOffset = (w - BLOCKSIZE * (world.mazeWidth() + 2)) / 2;
		mazeYOffset = (h - BLOCKSIZE * (world.mazeHeight() + 2)) / 2;

		black();
		g.fillRect(0, 0, w, h);

		white();

		for (int i = 1; i <= world.lives(); i++) {
			g.drawImage(heart, 10 + 30 * i, 10, null);
		}

		if (world.ticks() % 10 == 0) System.out.println("SCORE: " + world.getScore() + "\tPELLETS: " + world.debugNumPellets());
	}

	@Override
	public void visitPacman(Pacman p) {
		// TODO Draw Pac-Man
		MazePos pos = p.getPosition();
		int x = pos.getX() * BLOCKSIZE + mazeXOffset, y = pos.getY() * BLOCKSIZE + mazeYOffset;
		int startAngle = 45 + 90 * p.getDirection().ordinal();
		
		yellow();
		g.fillArc(x + BLOCKSIZE / 4, y + BLOCKSIZE / 4, BLOCKSIZE / 2,
				BLOCKSIZE / 2, startAngle, (world.ticks() / 10) % 4 < 2 ? 270 : 360);

		white();
		normalFont();
		g.drawString(p.getDirection().toString(), x, y);
	}

	@Override
	public void visitGhost(Ghost gh) {
		MazePos pos = gh.getPosition();
		int x = pos.getX() * BLOCKSIZE + mazeXOffset, y = pos.getY() * BLOCKSIZE + mazeYOffset;
		
		g.drawString(gh.getClass().getName(), x, y + 30);
		BufferedImage img = null;
		if (gh instanceof Blinky) img = blinky;
		if (gh instanceof Pinky) img = pinky;
		if (gh instanceof Inky) img = inky;
		if (gh instanceof Clyde) img = clyde;
		if (world.isInBlueGhostMode()) img = blueGhost;
		g.drawImage(img, x+5, y+5, null);
	}

	@Override
	public void visitMazeTile(MazeTile mt) {
		MazePos pos = mt.getPosition();
		int x = pos.getX() * BLOCKSIZE + mazeXOffset, y = pos.getY() * BLOCKSIZE + mazeYOffset;

		g.setColor(Color.blue);
		if (mt.hasWall(Direction.up))
			g.drawLine(x, y, x + BLOCKSIZE, y);
		if (mt.hasWall(Direction.right))
			g.drawLine(x + BLOCKSIZE, y, x + BLOCKSIZE, y + BLOCKSIZE);
		if (mt.hasWall(Direction.down))
			g.drawLine(x, y + BLOCKSIZE, x + BLOCKSIZE, y + BLOCKSIZE);
		if (mt.hasWall(Direction.left))
			g.drawLine(x, y, x, y + BLOCKSIZE);

		normalFont();
		switch (mt.item()) {
		case pellet:
			white();
			g.fillArc(x+18, y+18, 4, 4, 0, 360);
			break;
		case powerPellet:
			yellow();
			g.fillArc(x+15, y+15, 10, 10, 0, 360);
			break;
		case apple:
			g.drawImage(apple, x+5, y+5, null);
			break;
		case strawberry:
			g.drawImage(strawberry, x+5, y+5, null);
			break;
		default:
		}
	}

	@Override
	public void visitFlashingMessage(FlashingMessage fm) {
		String msg = fm.getMessage();
		if ((world.ticks() / 10) % 4 < 2) {
			bigFont();
			FontMetrics metrics = ((Graphics2D) g).getFontMetrics();
			g.drawString(msg, (w - metrics.stringWidth(msg)) / 2, h / 2);
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
	
	private static boolean imagesLoaded = false;
	private static BufferedImage heart, strawberry, apple, blinky, inky, pinky, clyde, blueGhost;

	private void loadImages() throws IOException {
		imagesLoaded = true;
		File folderInput1 = new File("./assets/heart.png");
		heart = ImageIO.read(folderInput1);
		File folderInput2 = new File("./assets/strawberry.png");
		strawberry = ImageIO.read(folderInput2);
		File folderInput3 = new File("./assets/redapple.png");
		apple = ImageIO.read(folderInput3);
		File folderInput7 = new File("./assets/blinky.png");
		blinky = ImageIO.read(folderInput7);
		File folderInput8 = new File("./assets/inky.png");
		inky = ImageIO.read(folderInput8);
		File folderInput9 = new File("./assets/pinky.png");
		pinky = ImageIO.read(folderInput9);
		File folderInput10 = new File("./assets/clyde.png");
		clyde = ImageIO.read(folderInput10);
		File folderInput11 = new File("./assets/blue-ghost.png");
		blueGhost = ImageIO.read(folderInput11);
	}
}
