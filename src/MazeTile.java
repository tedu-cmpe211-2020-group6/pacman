
public class MazeTile extends Entity {

	public MazeTile(GameWorld world, boolean topWall, boolean rightWall, boolean bottomWall, boolean leftWall, Item item) {
		super(world);
		walls[Direction.up.ordinal()] = topWall;
		walls[Direction.right.ordinal()] = rightWall;
		walls[Direction.down.ordinal()] = bottomWall;
		walls[Direction.left.ordinal()] = leftWall;
		this.item = item;
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitMazeTile(this);
	}

	@Override
	public void tick() {
		return;
	}
	
	public boolean hasWall(Direction dir) {
		return walls[dir.ordinal()];
	}
	
	public Item item() {
		return item;
	}
	
	public void eatItem() {
		if (item == Item.none) return;
		Item temp = item;
		item = Item.none;
		world.didEatItem(temp);
	}
	
	public MazePos getPathfindingPos() {
		return pathfindingPos;
	}

	public void setPathfindingPos(MazePos pathfindingPos) {
		this.pathfindingPos = pathfindingPos;
	}

	private boolean[] walls = new boolean[4];
	private Item item;
	private MazePos pathfindingPos;
}
