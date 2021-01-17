
public class FlashingMessage extends Entity {
	private String message;
	
	public FlashingMessage(GameWorld world, String message) {
		super(world);
		this.message = message;
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitFlashingMessage(this);
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void tick() {
		return;
	}
}
