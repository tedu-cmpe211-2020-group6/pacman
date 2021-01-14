
public enum Direction {
	right, up, left, down;

	Direction inverse() {
		switch (this) {
		case down: return up;
		case left: return right;
		case right: return left;
		case up: return down;
		default: return null;
		}
	}
}
