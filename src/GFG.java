
import java.util.*;

class GFG {
	int ROW = 9;
	int COL = 10;

	public GFG() {

	}

// A Data Structure for queue used in BFS
	static class queueNode {
		MazePos pt; // The cordinates of a cell
		int dist; // cell's distance of from the source

		public queueNode(MazePos pt, int dist) {
			this.pt = pt;
			this.dist = dist;
		}
	};

// check whether given cell (row, col) 
// is a valid cell or not.
	boolean isValid(int row, int col) {
		// return true if row number and
		// column number is in range
		return (row >= 0) && (col >= 0) && (row < ROW) && (col < COL);
	}

// These arrays are used to get row and column
// numbers of 4 neighbours of a given cell
	static int rowNum[] = { 1, -1, 0, 0 };
	static int colNum[] = { 0, 0, 1, -1 };

// function to find the shortest path between
// a given source cell to a destination cell.
	public int BFS(PathfindingTile mat[][], MazePos src, MazePos dest) {

		COL = mat[0].length;// mat.length;
		ROW = mat.length;// mat[0].length;
		// check source and destination cell
		// of the matrix have value 1

		boolean[][] visited = new boolean[ROW + 5][COL + 5];

		// Mark the source cell as visited
		visited[src.getX()][src.getY()] = true;

		// Create a queue for BFS
		Queue<queueNode> q = new LinkedList<>();

		// Distance of source cell is 0
		queueNode s = new queueNode(src, 0);
		q.add(s); // Enqueue source cell

		// Do a BFS starting from source cell
		while (!q.isEmpty()) {
			queueNode curr = q.peek();
			MazePos pt = curr.pt;

			// If we have reached the destination cell,
			// we are done
			if (pt.getX() == dest.getX() && pt.getY() == dest.getY())
				return curr.dist;

			// Otherwise dequeue the front cell
			// in the queue and enqueue
			// its adjacent cells
			q.remove();

			for (int i = 0; i < 4; i++) {
				int row = pt.getX() + rowNum[i];
				int col = pt.getY() + colNum[i];

				// if adjacent cell is valid, has path
				// and not visited yet, enqueue it.
				if (isValid(row, col)) {
					try {

						if (!mat[row][col].hasWall())

						{
							if (!visited[row][col]) {
								visited[row][col] = true;
								queueNode Adjcell = new queueNode(new MazePos(row, col), curr.dist + 1);
								q.add(Adjcell);
							}
						}
					} catch (Exception e) {
					}

					// mark cell as visited and enqueue it

				}
			}
		}

		// Return -1 if destination cannot be reached
		return -1;
	}

// Driver Code
//public static void main(String[] args) 
//{
//    int mat[][] = {{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
//                   { 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
//                   { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
//                   { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
//                   { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 },
//                   { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
//                   { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//                   { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
//                   { 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 }};
// 
//    Point source = new Point(0, 0);
//    Point dest = new Point(3, 4);
// 
//    int dist = BFS(mat, source, dest);
// 
//    if (dist != -1)
//        System.out.println("Shortest Path is " + dist);
//    else
//        System.out.println("Shortest Path doesn't exist");
//   }
}
