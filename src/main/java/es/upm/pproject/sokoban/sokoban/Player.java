package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class Player extends Objetos implements Serializable{
	private static final long serialVersionUID = 7L;
	private Deque<int[]> moveHistory;
	
	// 0 = down, 1 = up, 2 = left, 3 = right
	private int direction;
	
	// Constructor
    public Player(int x, int y, Image image) {
        super(x, y, image);
        this.direction = 0;
        this.moveHistory = new ArrayDeque<>();
    }
    
    public Player(int x, int y, int direction, Image image) {
        super(x, y, image);
        this.direction = direction;
        this.moveHistory = new ArrayDeque<>();
    }
    
    public Deque<int[]> getMoveHistory() {
		return moveHistory;
	}
    
	public void setMoveHistory(Deque<int[]> moveHistory) {
		this.moveHistory = moveHistory;
	}
	
	public void setDirection(int dir) {
		this.direction = dir;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
}
