
public interface EntityVisitor {
	void visitPacman(Pacman p);
	void visitGhost(Ghost g);
	void visitMazeTile(MazeTile mt);
}
