
public class GameWorld {
	
	private int score;
	private MazeTile[][] maze;

	public void didEatItem(Item item) {
		score += item.pointValue();
	}

	public MazeTile mazeTileAt(MazePos pos) {
		return maze[pos.getX()][pos.getY()];
	}

}
