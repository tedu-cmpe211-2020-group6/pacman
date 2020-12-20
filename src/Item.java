
public enum Item {
	pellet, powerPellet, cherry, strawberry,orange, apple, melon, galaxian, bell, key;
	
	public int pointValue() {
		switch (this) {
		case apple:
			return 700;
		case bell:
			return 3000;
		case cherry:
			return 100;
		case galaxian:
			return 2000;
		case key:
			return 5000;
		case melon:
			return 1000;
		case orange:
			return 500;
		case pellet:
			return 10;
		case powerPellet:
			return 50;
		case strawberry:
			return 300;
		default:
			return 0;
		}
	}
}
