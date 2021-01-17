
public enum Direction {
	right, up, left, down;

	public Direction inverse() {
		switch (this) {
		case down: return up;
		case left: return right;
		case right: return left;
		case up: return down;
		default: return null;
		}
	}
	
	public static Direction random() {
		int rand = (int) Math.floor(Math.random() * values().length);
		return values()[rand];
	}
}
