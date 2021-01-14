import java.awt.event.KeyEvent;

public class Pacman extends Entity {
	private static final int SPEED = 10;

	public Pacman(GameWorld world) {
		super(world);
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitPacman(this);
	}

	@Override
	public void tick() {
		if (world.ticks() % (GameWorld.NANOSECONDS_PER_TICK / SPEED) == 0) {
			move();
			eat();
		}
	}
	
	public void changeDirection(Direction dir) {
		direction = dir;
	}
	
	private void move() {
		MazePos pos = getPosition();
		if (world.mazeTileAt(pos).hasWall(direction) || world.mazeTileAt(pos.move(direction, 1)).hasWall(direction.inverse())) {
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
	
	private void eat() {
		world.mazeTileAt(getPosition()).eatItem();
	}
	
	private Direction direction = Direction.down;

	public void handleInput(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			changeDirection(Direction.up);
			break;
		case KeyEvent.VK_RIGHT:
			changeDirection(Direction.right);
			break;
		case KeyEvent.VK_DOWN:
			changeDirection(Direction.down);
			break;
		case KeyEvent.VK_LEFT:
			changeDirection(Direction.left);
			break;
		}
	}

	public Direction getDirection() {
		return direction;
	}

}
