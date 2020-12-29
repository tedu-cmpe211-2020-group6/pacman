
public class MazeTile extends Entity {

	public MazeTile(GameWorld world, boolean topWall, boolean rightWall, boolean bottomWall, boolean leftWall, Item item) {
		super(world);
		walls[Direction.up.ordinal()] = topWall;
		walls[Direction.right.ordinal()] = rightWall;
		walls[Direction.down.ordinal()] = leftWall;
		walls[Direction.left.ordinal()] = leftWall;
		this.item = item;
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitMazeTile(this);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	
	public boolean hasWall(Direction dir) {
		return walls[dir.ordinal()];
	}
	
	public Item item() {
		return item;
	}
	
	public void eatItem() {
		Item temp = item;
		item = null;
		world.didEatItem(temp);
	}
	
	private boolean[] walls = new boolean[4];
	private Item item;
}
