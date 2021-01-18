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
	private int respawnBlinkyTimer = 0, respawnPinkyTimer = 0, respawnInkyTimer = 0, respawnClydeTimer = 0;
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
	private PathfindingTile[][] pathfindingMaze;
	
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
		
		respawnBlinkyTimer--;
		if (respawnBlinkyTimer == 0) {
			Blinky b = new Blinky(this);
			ghosts.add(b);
			entities.add(b);
		}
		respawnPinkyTimer--;
		if (respawnPinkyTimer == 0) {
			Pinky p = new Pinky(this);
			ghosts.add(p);
			entities.add(p);
		}
		respawnInkyTimer--;
		if (respawnInkyTimer == 0) {
			Inky in = new Inky(this);
			ghosts.add(in);
			entities.add(in);
		}
		respawnClydeTimer--;
		if (respawnClydeTimer == 0) {
			Clyde c = new Clyde(this);
			ghosts.add(c);
			entities.add(c);
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
			if (e instanceof Blinky && e.isDead()) respawnBlinkyTimer = 500;
			if (e instanceof Pinky && e.isDead()) respawnPinkyTimer = 500;
			if (e instanceof Inky && e.isDead()) respawnInkyTimer = 500;
			if (e instanceof Clyde && e.isDead()) respawnClydeTimer = 500;
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
				r("<o^    .^    .^   ^.     .^    .^    .^    .^    o^>"),
				r("<.    <^    ^>     .    <.>    .    <^    ^>     .>"),
				r("<.    <_    _>     .    <.>    .    <_    _>     .>"),
				r("<.     .     .     .     .     .     .     .     .>"),
				r("<.>   <_^   ^_>    .    <^_>   .    <_^   ^_>    .>"),
				r("<.     .     .     .     .     .     .     .     .>"),
				r("<.     .^    .^   <      a      >    ^.   ^.     .>"),
				r("<.     .^   <s    <_    _      _>    s>   ^.     .>"),
				r("<.>    .    <._    ._    ._    ._   _.>    .    <.>"),
				r("<.>    ._    ._    ._    a_    ._    ._    ._   <.>"),
				r("<._    ._    ._    ._    ._    ._    ._    ._   _.>"),
		};

		
		initPathfindingMaze();
		
		for (int y = 0; y < getMaze().length; y++) for (int x = 0; x < getMaze()[0].length; x++) {
			getMaze()[y][x].setPosition(new MazePos(x + 1, y + 1));
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

	private void initPathfindingMaze() {
		pathfindingMaze = new PathfindingTile[2 * maze.length + 1][2 * maze[0].length + 1];
		
		for (int y = 0; y < maze.length; y++) for (int x = 0; x < maze[y].length; x++) {
			int pfY = 2 * y + 1, pfX = 2 * x + 1;
			MazeTile mt = maze[y][x];
			mt.setPathfindingPos(new MazePos(pfX, pfY));
			pathfindingMaze[pfY][pfX] = new PathfindingTile(mt);
			if (mt.hasWall(Direction.left)) {
				pathfindingMaze[pfY][pfX-1] = PathfindingTile.wall();
				pathfindingMaze[pfY-1][pfX-1] = PathfindingTile.wall();
				pathfindingMaze[pfY+1][pfX-1] = PathfindingTile.wall();
			}
			if (mt.hasWall(Direction.up)) {
				pathfindingMaze[pfY-1][pfX] = PathfindingTile.wall();
				pathfindingMaze[pfY-1][pfX-1] = PathfindingTile.wall();
				pathfindingMaze[pfY-1][pfX+1] = PathfindingTile.wall();
			}
			if (mt.hasWall(Direction.right)) {
				pathfindingMaze[pfY][pfX+1] = PathfindingTile.wall();
				pathfindingMaze[pfY-1][pfX+1] = PathfindingTile.wall();
				pathfindingMaze[pfY+1][pfX+1] = PathfindingTile.wall();
			}
			if (mt.hasWall(Direction.down)) {
				pathfindingMaze[pfY+1][pfX] = PathfindingTile.wall();
				pathfindingMaze[pfY+1][pfX-1] = PathfindingTile.wall();
				pathfindingMaze[pfY+1][pfX+1] = PathfindingTile.wall();
			}
		}
		
		// Fill null spots
		for (int y = 0; y < pathfindingMaze.length; y++) for (int x = 0; x < pathfindingMaze[y].length; x++) {
			if (pathfindingMaze[y][x] == null) pathfindingMaze[y][x] = PathfindingTile.empty();
			if (x == 0) System.out.println();
			System.out.print(pathfindingMaze[y][x].hasWall()?"#":" ");
		}
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
			return getMaze()[pos.getY() - 1][pos.getX() - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return parseTile("<^_>");
		}
	}
	
	public int mazeHeight() {
		return getMaze().length;
	}
	
	public int mazeWidth() {
		return getMaze()[0].length;
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

	public Pacman getPacman() {
		return pacman;
	}

	public MazeTile[][] getMaze() {
		return maze;
	}

	public PathfindingTile[][] getPathfindingMaze() {
		return pathfindingMaze;
	}

	public MazePos mazePosToPathfindingMaze(MazePos position) {
		return mazeTileAt(position).getPathfindingPos();
	}
}
