package Pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacmanChrachtersMove extends DrawingVisitor {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Coordinate Positions;
 double PositionX;
 double PostionY;
 Direction Direction;
 private int PacmanSpeed = 6;   
 private int GhostSpeed;
 private int Speeds[]= {1,2,3,4,5,6};
 private int CurrentSpeed= 3;
 private short [] screendata;
 

 private int [] Ghostx;
 private int [] Ghosty;

 class TAdapter extends KeyAdapter{
	 private int req_x;
	 private int req_y;

	public void keypressed(KeyEvent e) {
	 int key = e.getKeyCode();
	 boolean Game = false;
	 if(Game) {
		if (key== KeyEvent.VK_LEFT) {
		setReq_x(-1);
		req_x=-1;
		req_y=0;
		}
		if (key== KeyEvent.VK_RIGHT) {
			setReq_x(-1);
			req_x=1;
			req_y=0;
		}
		if (key== KeyEvent.VK_UP) {
			
			req_x=0;
			req_y=-1;
		}
		if (key== KeyEvent.VK_DOWN) {
			
			req_x=0;
			req_y=1;
		}
		
			
			
		
	}
		 
	 }

	public int getReq_x() {
		return req_x;
	}

	public void setReq_x(int req_x) {
		this.req_x = req_x;
	}

	public int getReq_y() {
		return req_y;
	}

	public void setReq_y(int req_y) {
		this.req_y = req_y;
	}
	 
 }
 
 
 public void PacmanChrachtersMove(Coordinate Positions, double PositionX, double PostionY) {
}
 
 
 
public double getPositionX() {
	return PositionX;
}



public void setPositionX(double positionX) {
	PositionX = positionX;
}



public double getPostionY() {
	return PostionY;
}



public void setPostionY(double postionY) {
	PostionY = postionY;
}



public Direction getDirection() {
	return Direction;
}



public void setDirection(Direction direction) {
	Direction = direction;
}



public Coordinate getPositions() {
	return Positions;
}

public void setPositions(Coordinate positions) {
	Positions = positions;
}



public int getPacmanSpeed() {
	return PacmanSpeed;
}



public void setPacmanSpeed(int pacmanSpeed) {
	PacmanSpeed = pacmanSpeed;
}



public int getGhostSpeed() {
	return GhostSpeed;
}



public void setGhostSpeed(int ghostSpeed) {
	GhostSpeed = ghostSpeed;
}



public int[] getSpeeds() {
	return Speeds;
}



public void setSpeeds(int speeds[]) {
	Speeds = speeds;
}



public int getCurrentSpeed() {
	return CurrentSpeed;
}



public void setCurrentSpeed(int currentSpeed) {
	CurrentSpeed = currentSpeed;
}



public short [] getScreendata() {
	return screendata;
}



public void setScreendata(short [] screendata) {
	this.screendata = screendata;
}



public int [] getGhostx() {
	return Ghostx;
}



public void setGhostx(int [] ghostx) {
	Ghostx = ghostx;
}



public int [] getGhosty() {
	return Ghosty;
}



public void setGhosty(int [] ghosty) {
	Ghosty = ghosty;
}



 

}
