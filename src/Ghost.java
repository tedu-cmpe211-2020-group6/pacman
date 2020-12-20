
public abstract class Ghost extends Entity {
	public Ghost(GameWorld gw) {
		super(gw);
	}
	
	abstract void findPath();
}
