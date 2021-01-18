
public class PathfindingTile {
	private boolean isWall;
	private MazeTile mazeTile;
	
	public PathfindingTile(MazeTile mt) {
		mazeTile = mt;
	}
	
	public static PathfindingTile wall() {
		PathfindingTile rv = new PathfindingTile(null);
		rv.isWall = true;
		return rv;
	}
	
	public boolean hasWall() {
		return isWall;
	}
	
	public MazeTile associatedMazeTile() {
		return mazeTile;
	}

	public static PathfindingTile empty() {
		return new PathfindingTile(null);
	}
}
