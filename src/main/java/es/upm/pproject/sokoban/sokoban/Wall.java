package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;

public class Wall extends Objetos implements Serializable{
	private static final long serialVersionUID = 8L;

	// Constructor
	public Wall(int x, int y, Image image) {
        super(x, y, image);
    }
	

}
