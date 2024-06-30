package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;

public class Goal extends Objetos implements Serializable{
	private static final long serialVersionUID = 5L;
	
    private Box box; // box placed on the goal position, null if empty

    // Constructor
    public Goal(int x, int y, Image image) {
        super(x, y, image);
    }
		//Goal constructor is empty so that we can access the objects
		//parameters through setters and getters
   
    
    // Method to check if the goal position is empty
    public boolean isEmpty() {
    	boolean empty = true;
    	
    	if(box != null) {
    		empty = false;
    	}
        return empty;
    }

    // Method to get the box placed on the goal position
    public Box getBox() {
        return box;
    }

    // Method to place a box on the goal position
    public void setBox(Box box) {
        this.box = box;
    }
}
