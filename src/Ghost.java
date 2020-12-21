
public abstract class Ghost extends Entity {
	public Ghost(GameWorld gw) {
		super(gw);
	}
	
	abstract void findPath();
	
	public void accept(EntityVisitor visitor) {
		visitor.visitGhost();
	}
	
	public void tick() {
		// TODO
	}
}
