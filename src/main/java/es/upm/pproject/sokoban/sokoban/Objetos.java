package es.upm.pproject.sokoban.sokoban;

import java.awt.Image;
import java.io.Serializable;
import java.awt.Graphics;

public class Objetos implements Serializable{
	private static final long serialVersionUID = 6L;
	
	protected int x;
    protected int y;
    protected transient Image image = null;
    
    
    protected Objetos(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Image getImage() {
    	return this.image;
    }
    
    public void setImage(Image i) {
    	this.image = i;
    }

    public boolean draw(Graphics g, int tileSize) throws NullPointerException{
    	Boolean done = false;
        if (image != null) {
            g.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
            done = true;
        }else {
        	throw new NullPointerException("image of object to paint cannot be Null");
        }
        return done;
    }
}

