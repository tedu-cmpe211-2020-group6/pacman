
public class Pacman extends Entity {

	public Pacman(GameWorld world) {
		super(world);
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitPacman(this);
	}

	@Override
	public void tick() {
		move();
	}
	
	public void changeDirection(Direction dir) {
		direction = dir;
	}
	
	private void move() {
		MazePos pos = getPosition();
		if (world.mazeTileAt(pos).hasWall(direction)) {
			return;
		}
		MazePos newPos = pos;
		switch (direction) {
		case up:
			newPos = pos.withY(pos.getY() - 1);
			break;
		case right:
			newPos = pos.withX(pos.getX() + 1);
			break;
		case down:
			newPos = pos.withY(pos.getY() + 1);
			break;
		case left:
			newPos = pos.withX(pos.getX() - 1);
			break;
		}
		setPosition(newPos);
	}
	
	private Direction direction = Direction.down;

}
