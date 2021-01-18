
import java.util.Random;
import javax.swing.ImageIcon;

public class Clyde extends Ghost {

	private int rand = 0;

	public Clyde(GameWorld gw) {
		super(gw);
		setPosition(new MazePos(6, 7));
	}

	@Override
	Direction findPath() {
		GFG gz = new GFG();
		MazePos[] postions = new MazePos[4];
		int minindex = 0;
		int minimum = Integer.MAX_VALUE;
		int mindistance = minimum;
		MazePos pos = world.mazePosToPathfindingMaze(this.getPosition());
		MazePos pacmanpos = world.mazePosToPathfindingMaze(world.getPacman().getPosition());

		if (world.ticks() % 200 == 0) rand = new Random().nextInt(3);
		
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
				pacmanpos.setY(23);
			}

		}

		int random = new Random().nextInt(2);

		postions[0] = canMove(pos, Direction.right) ? pos.move(Direction.right, 2) : pos; // new MazePos(pos.getX() + 1, pos.getY());
		postions[1] = canMove(pos, Direction.left) ? pos.move(Direction.left, 2) : pos; // new MazePos(pos.getX() - 1, pos.getY());
		postions[2] = canMove(pos, Direction.down) ? pos.move(Direction.down, 2) : pos; // new MazePos(pos.getX(), pos.getY() + 1);
		postions[3] = canMove(pos, Direction.up) ? pos.move(Direction.up, 2) : pos; // new MazePos(pos.getX(), pos.getY() - 1);

		Direction rv = Direction.values()[rand];
		
		MazePos pacmanpostion = world.mazePosToPathfindingMaze(world.getPacman().getPosition());

		
		try {
			mindistance = gz.BFS(world.getPathfindingMaze(), postions[0], pacmanpostion);

			if (mindistance != -1 && minimum > mindistance) {
				minimum = mindistance;
				minindex = 0;
				rv = Direction.right;
			}
		} catch (Exception e) {
		}
		try {
			mindistance = gz.BFS(world.getPathfindingMaze(), postions[1], pacmanpostion);
			if (mindistance != -1 && minimum > mindistance) {
				minimum = mindistance;
				minindex = 1;
				rv = Direction.left;
			}
		} catch (Exception e) {
		}
		try {
			mindistance = gz.BFS(world.getPathfindingMaze(), postions[2], pacmanpostion);
			if (mindistance != -1 && minimum > mindistance) {
				minimum = mindistance;
				minindex = 2;
				rv = Direction.down;
			}
		} catch (Exception e) {
		}
		try {
			mindistance = gz.BFS(world.getPathfindingMaze(), postions[3], pacmanpostion);
			if (mindistance != -1 && minimum > mindistance) {
				minimum = mindistance;
				minindex = 3;
				rv = Direction.up;
			}
		} catch (Exception e) {
		}

		MazePos finalposition = new MazePos(postions[(minindex + random) % 4]);

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

		return rv;

	}


}
