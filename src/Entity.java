
public abstract class Entity {
	private MazePos position = new MazePos(1, 1);
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
		this.position = position;
	}
}
