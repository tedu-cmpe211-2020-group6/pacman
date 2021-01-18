
public abstract class Ghost extends Entity {
	private static final int SPEED = 20;
	
	public Ghost(GameWorld gw) {
		super(gw);
	}
	
	abstract MazePos findPath();
	
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
			MazePos pathfindPos = findPath();
			
			MazeTile mt = world.getPathfindingMaze()[pathfindPos.getY()][pathfindPos.getX()].associatedMazeTile();
			
			if (mt != null) {
				setPosition(mt.getPosition());
			}
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
		if (pos.getY() <= 0 || pos.getX() <= 0 || pos.getY() >= pfm.length-1 || pos.getX() >= pfm[0].length-1) return false;
		pos = pos.move(dir, 1);
		return !pfm[pos.getY()][pos.getX()].hasWall();
	}
}
