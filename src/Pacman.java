import java.awt.event.KeyEvent;

public class Pacman extends Entity {
	private static final int SPEED = 10;

	public Pacman(GameWorld world) {
		super(world);
		setPosition(new MazePos(9, 1));
	}

	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visitPacman(this);
	}

	@Override
	public void tick() {
		if (world.ticks() % SPEED == 0) {
			move();
			eat();
		}
		Ghost gh = touchingGhost();
		if (world.isInBlueGhostMode() && gh != null) gh.kill();
	}
	
	public Ghost touchingGhost() {
		for (Ghost g: world.ghosts()) {
			if (g.getPosition().equals(getPosition())) {
				return g;
			}
		}
		return null;
	}
	
	private void eat() {
		world.mazeTileAt(getPosition()).eatItem();
	}

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

	public boolean isDead() {
		return !world.isInBlueGhostMode() && touchingGhost() != null && !touchingGhost().isDead();
	}

}
