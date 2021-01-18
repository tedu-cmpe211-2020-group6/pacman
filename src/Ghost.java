
public abstract class Ghost extends Entity {
	private static final int SPEED = 20;
	
	public Ghost(GameWorld gw) {
		super(gw);
	}
	
	abstract Direction findPath();
	
	public void accept(EntityVisitor visitor) {
		visitor.visitGhost(this);
	}
	
	public void tick() {
//		if (world.ticks() % SPEED == 0) {
//			do {
//				changeDirection(Direction.random());
//			} while (cannotMove());
//			move();
//		}
		if (world.ticks() % SPEED == 0) {
			Direction pathfind = findPath();
			
			changeDirection(pathfind);
			move();
		}
	}
	
	public boolean isInBlueMode() {
		return world.isInBlueGhostMode();
	}
	
	public void kill() {
		if (!isInBlueMode()) return;
		world.didKillGhost(this);
		super.kill();
	}
	
	// used for pathfinding
	protected boolean canMove(MazePos pos, Direction dir) {
		PathfindingTile[][] pfm = world.getPathfindingMaze();
		if (pos.getY() <= 0 || pos.getX() <= 0 || pos.getY() >= pfm.length-2 || pos.getX() >= pfm[0].length-2) return false;
		pos = pos.move(dir, 1);
		return !pfm[pos.getY()][pos.getX()].hasWall();
	}
}
