package es.upm.pproject.sokoban.sokoban;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Model.class);

	public static final int TILE_SIZE = 32;
	private Player player = new Player(0, 0, null);
	private List<Objetos> gameObjects = new ArrayList<>();
	private int moveCount;
	private int globalCount = 0;
	private int currentLevel;
	private int ultCont = 0;
	private List<List<Box>> historial = new ArrayList<>();
	private List<Player> playerHistorial = new ArrayList<>();

	// path = "/home/pproject/eclipse-workspace/sokoban"
	String path = new File("").getAbsolutePath();
	String playerDownDirectory = path + "/src/main/resources/images/playerDown.png";
	String playerUpDirectory = path + "/src/main/resources/images/playerUp.png";
	String playerLeftDirectory = path + "/src/main/resources/images/playerLeft.png";
	String playerRightDirectory = path + "/src/main/resources/images/playerRight.png";
	String boxDirectory = path + "/src/main/resources/images/Crate.png";
	String boxGoalDirectory = path + "/src/main/resources/images/crateGoal.png";
	String wallDirectory = path + "/src/main/resources/images/Wall.png";
	String floorDirectory = path + "/src/main/resources/images/Ground.png";
	String goalDirectory = path + "/src/main/resources/images/Goal.png";
	String file = "";

	// Constructor
	public Model() {
		this.currentLevel = 1;
		this.file = path + "/src/main/resources/levels/Level_1.txt";
		loadLevel(file);
	}

	
	public final void loadLevel(String filename) {
	    List<char[]> level = readCharactersFromFile(filename);
	    if (level == null) {
	        throw new NullPointerException("level not correct");
	    }
	    moveCount = 0;
	    gameObjects.clear();
	    Image playerImage = loadImage(playerDownDirectory);
		Image boxImage = loadImage(boxDirectory);
		Image wallImage = loadImage(wallDirectory);
		Image floorImage = loadImage(floorDirectory);
		Image goalImage = loadImage(goalDirectory);
		int numberOfPlayers = 0;
		int numberOfBoxes = 0;
		int numberOfGoals = 0;

		    for (int y = 2; y < level.size(); y++) {
		        for (int x = 0; x < level.get(y).length; x++) {
		            char tile = level.get(y)[x];
		            switch (tile) {
		                case 'W':
		                    numberOfPlayers++;
		                    initializePlayer(x, y,playerImage);
		                    gameObjects.add(new Floor(x, y, floorImage)); // Add floor under player
		                    break;
		                case '#':
		                    numberOfBoxes++;
		                    gameObjects.add(new Floor(x, y, floorImage));
		                    gameObjects.add(new Box(x, y, boxImage));
		                    break;
		                case '+':
		                    gameObjects.add(new Wall(x, y, wallImage));
		                    break;
		                case ' ':
		                    gameObjects.add(new Floor(x, y, floorImage));
		                    break;
		                case '*':
		                    numberOfGoals++;
		                    gameObjects.add(new Floor(x, y, floorImage));
		                    gameObjects.add(new Goal(x, y, goalImage));
		                    break;
		                default:
		                	break;
		            }
		        }
		    }
	    initializeHistorial(numberOfBoxes);
	    validezNivel(numberOfPlayers, numberOfBoxes, numberOfGoals, true);
	}



	

	private void initializePlayer(int x, int y,Image playerImage) {
	    player.setImage(playerImage);
	    player.setX(x);
	    player.setY(y);
	    player.setDirection(0);
	}

	private void initializeHistorial(int numberOfBoxes) {
	    for (int i = 0; i < numberOfBoxes; i++) {
	        historial.add(new ArrayList<>());
	    }
	}

	
	
	

	public boolean validezNivel(int numberOfP, int numberOfB, int numberOfG, boolean showMessage) {
		Boolean nivelCorrecto = true;
		if (numberOfP != 1 || numberOfB == 0 || numberOfG == 0 || numberOfB != numberOfG) {
			if (showMessage) {
				JOptionPane.showMessageDialog(null,
						"Error: The loaded level is incorrect. Trying to load the next level.");
			}
			currentLevel++;
			String messageFormat = path + "/src/main/resources/levels/Level_%d.txt";
			String nextLevelFilename = String.format(messageFormat, currentLevel);
			loadLevel(nextLevelFilename);
			nivelCorrecto = false;
		}
		return nivelCorrecto;
	}

	public static Image loadImage(String path) throws NullPointerException {
		Image image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			String message = "Reading of fle failed:  " + e.getMessage();
			logger.error(message);
		}
		return image;
	}

	public boolean movePlayer(int keyCode) {
	    boolean movedCorrectly = false;

	    Image playerImage = loadImage(playerDownDirectory);
		Image playerUp = loadImage(playerUpDirectory);
		Image playerLeft = loadImage(playerLeftDirectory);
		Image playerRight = loadImage(playerRightDirectory);
		
	    int dx = 0;
	    int dy = 0;

	    switch (keyCode) {
		case KeyEvent.VK_UP:
			if (player.getImage() != playerUp) {
				player.setImage(playerUp);
				player.setDirection(1);
			}
			dy = -1;
			break;
		case KeyEvent.VK_DOWN:
			if (player.getImage() != playerImage) {
				player.setImage(playerImage);
				player.setDirection(0);
			}
			dy = 1;
			break;
		case KeyEvent.VK_LEFT:
			if (player.getImage() != playerLeft) {
				player.setImage(playerLeft);
				player.setDirection(2);
			}
			dx = -1;
			break;
		case KeyEvent.VK_RIGHT:
			if (player.getImage() != playerRight) {
				player.setImage(playerRight);
				player.setDirection(3);
			}
			dx = 1;
			break;
		default:
			return false;
		}

	    int newX = player.getX() + dx;
	    int newY = player.getY() + dy;

	    if (isMovable(newX, newY)) {
	        recordHistory();
	        updatePlayerPosition(newX, newY);
	        movedCorrectly = true;
	    } else if (isPushable(newX, newY, dx, dy)) {
	        Box box = getBoxAt(newX, newY);

	        if (box.getX() != -1) {
	            recordHistory();
	            updateBoxAndPlayerPosition(box, newX, newY, dx, dy);
	            movedCorrectly = true;
	        }
	    }

	    return movedCorrectly;
	}


	private void recordHistory() {
	    int i = 0;
	    for (Objetos o : gameObjects) {
	        if (o instanceof Box) {
	            historial.get(i).add(new Box(o.getX(), o.getY(), ((Box) o).isInGoal(), o.getImage()));
	            i++;
	        }
	    }
	    playerHistorial.add(new Player(player.getX(), player.getY(), player.getDirection(), player.getImage()));
	}

	private void updatePlayerPosition(int newX, int newY) {
	    player.setX(newX);
	    player.setY(newY);
	    moveCount++;
	    globalCount++;
	}

	private void updateBoxAndPlayerPosition(Box box, int newX, int newY, int dx, int dy) {
	    box.setX(newX + dx);
	    box.setY(newY + dy);
	    updatePlayerPosition(newX, newY);
	}


	

	public boolean isMovable(int x, int y) {
		for (Objetos obj : gameObjects) {
			if (obj.getX() == x && obj.getY() == y && (obj instanceof Wall || obj instanceof Box)) {
				return false;
			}
		}
		return true;
	}

	public boolean isPushable(int x, int y, int dx, int dy) {
		for (Objetos obj : gameObjects) {
			if (obj.getX() == x && obj.getY() == y && obj instanceof Box) {
				int newX = x + dx;
				int newY = y + dy;
				return isMovable(newX, newY);
			}
		}
		return false;
	}

	public void undoMove() {
		if (!historial.isEmpty()) {
			int i = 0;
			for (Objetos o : gameObjects) {
				if (o instanceof Box) {
					Box b = historial.get(i).remove(historial.get(i).size() - 1);
					Box bo = getBoxAt(o.getX(), o.getY());
					bo.setX(b.getX());
					bo.setY(b.getY());
					bo.setImage(b.getImage());
					i++;
				}
			}
			player = playerHistorial.remove(playerHistorial.size() - 1);

			if (moveCount != 0) {
				moveCount--;
				globalCount--;
			}
		}
	}

	public static List<char[]> readCharactersFromFile(String fileName) {
		List<char[]> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line.toCharArray());
			}
		} catch (IOException e) {
			String message = "Reading of file failed:  " + e.getMessage();
			logger.error(message);
			result = null;
			return result;
		}
		return result;
	}

	public Box getBoxAt(int x, int y) {
		Box box = new Box(-1, -1, null);
		for (Objetos obj : gameObjects) {
			if (obj.getX() == x && obj.getY() == y && obj instanceof Box) {
				return (Box) obj;
			}
		}
		return box;
	}
	public boolean checkLevelComplete() {
	    boolean check = true;

	    for (Objetos o : gameObjects) {
	        if (o instanceof Box && !isBoxOnGoal((Box) o)) {
	            check = false;
	            
	        }
	    }

	    if (check) {
	        handleLevelCompletion();
	    }

	    return check;
	}

	public boolean isBoxOnGoal(Box box) {
	    for (Objetos goal : gameObjects) {
	        if (goal instanceof Goal && goal.getX() == box.getX() && goal.getY() == box.getY()) {
	            box.setInGoal(true);
	            box.setImage(loadImage(boxGoalDirectory));
	            return true;
	        }
	    }
	    return false;
	}

	public void handleLevelCompletion() {
	    ultCont += moveCount;

	    if (currentLevel < getMaxLevel()) {
	        JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el nivel " + currentLevel);
	        currentLevel++;
	        String messageFormat = path + "/src/main/resources/levels/Level_%d.txt";
	        String nextLevelFilename = String.format(messageFormat, currentLevel);
	        loadLevel(nextLevelFilename);
	    } else {
	        int totalScore = getGlobalCount();
	        JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado el último nivel.\nPuntuación total del juego: " + totalScore);
	        App.frame.dispose();
	    }
	}
	

	public void saveGame(String filePath) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(this); // Guarda el objeto completo, incluyendo player y gameObjects
			String message = "Juego guardado exitosamente en: " + filePath;
			logger.info(message);
		} catch (IOException e) {
			String message = "Error al guardar el juego: " + e.getMessage();
			logger.error(message);
		}
	}

	public void loadGame(String filePath) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			Model loadedModel = (Model) ois.readObject();
			this.player = loadedModel.player;
			this.moveCount = loadedModel.moveCount;
			this.globalCount = loadedModel.globalCount;
			this.currentLevel = loadedModel.currentLevel;
			this.historial = loadedModel.historial;
			this.playerHistorial = loadedModel.playerHistorial;
			this.gameObjects = loadedModel.getGameObjects();
			this.ultCont = loadedModel.ultCont;

			// Volver a cargar las imágenes después de deserializar
			reloadImages();
		} catch (IOException | ClassNotFoundException e) {
			String message = "Error al cargar el juego: " + e.getMessage();
			logger.error(message);
		}
	}

	private void reloadImages() {
		Image playerImage = loadImage(playerDownDirectory);
		Image playerUp = loadImage(playerUpDirectory);
		Image playerLeft = loadImage(playerLeftDirectory);
		Image playerRight = loadImage(playerRightDirectory);
		Image boxImage = loadImage(boxDirectory);
		Image boxOnGoalImage = loadImage(boxGoalDirectory);
		Image wallImage = loadImage(wallDirectory);
		Image floorImage = loadImage(floorDirectory);
		Image goalImage = loadImage(goalDirectory);

	    updateGameObjectImages(boxImage,wallImage,floorImage,goalImage);
	    updatePlayerHistorialImages(playerImage, playerUp, playerLeft, playerRight);
	    updateBoxHistorialImages( boxImage, boxOnGoalImage);
	    updatePlayerImage(playerImage, playerUp, playerLeft, playerRight);

	    checkLevelComplete();
	}


	public void updateGameObjectImages(Image boxImage,Image wallImage,Image floorImage,Image goalImage) {
	    for (Objetos obj : gameObjects) {
	        if (obj instanceof Box) {
	            obj.setImage(boxImage);
	        } else if (obj instanceof Wall) {
	            obj.setImage(wallImage);
	        } else if (obj instanceof Floor) {
	            obj.setImage(floorImage);
	        } else if (obj instanceof Goal) {
	            obj.setImage(goalImage);
	        }
	    }
	}

	public void updatePlayerHistorialImages(Image playerImage,Image playerUp,Image playerLeft,Image playerRight ) {
	    for (Player p : playerHistorial) {
	        switch (p.getDirection()) {
	            case 0:
	                p.setImage(playerImage);
	                break;
	            case 1:
	                p.setImage(playerUp);
	                break;
	            case 2:
	                p.setImage(playerLeft);
	                break;
	            case 3:
	                p.setImage(playerRight);
	                break;
	            default:
	    			break;
	        }
	    }
	}

	private void updateBoxHistorialImages(Image boxImage,Image boxOnGoalImage ) {
	    for (List<Box> l : historial) {
	        for (Box b : l) {
	            if (b.isInGoal()) {
	                b.setImage(boxOnGoalImage);
	            } else {
	                b.setImage(boxImage);
	            }
	        }
	    }
	}

	public void updatePlayerImage(Image playerImage,Image playerUp,Image playerLeft,Image playerRight) {
	    switch (player.getDirection()) {
	        case 0:
	            player.setImage(playerImage);
	            break;
	        case 1:
	            player.setImage(playerUp);
	            break;
	        case 2:
	            player.setImage(playerLeft);
	            break;
	        case 3:
	            player.setImage(playerRight);
	            break;
	        default:
    			break;
	    }
	}
	
	public int getMaxLevel() {
	    int maxLevel = 0;
	    String directoryPath = path + "/src/main/resources/levels/";

	    File directory = new File(directoryPath);
	    if (directory.exists() && directory.isDirectory()) {
	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File actualFile : files) {
	                maxLevel = processFile(actualFile, maxLevel);
	            }
	        }
	    }

	    return maxLevel;
	}

	private int processFile(File actualFile, int currentMaxLevel) {
	    if (isLevelFile(actualFile)) {
	        int levelNumber = extractLevelNumber(actualFile.getName());
	        if (levelNumber > currentMaxLevel) {
	            currentMaxLevel = levelNumber;
	        }
	    }
	    return currentMaxLevel;
	}

	private boolean isLevelFile(File file) {
	    return file.isFile() && file.getName().startsWith("Level_") && file.getName().endsWith(".txt");
	}

	private int extractLevelNumber(String fileName) {
	    String[] parts = fileName.split("_");
	    if (parts.length == 2) {
	        try {
	            return Integer.parseInt(parts[1].replace(".txt", ""));
	        } catch (NumberFormatException e) {
	        	String message = "Error al conseguir el nivel máximo: " + e.getMessage();
				logger.error(message);
	        }
	    }
	    return 0;
	}
	

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int contador) {
		this.moveCount = contador;
	}

	public int getGlobalCount() {
		return globalCount;
	}

	public void setGlobalContador(int contador) {
		this.globalCount = contador;
	}

	public List<Objetos> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(List<Objetos> objetosJuego) {
		this.gameObjects = objetosJuego;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player jugador) {
		this.player = jugador;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int level) {
		this.currentLevel = level;
	}

	public int getultCont() {
		return ultCont;
	}

	public void setultCont(int count) {
		this.ultCont = count;
	}

}
