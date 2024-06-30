package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;

public class Floor extends Objetos implements Serializable{
	private static final long serialVersionUID = 3L;
    public Floor(int x, int y, Image image) {
        super(x, y, image);
    }
}
