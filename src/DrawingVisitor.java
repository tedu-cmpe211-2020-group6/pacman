import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawingVisitor implements EntityVisitor {
	private Graphics g;
	private static int BLOCKSIZE = 40;
	private GameWorld world;
	private int w, h;

	public DrawingVisitor(Graphics g, GameWorld world, int w, int h) {
		if (!imagesLoaded) try{loadImages();} catch(Exception e) {}
		
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

		g.drawString("pellet count:" + world.debugNumPellets() + ", hearts: " + world.lives(), 20, 20);

		System.out.println("SCORE: " + world.getScore());
	}

	@Override
	public void visitPacman(Pacman p) {
		// TODO Draw Pac-Man
		MazePos pos = p.getPosition();
		int startAngle = 45 + 90 * p.getDirection().ordinal();
		yellow();
		g.fillArc(pos.getX() * BLOCKSIZE + BLOCKSIZE / 4, pos.getY() * BLOCKSIZE + BLOCKSIZE / 4, BLOCKSIZE / 2,
				BLOCKSIZE / 2, startAngle, (world.ticks() / 10) % 4 < 2 ? 270 : 360);

		white();
		normalFont();
		g.drawString("pacman", pos.getX() * BLOCKSIZE, pos.getY() * BLOCKSIZE);
	}

	@Override
	public void visitGhost(Ghost gh) {
		MazePos pos = gh.getPosition();
		int x = pos.getX() * BLOCKSIZE, y = pos.getY() * BLOCKSIZE;
		
		g.drawString(gh.getClass().getName(), x, y + 30);
	}

	@Override
	public void visitMazeTile(MazeTile mt) {
		MazePos pos = mt.getPosition();
		int x = pos.getX() * BLOCKSIZE, y = pos.getY() * BLOCKSIZE;

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
			g.drawString("pellet", x, y + 20);
			break;
		case powerPellet:
			yellow();
			g.drawString("power\npellet", x, y + 20);
		default:
		}
	}

	@Override
	public void visitWinnerCelebration(WinnerCelebration wc) {
		if ((world.ticks() / 10) % 4 < 2) {
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
	
	private boolean imagesLoaded = false;
	private BufferedImage pacman, heart, strawberry, apple, grape, cherry, redapple, blinky, inky, pinky, clyde;

	private void loadImages() throws IOException {
		imagesLoaded = true;
		File folderInput = new File("C:\\Users\\Begüm\\Desktop\\pacman.png");
		pacman = ImageIO.read(folderInput);
		File folderInput1 = new File("C:\\Users\\Begüm\\Desktop\\heart.png");
		heart = ImageIO.read(folderInput1);
		File folderInput2 = new File("C:\\Users\\Begüm\\Desktop\\stawberry.png");
		strawberry = ImageIO.read(folderInput2);
		File folderInput3 = new File("C:\\Users\\Begüm\\Desktop\\apple.png");
		apple = ImageIO.read(folderInput3);
		File folderInput4 = new File("C:\\Users\\Begüm\\Desktop\\grape.png");
		grape = ImageIO.read(folderInput4);
		File folderInput5 = new File("C:\\Users\\Begüm\\Desktop\\cherry.png");
		cherry = ImageIO.read(folderInput5);
		File folderInput6 = new File("C:\\Users\\Begüm\\Desktop\\redapple.png");
		redapple = ImageIO.read(folderInput6);
		File folderInput7 = new File("C:\\Users\\Begüm\\Desktop\\blinky.png");
		blinky = ImageIO.read(folderInput7);
		File folderInput8 = new File("C:\\Users\\Begüm\\Desktop\\inky.png");
		inky = ImageIO.read(folderInput8);
		File folderInput9 = new File("C:\\Users\\Begüm\\Desktop\\pinky.png");
		pinky = ImageIO.read(folderInput9);
		File folderInput10 = new File("C:\\Users\\Begüm\\Desktop\\clyde.png");
		clyde = ImageIO.read(folderInput10);
	}
}
