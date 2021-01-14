
public class MazePos {
	private int x;
	private int y;
	
	public MazePos(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public MazePos(MazePos pos) {
		setX(pos.getX());
		setY(pos.getY());
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public MazePos withX(int x) {
		return new MazePos(x, y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public MazePos withY(int y) {
		return new MazePos(x, y);
	}

	public MazePos move(Direction direction, int n) {
		switch (direction) {
		case down:
			return this.withY(y + n);
		case left:
			return this.withX(x - n);
		case right:
			return this.withX(x + n);
		case up:
			return this.withY(y - n);
		default:
			return this;
		}
	}
}
