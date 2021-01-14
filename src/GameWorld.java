import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameWorld {
	public static final long NANOSECONDS_PER_TICK = 1000000000 / 100; // 1/100 sec
	
	private int score;
	private int ticks;
	private int numPellets;
	private boolean blueGhostMode;
	private boolean didWin;
	private int blueGhostModeTimer = 0;
	
	/* 
	 * < Left wall
	 * > Right wall
	 * ^ Top wall
	 * _ Bottom wall
	 * . Pellet
	 * o Power pellet
	 * a Apple
	 * c Cherry
	 */
	private MazeTile[][] maze = {
			r("<o^    .^    .^   ^.    <.>^   .^    .^    .^    o^>"),
			r("<.    <^    ^>     .    <.>    .    <^    ^>     .>"),
			r("<.    <_    _>     .    <.>    .    <_    _>     .>"),
			r("<.     .     .     .     .     .     .     .     .>"),
			r("<.>   <_^   ^_>    .    <^_>   .    <_^   ^_>    .>"),
			r("<.     .     .     .     .     .     .     .     .>"),
			r("<.     .     .    <.^    .    ^.>    .     .     .>"),
			r("<.     .     .    <._   _.    _.>    .     .     .>"),
			r("<.     .     .     .     .     .     .     .     .>"),
			r("<._    ._    ._    ._    ._    ._    ._    ._    ._>"),
	};
	
	private Pacman pacman = new Pacman(this);
	private WinnerCelebration winnerCelebration = null;
	
	private ArrayList<Entity> entities = new ArrayList<>();

	public GameWorld() {
		entities.add(pacman);
		entities.add(new Blinky(this));
		entities.add(new Pinky(this));
		entities.add(new Inky(this));
		entities.add(new Clyde(this));
		
		for (int y = 0; y < maze.length; y++) for (int x = 0; x < maze[0].length; x++) {
			maze[y][x].setPosition(new MazePos(x + 1, y + 1));
		}
	}
	
	public void tick() {
		if (numPellets == 0 && !didWin) {
			didWin = true;
			winnerCelebration = new WinnerCelebration(this);
			entities.add(winnerCelebration);
		}
		
		if (blueGhostMode && ++blueGhostModeTimer >= 20) {
			blueGhostMode = false;
		}
		
		for (Entity e : entities) e.tick();
		ticks++;
	}
	
	private void win() {

	}

	public void didEatItem(Item item) {
		score += item.pointValue();
		numPellets--;
		
		if (item == Item.powerPellet) startBlueGhostMode();
	}

	private void startBlueGhostMode() {
		blueGhostMode = true;
		blueGhostModeTimer = 0;
		
	}

	public MazeTile mazeTileAt(MazePos pos) {
		return maze[pos.getY() - 1][pos.getX() - 1];
	}
	
	public int mazeHeight() {
		return maze.length;
	}
	
	public int mazeWidth() {
		return maze[0].length;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void handleInput(KeyEvent e) {
		pacman.handleInput(e);
	}
	
	private MazeTile[] r(String rowDesc) {
		ArrayList<MazeTile> rv = new ArrayList<>();
		String[] tiles = rowDesc.trim().split(" +");
		for (String tile: tiles) rv.add(parseTile(tile));
		return rv.toArray(new MazeTile[rv.size()]);
	}
	
	private MazeTile parseTile(String tileDesc) {
		boolean top = false;
		boolean right = false;
		boolean bottom = false;
		boolean left = false;
		Item item = Item.none;
		
		for (char c: tileDesc.toCharArray()) switch (c) {
		case '<': left = true; break;
		case '>': right = true; break;
		case '^': top = true; break;
		case '_': bottom = true; break;
		case '.': item = Item.pellet; numPellets++; break;
		case 'o': item = Item.powerPellet; numPellets++; break;
		case 'a': item = Item.apple; numPellets++; break;
		case 'c': item = Item.cherry; numPellets++; break;
		}
		
		return new MazeTile(this, top, right, bottom, left, item);
	}

	public int getScore() {
		return score;
	}
	
	public int ticks() {
		return ticks;
	}

	public boolean isInBlueGhostMode() {
		return blueGhostMode;
	}

	public int debugNumPellets() {
		return numPellets;
	}
}
