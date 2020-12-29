import java.util.ArrayList;

public class GameWorld {
	
	private int score;
	private MazeTile[][] maze = {
			{ new MazeTile(this, false, false, false, false, null), new MazeTile(this, false, true, false, false, null) },
			{ new MazeTile(this, false, false, false, false, null), new MazeTile(this, false, true, false, false, null) },
			{ new MazeTile(this, false, false, false, false, null), new MazeTile(this, false, true, false, false, null) },
			{ new MazeTile(this, false, false, false, false, null), new MazeTile(this, false, true, false, false, null) },
			{ new MazeTile(this, false, false, false, false, null), new MazeTile(this, false, true, false, false, null) },
			{ new MazeTile(this, true, true, true, true, null), new MazeTile(this, false, true, false, false, null) }
	};
	private Pacman pacman = new Pacman(this);
	
	private ArrayList<Entity> entities = new ArrayList<>();

	public GameWorld() {
		entities.add(pacman);
		entities.add(new Blinky(this));
		entities.add(new Pinky(this));
		entities.add(new Inky(this));
		entities.add(new Clyde(this));
	}
	
	public void tick() {
		for (Entity e : entities) e.tick();
	}
	
	public void didEatItem(Item item) {
		score += item.pointValue();
	}

	public MazeTile mazeTileAt(MazePos pos) {
		return maze[pos.getY() - 1][pos.getX() - 1];
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
}
