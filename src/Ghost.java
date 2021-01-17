
public abstract class Ghost extends Entity {
	private static final int SPEED = 20;
	
	public Ghost(GameWorld gw) {
		super(gw);
	}
	
	abstract void findPath();
	
	public void accept(EntityVisitor visitor) {
		visitor.visitGhost(this);
	}
	
	public void tick() {
		if (world.ticks() % SPEED == 0) {
			do {
				changeDirection(Direction.random());
			} while (cannotMove());
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
}
