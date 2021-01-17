import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Predicate;

public class GameWorld {
	public static final long NANOSECONDS_PER_TICK = 1000000000 / 100; // 1/100 sec
	
	private int score;
	private int ticks;
	private int numPellets;
	private boolean blueGhostMode;
	private int lives = 3;
	private boolean didWin;
	private int blueGhostModeTimer = 0;
	private int cooldownTimer = 0;
	private boolean resetNextTick = false;
	private boolean gameOver = false;
	
	/* 
	 * < Left wall
	 * > Right wall
	 * ^ Top wall
	 * _ Bottom wall
	 * . Pellet
	 * o Power pellet
	 * a Apple
	 * s Strawberry
	 */
	private MazeTile[][] maze;
	
	private Pacman pacman;
	
	private ArrayList<Entity> entities;
	private ArrayList<Ghost> ghosts;

	public GameWorld() {
		reset();
	}
	
	public void tick() {		
		ticks++;
		
		if (gameOver) return;
		if (cooldownTimer > 0) {
			cooldownTimer--;
			return;
		}
		
		if (resetNextTick) {
			reset();
			resetNextTick = false;
		}
		
		if (numPellets == 0 && !didWin) {
			win();
		}
		
		if (blueGhostMode && ++blueGhostModeTimer >= 400) {
			blueGhostMode = false;
		}
		
		if (pacman.isDead()) {
			lives--;
			if (lives == 0) endGame();
			else {
				cooldownTimer = 300;
				resetNextTick = true;
				return;
			}
		}
		
		for (Entity e : entities) e.tick();
		
		Predicate<Entity> entityDead = new Predicate<Entity>() { @Override public boolean test(Entity e) {
			return e.isDead();
		} };
		entities.removeIf(entityDead);
		ghosts.removeIf(entityDead);
	}
	
	private void reset() {
		System.out.println("=============== RESETTING ===============");
		if (pacman != null) System.out.println(pacman.touchingGhost().getClass());
		
		numPellets = 0;
		score = 0;

		maze = new MazeTile[][] {
				r("<o^    .^    .^   ^.    <.>^   .^    .^    .^    o^>"),
				r("<.    <^    ^>     .    <.>    .    <^    ^>     .>"),
				r("<.    <_    _>     .    <.>    .    <_    _>     .>"),
				r("<.     .     .     .     .     .     .     .     .>"),
				r("<.>   <_^   ^_>    .    <^_>   .    <_^   ^_>    .>"),
				r("<.     .     .     .     .     .     .     .     .>"),
				r("<.     .^    .^   <^     a     ^>    ^.   ^.     .>"),
				r("<.     .^   <s    <_    _      _>    s>   ^.     .>"),
				r("<.>    .    <._    ._    ._    ._   _.>    .    <.>"),
				r("<.>    ._    ._    ._    a_    ._    ._    ._   <.>"),
				r("<._    ._    ._    ._    ._    ._    ._    ._   _.>"),
		};
		
		for (int y = 0; y < maze.length; y++) for (int x = 0; x < maze[0].length; x++) {
			maze[y][x].setPosition(new MazePos(x + 1, y + 1));
		}
		
		entities = new ArrayList<>();
		ghosts = new ArrayList<>();
		pacman = new Pacman(this);
		
		entities.add(pacman);
		ghosts.add(new Blinky(this));
		ghosts.add(new Pinky(this));
		ghosts.add(new Inky(this));
		ghosts.add(new Clyde(this));
		entities.addAll(ghosts);
		
		score = 0;
	}

	private void endGame() {
		gameOver = true;
		entities.add(new FlashingMessage(this, "!!! GAME OVER ("+score+") !!!"));
	}

	private void win() {
		didWin = true;
		entities.add(new FlashingMessage(this, "!!! YOU WIN ("+score+") !!!"));
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
		try {
			return maze[pos.getY() - 1][pos.getX() - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return parseTile("<^_>");
		}
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
		case 's': item = Item.strawberry; numPellets++; break;
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

	public ArrayList<Ghost> ghosts() {
		return ghosts;
	}

	public int lives() {
		return lives;
	}

	public void didKillGhost(Ghost ghost) {
		switch (ghosts.size()) {
		case 4: score += 200;  break;
		case 3: score += 400;  break;
		case 2: score += 800;  break;
		case 1: score += 1600; break;
		}
	}
}
