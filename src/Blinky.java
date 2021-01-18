
import java.util.Random;
import javax.swing.ImageIcon;

public class Blinky extends Ghost {

	int rand = 0;

	public Blinky(GameWorld gw) {
		super(gw);
		setPosition(new MazePos(4, 7));
	}

	@Override
	MazePos findPath() {
		GFG gz = new GFG();
		MazePos[] postions = new MazePos[4];
		int minindex = 0;
		int minimum = Integer.MAX_VALUE;
		int mindistance = minimum;
		MazePos pos = world.mazePosToPathfindingMaze(this.getPosition());
		MazePos pacmanpos = world.mazePosToPathfindingMaze(new MazePos(world.getPacman().getPosition()));

		if (world.ticks() % 200 == 0)
			rand = new Random().nextInt(3);

		if (isInBlueMode()) {

			if (rand == 0) {
				pacmanpos.setX(1);
				pacmanpos.setY(1);
			}

			if (rand == 1) {
				pacmanpos.setX(16);
				pacmanpos.setY(20);
			}

			if (rand == 2) {
				pacmanpos.setX(22);
				pacmanpos.setY(26);
			}

		}

		postions[0] = canMove(pos, Direction.right) ? pos.move(Direction.right, 2) : pos; // new MazePos(pos.getX() + 1, pos.getY());
		postions[1] = canMove(pos, Direction.left) ? pos.move(Direction.left, 2) : pos; // new MazePos(pos.getX() - 1, pos.getY());
		postions[2] = canMove(pos, Direction.down) ? pos.move(Direction.down, 2) : pos; // new MazePos(pos.getX(), pos.getY() + 1);
		postions[3] = canMove(pos, Direction.up) ? pos.move(Direction.up, 2) : pos; // new MazePos(pos.getX(), pos.getY() - 1);

		mindistance = gz.BFS(world.getPathfindingMaze(), postions[0], pacmanpos);
		if (mindistance != -1 && minimum > mindistance) {
			minimum = mindistance;
			minindex = 0;
		}
		mindistance = gz.BFS(world.getPathfindingMaze(), postions[1], pacmanpos);
		if (mindistance != -1 && minimum > mindistance) {
			minimum = mindistance;
			minindex = 1;
		}
		mindistance = gz.BFS(world.getPathfindingMaze(), postions[2], pacmanpos);
		if (mindistance != -1 && minimum > mindistance) {
			minimum = mindistance;
			minindex = 2;
		}

		mindistance = gz.BFS(world.getPathfindingMaze(), postions[3], pacmanpos);
		if (mindistance != -1 && minimum > mindistance) {
			minimum = mindistance;
			minindex = 3;
		}

		// this.setPosition(new MazePos(postions[minindex]));

		MazePos finalposition = new MazePos(postions[minindex]);

		if (finalposition.getX() < 1) {
			finalposition.setX(1);
		}

		if (finalposition.getY() < 1) {
			finalposition.setY(1);
		}

		if (finalposition.getX() > world.getPathfindingMaze()[0].length - 2) {
			finalposition.setX(world.getPathfindingMaze()[0].length - 2);
		}

		if (finalposition.getY() > world.getPathfindingMaze().length - 2) {
			finalposition.setY(world.getPathfindingMaze().length - 2);
		}

		return finalposition;

	}

}
