package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class Box extends Objetos implements Serializable{
	private static final long serialVersionUID = 2L;
    private boolean inGoal; // whether the box is in a goal position or not
    private Deque<int[]> moveHistory;

    // Constructor
    public Box(int x, int y, Image image) {
        super(x, y, image);
        this.moveHistory = new ArrayDeque<>();
    }
    
    public Box(int x, int y, boolean inGoal, Image image) {
        super(x, y, image);
        this.inGoal = inGoal;
        this.moveHistory = new ArrayDeque<>();
    }

    // Method to check if the box is in a goal position
    public boolean isInGoal() {
        return inGoal;
    }

    // Method to set the box in a goal position
    public void setInGoal(boolean inGoal) {
        this.inGoal = inGoal;
    }
    
    public Deque<int[]> getMoveHistory() {
		return moveHistory;
	}
    
	public void setMoveHistory(Deque<int[]> moveHistory) {
		this.moveHistory = moveHistory;
	}
}
