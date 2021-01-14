
public class WinnerCelebration extends Entity {

	public WinnerCelebration(GameWorld world) {
		super(world);
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitWinnerCelebration(this);
	}

	@Override
	public void tick() {
		
	}

}
