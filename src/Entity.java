
public abstract class Entity {
	private MazePos position = new MazePos(1, 1);
	private Direction direction = Direction.down;
	private boolean dead = false;
	protected GameWorld world;
	
	public Entity(GameWorld world) {
		this.world = world;
	}
	
	public abstract void accept(EntityVisitor visitor);
	public abstract void tick();

	public MazePos getPosition() {
		return position;
	}

	public void setPosition(MazePos position) {
		if (position.getX() < 0) position.withX(0);
		if (position.getY() < 0) position.withY(0);
		this.position = position;
	}
	
	public boolean cannotMove() {
		MazePos pos = getPosition();
		return world.mazeTileAt(pos).hasWall(direction)
				|| world.mazeTileAt(pos.move(direction, 1)).hasWall(direction.inverse());
	}
	
	public void move() {
		if (cannotMove()) {
			return;
		}
		setPosition(getPosition().move(getDirection(), 1));
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void changeDirection(Direction dir) {
		direction = dir;
	}

	public boolean isDead() {
		return dead;
	}

	public void kill() {
		dead = true;
	}
	
	public void revive() {
		dead = false;
	}
}
