package es.upm.pproject.sokoban.sokoban;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.*;

import org.junit.jupiter.api.Test;


import javax.swing.JOptionPane;



import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppTest {

	private static final Logger logger = LoggerFactory.getLogger(AppTest.class);
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

	// Pruebas para clase main App.java
	@SuppressWarnings("static-access")
	@Test
	void testMain() {
		PrintStream old = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		try {
			App app = new App();
			app.main(new String[0]);
			System.out.flush();
			System.setOut(old);
			String message = new String(baos.toByteArray(), Charset.defaultCharset());
			if (message.isEmpty()) {
				assertTrue(true);
			} else {
				assertFalse(false);
			}
			App.frame.dispose();
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al probar la clase main");
			logger.error("App main class testing failure: " + e.getMessage());
		}
	}

	// PRUEBAS FUNCION LOADIMAGE
	@Test
	void testLoadImageTrue() throws Exception {
		try {
			Image testImage = Model.loadImage(playerDownDirectory);
			if (testImage != null) {
				assertTrue(testImage instanceof Image);

			}
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al cargar una imagen");
			logger.error("IllegalArgument raised for loading an image: " + e.getMessage());
		}
	}

	@Test
	void testLoadImageFalse() throws Exception {
		try {
			Image testImage = Model.loadImage("notARealDirectory");
			assertNull(testImage);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al cargar una imagen");
			logger.error("IllegalArgument raised for loading an image: " + e.getMessage());
		}
	}

	Image playerImage = Model.loadImage(playerDownDirectory);
	Image boxImage = Model.loadImage(boxDirectory);
	Image wallImage = Model.loadImage(wallDirectory);
	Image floorImage = Model.loadImage(floorDirectory);
	Image goalImage = Model.loadImage(goalDirectory);

	// PRUEBAS PARA CLASE Board
	Board board = new Board(8, 8);

	@Test
	void testCreateBoardSuccessful() {
		try {
			Object[][] actualBoard = board.getGrid();
			for (int i = 0; i < actualBoard.length; i++) {
				for (int j = 0; j < actualBoard[i].length; j++) {
					actualBoard[i][j] = null; // Create a new empty cell
				}
			}
			Boolean check = true;
			for (int i = 0; i < actualBoard.length; i++) {
				for (int j = 0; j < actualBoard[i].length; j++) {
					if (board.getObject(j, i) != actualBoard[i][j]) {
						check = false;
					}
				}
			}
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear un board nuevo");
			logger.error("board creation failed: " + e.getMessage());
		}
	}

	@Test
	void testBoardPlaceObject() {
		try {
			Wall wallTest52 = new Wall(1, 1, wallImage);
			board.placeObject(1, 1, wallTest52);
			assertEquals(board.getObject(1, 1), wallTest52);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar si se puede insertar objeto en board");
			logger.error("Exception raised for placing object in board: " + e.getMessage());
		}
	}

	@Test
	void testBoardCheckColumns() {
		try {
			int columns = board.getNumCols();
			assertEquals(8, columns);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el número de columnas");
			logger.error("Exception raised for checking number of columns: " + e.getMessage());
		}
	}

	@Test
	void testBoardCheckRows() {
		try {
			int row = board.getNumRows();
			assertEquals(8, row);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el número de filas");
			logger.error("Exception raised for checking number of rows: " + e.getMessage());
		}
	}

	// PRUEBAS PARA CLASE BOX
	@Test
	void testCreateBoxSuccess1() {
		try {
			Box box = new Box(0, 0, null);
			box.setX(20);
			box.setY(10);
			box.setImage(boxImage);
			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean allCorrect = false;

			if (box.getImage() == boxImage) {
				imageCorrect = true;
			}
			if (box.getX() == 20) {
				xCorrect = true;
			}
			if (box.getY() == 10) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear una caja");
			logger.error("Box creation failed: " + e.getMessage());
		}
	}

	@Test
	void testCreateBoxSuccess2() {
		try {
			Box box = new Box(0, 0, true, null);
			box.setX(20);
			box.setY(10);
			box.setImage(boxImage);
			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			Boolean isInGoalCorrect = false;
			boolean allCorrect = false;

			isInGoalCorrect = box.isInGoal();

			if (box.getImage() == boxImage) {
				imageCorrect = true;
			}
			if (box.getX() == 20) {
				xCorrect = true;
			}
			if (box.getY() == 10) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect && isInGoalCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear una caja");
			logger.error("Box creation failed: " + e.getMessage());
		}
	}

	@Test
	void testCreateBoxSuccessInGoal() {
		try {
			Box box = new Box(20, 10, boxImage);

			box.setInGoal(false);
			assertEquals(false, box.isInGoal());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al insertar el componente IsInGoal de una caja");
			logger.error("Component isInGoal insertation of box failed: " + e.getMessage());
		}
	}

	@Test
	void testBoxsetMoveHistory() {
		Deque<int[]> moveHistory = new ArrayDeque<>();
		try {
			Box box = new Box(0, 0, null);
			box.setMoveHistory(moveHistory);
			Deque<int[]> testmoveHistory = box.getMoveHistory();
			assertEquals(moveHistory, testmoveHistory);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al insertar el componente moveHistory de una caja");
			logger.error("Component moveHistory insertation of box failed: " + e.getMessage());
		}
	}

	// PRUEBAS PARA CLASE Floor
	@Test
	void testCreateFloorSuccess() {
		try {
			Floor floor = new Floor(0, 0, null);
			floor.setImage(floorImage);
			floor.setX(10);
			floor.setY(25);
			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean allCorrect = false;

			if (floor.getImage() == floorImage) {
				imageCorrect = true;
			}
			if (floor.getX() == 10) {
				xCorrect = true;
			}
			if (floor.getY() == 25) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear un floor");
			logger.error("Creation of floor failed: " + e.getMessage());
		}
	}

	// PRUEBAS PARA CLASE GOAL
	@Test
	void testCreateGoalSuccess() {
		try {
			Goal goal = new Goal(0, 0, null);
			goal.setX(5);
			goal.setY(20);
			goal.setImage(goalImage);
			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean allCorrect = false;

			if (goal.getImage() == goalImage) {
				imageCorrect = true;
			}
			if (goal.getX() == 5) {
				xCorrect = true;
			}
			if (goal.getY() == 20) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear un objetivo");
			logger.error("Creation of goal failed: " + e.getMessage());
		}
	}

	Goal goalTest = new Goal(5, 20, goalImage);
	Box boxGoalTests = new Box(0, 0, boxImage);

	@Test
	void testCreateGoalsetBox() {
		try {
			Goal goalTest = new Goal(5, 20, goalImage);
			goalTest.setBox(boxGoalTests);
			assertEquals(goalTest.getBox(), boxGoalTests);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción  al insertar la caja en el Goal");
			logger.error("Box insertation in goal failed: " + e.getMessage());
		}
	}

	@Test
	void testBoxGoalisEmptyTrue() {
		try {
			assertTrue(goalTest.isEmpty());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar si el goal estuviese vacía");
			logger.error("Exception in isEmpty method of Goal: " + e.getMessage());
		}
	}

	@Test
	void testBoxGoalisEmptyFalse() {
		goalTest.setBox(boxGoalTests);
		try {
			assertFalse(goalTest.isEmpty());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar si el goal estuviese vacía");
			logger.error("Exception in isEmpty method of Goal: " + e.getMessage());
		}
	}

	// PRUEBAS PARA CLASE OBJETOS
	@Test
	void testNullPointerExceptionFailure() {
		try {
			Box box = new Box(0, 0, null);
			JFrame auxFrame = new JFrame();
			Graphics graphics = auxFrame.getGraphics();
			box.draw(graphics, 2);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un error al realizar la prueba de tiro de excepciones");
			logger.error("Object throws NullPointer test failed: " + e.getMessage());
		} catch (NullPointerException e) {
			String message = "image of object to paint cannot be Null";
			if (e.getMessage().equals(message)) {
				assertTrue(true);
			} else {
				assertFalse(false);
			}
		}
	}

	// PRUEBAS PARA CLASE PLAYER
	@Test
	void testCreatePlayerSuccess1() {
		try {
			Player player = new Player(0, 0, null);
			player.setImage(playerImage);
			player.setX(10);
			player.setY(20);
			Player playerFull = new Player(10, 20, playerImage);

			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean allCorrect = false;

			if (player.getImage() == playerFull.getImage()) {
				imageCorrect = true;
			}
			if (player.getX() == playerFull.getX()) {
				xCorrect = true;
			}
			if (player.getY() == playerFull.getY()) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al un jugador");
			logger.error("Creation of player failed: " + e.getMessage());
		}
	}

	@Test
	void testCreatePlayerSuccess2() {
		try {
			Player player = new Player(0, 0, 0, null);
			player.setImage(playerImage);
			player.setX(10);
			player.setY(20);
			player.setDirection(1);
			Player playerFull = new Player(10, 20, 1, playerImage);

			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean directionCorrect = false;
			boolean allCorrect = false;

			if (player.getImage() == playerFull.getImage()) {
				imageCorrect = true;
			}
			if (player.getX() == playerFull.getX()) {
				xCorrect = true;
			}
			if (player.getY() == playerFull.getY()) {
				yCorrect = true;
			}
			if (player.getDirection() == playerFull.getDirection()) {
				directionCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect && directionCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al un jugador");
			logger.error("Creation of player failed: " + e.getMessage());
		}
	}

	@Test
	void testPlayersetMoveHistory() {
		Deque<int[]> moveHistory = new ArrayDeque<>();
		try {
			Player player = new Player(0, 0, null);
			player.setMoveHistory(moveHistory);
			Deque<int[]> testmoveHistory = player.getMoveHistory();
			assertEquals(moveHistory, testmoveHistory);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al insertar el componente moveHistory de una caja");
			logger.error("Component moveHistory insertation of box failed: " + e.getMessage());
		}
	}

	// PRUEBAS PARA CLASE WALL
	@Test
	void testCreateWallSuccess() {
		try {
			Wall wall = new Wall(0, 0, null);
			wall.setImage(wallImage);
			wall.setX(20);
			wall.setY(40);
			boolean xCorrect = false;
			boolean yCorrect = false;
			boolean imageCorrect = false;
			boolean allCorrect = false;

			if (wall.getImage() == wallImage) {
				imageCorrect = true;
			}
			if (wall.getX() == 20) {
				xCorrect = true;
			}
			if (wall.getY() == 40) {
				yCorrect = true;
			}
			if (imageCorrect && xCorrect && yCorrect) {
				allCorrect = true;
			}
			assertTrue(allCorrect);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al crear una pared");
			logger.error("Creation of wall failed: " + e.getMessage());
		}
	}

	// Prueba FUNCION loadLevel
	@Test
	void testSetloadLevelthrowsException() throws NullPointerException {
		try {
			Model model = new Model();
			model.loadLevel("DirectorioNoExistente");
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar si tiraba la excepcion loadLevel");
			logger.error("Failure while trying to check if it throws NullPointer correctly: " + e.getMessage());
		} catch (NullPointerException e) {
			String message = "level not correct";
			assertEquals(message, e.getMessage());
		}
	}
	

	// PRUEBAS FUNCIONES "Setters" y "Getters"
	@Test
	void testSetCurrentLevel() {
		try {
			Model model = new Model();
			model.setCurrentLevel(2);
			assertEquals(2, model.getCurrentLevel());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto del setter CurrentLevel");
			logger.error("Failure while trying to set CurrentLevel: " + e.getMessage());
		}
	}

	@Test
	void testSetGetContador() {
		try {
			Model model = new Model();
			model.setMoveCount(10);
			assertEquals(10, model.getMoveCount());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto del setter CurrentLevel");
			logger.error("Failure while trying to set CurrentLevel: " + e.getMessage());
		}
	}

	@Test
	void testSetGetGlobalContador() {
		try {
			Model model = new Model();
			model.setGlobalContador(10);
			assertEquals(10, model.getGlobalCount());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto del setter CurrentLevel");
			logger.error("Failure while trying to set CurrentLevel: " + e.getMessage());
		}
	}

	@Test
	void testSetPlayer() {
		try {
			Model model = new Model();
			Player player = new Player(0, 0, playerImage);
			model.setPlayer(player);
			assertEquals(player, model.getPlayer());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto del setter CurrentLevel");
			logger.error("Failure while trying to set CurrentLevel: " + e.getMessage());
		}
	}

	@Test
	void testSetGetUltCont() {
		try {
			Model model = new Model();
			model.setultCont(10);
			assertEquals(10, model.getultCont());
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto del setter CurrentLevel");
			logger.error("Failure while trying to set CurrentLevel: " + e.getMessage());
		}
	}

	// Prueba FUNCION getMaxLevel
	@Test
	void testgetMaxLevel() {
		try {
			Model model = new Model();
			int maxLevel = model.getMaxLevel();
			assertEquals(4, maxLevel);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto de getMaxLevel");
			logger.error("Failure while trying to check if it throws NullPointer correctly: " + e.getMessage());
		}
	}

	// Prueba FUNCION isMovable
	@Test
	void testIsMovableFalse1() {
		try {
			Model model = new Model();
			Box box = new Box(0, 0, null);
			gameObjects.add(box);
			model.setGameObjects(gameObjects);
			Boolean check = model.isMovable(0, 0);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isMovable()");
			logger.error("Failure while trying to test isMovable() " + e.getMessage());
		}
	}

	@Test
	void testIsMovableFalse2() {
		try {
			Model model = new Model();
			Wall wall = new Wall(0, 0, null);
			gameObjects.add(wall);
			model.setGameObjects(gameObjects);
			Boolean check = model.isMovable(0, 0);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isMovable()");
			logger.error("Failure while trying to test isMovable() " + e.getMessage());
		}
	}

	@Test
	void testIsMovableTrue1() {
		try {
			Model model = new Model();
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.isMovable(0, 0);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isMovable()");
			logger.error("Failure while trying to test isMovable() " + e.getMessage());
		}
	}

	@Test
	void testIsMovableTrue2() {
		try {
			Model model = new Model();
			Wall wall = new Wall(0, 0, null);
			gameObjects.add(wall);
			model.setGameObjects(gameObjects);
			Boolean check = model.isMovable(1, 0);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isPushable()");
			logger.error("Failure while trying to test isPushable() " + e.getMessage());
		}
	}

	@Test
	void testIsMovableTrue3() {
		try {
			Model model = new Model();
			Wall wall = new Wall(0, 0, null);
			gameObjects.add(wall);
			model.setGameObjects(gameObjects);
			Boolean check = model.isMovable(0, 1);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isPushable()");
			logger.error("Failure while trying to test isPushable() " + e.getMessage());
		}
	}

	// Prueba FUNCION isPushable()
	@Test
	void testIsPushableTrue1() {
		try {
			Model model = new Model();
			Box box = new Box(0, 0, null);
			gameObjects.add(box);
			model.setGameObjects(gameObjects);
			Boolean check = model.isPushable(0, 0, 1, 1);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isMovable()");
			logger.error("Failure while trying to test isMovable() " + e.getMessage());
		}
	}
	@Test
	void testIsPushableFalse1() {
		try {
			Model model = new Model();
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.isPushable(1, 0, 1, 1);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isPushable()");
			logger.error("Failure while trying to test isPushable() " + e.getMessage());
		}
	}
	@Test
	void testIsPushableFalse2() {
		try {
			Model model = new Model();
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.isPushable(0, 1, 1, 1);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isPushable()");
			logger.error("Failure while trying to test isPushable() " + e.getMessage());
		}
	}
	@Test
	void testIsPushableFalse3() {
		try {
			Model model = new Model();
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.isPushable(0, 0, 1, 1);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar isPushable()");
			logger.error("Failure while trying to test isPushable() " + e.getMessage());
		}
	}

	// PRUEBAS FUNCION "ReadCharacteresFromFile"
	String fileDirectory = path + "/src/main/resources/levels/Level_1.txt";
	List<char[]> caracteres = new ArrayList<>();

	@Test
	void testReadingFile() {
		List<char[]> resultadoCorrecto = new ArrayList<>();
		resultadoCorrecto.add("".toCharArray());
		resultadoCorrecto.add("".toCharArray());
		resultadoCorrecto.add("++++".toCharArray());
		resultadoCorrecto.add("+  +".toCharArray());
		resultadoCorrecto.add("+  +++++".toCharArray());
		resultadoCorrecto.add("+      +".toCharArray());
		resultadoCorrecto.add("++W*+# +".toCharArray());
		resultadoCorrecto.add("+   +  +".toCharArray());
		resultadoCorrecto.add("+   ++++".toCharArray());
		resultadoCorrecto.add("+++++".toCharArray());

		boolean equalCheck = true;

		try {
			caracteres = Model.readCharactersFromFile(fileDirectory);
			for (int i = 0; i < resultadoCorrecto.size(); i++) {
				char[] correcto = resultadoCorrecto.get(i);
				char[] resultado = caracteres.get(i);
				if (correcto.length != resultado.length) {
					equalCheck = false;
					break;
				}
				for (int j = 0; j < resultado.length; j++) {
					if (correcto[j] != resultado[j]) {
						equalCheck = false;
					}
				}
			}
			assertTrue(equalCheck);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba una excepción al comprobar el funcionamiento correcto de la lectura del archivo");
			logger.error("check for reading characters failed: " + e.getMessage());

		}
	}

	@Test
	void testReadingFileIOException() {
		String wrongDirectory = "wrongDirectory";
		try {
			caracteres = Model.readCharactersFromFile(wrongDirectory);
			assertNull(caracteres);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar el tiro de IOException");
			logger.error("IllegalArgument raised for checking for IOException: " + e.getMessage());

		}
	}

	// PRUEBAS FUNCION "getBoxAt()"
	ArrayList<Objetos> gameObjects = new ArrayList<>();

	@Test
	void testgetBoxAtNoBox() throws Exception {
		Model model = new Model();
		Player player = new Player(0, 0, null);
		gameObjects.add(player);
		model.setGameObjects(gameObjects);
		try {
			Objetos boxtest = model.getBoxAt(0, 0);
			Boolean testCheck = false;
			if (boxtest.getX() == -1 && boxtest.getY() == -1 && boxtest.getImage() == null) {
				testCheck = true;
			}
			assertTrue(testCheck);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testgetBoxAtWrongX() throws Exception {
		Model model = new Model();
		Player player = new Player(0, 0, null);
		Wall wall = new Wall(0, 1, null);

		gameObjects.add(player);
		gameObjects.add(wall);
		model.setGameObjects(gameObjects);
		try {
			Objetos boxtest = model.getBoxAt(2, 1);
			Boolean testCheck = false;
			if (boxtest.getX() == -1 && boxtest.getY() == -1 && boxtest.getImage() == null) {
				testCheck = true;
			}
			assertTrue(testCheck);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testgetBoxAtWrongY() throws Exception {
		Model model = new Model();
		Player player = new Player(0, 0, null);
		Wall wall = new Wall(0, 1, null);

		gameObjects.add(player);
		gameObjects.add(wall);
		model.setGameObjects(gameObjects);

		try {
			Objetos boxtest = model.getBoxAt(0, 2);
			Boolean testCheck = false;
			if (boxtest.getX() == -1 && boxtest.getY() == -1 && boxtest.getImage() == null) {
				testCheck = true;
			}
			assertTrue(testCheck);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testgetBoxAtCorrect() throws Exception {
		Model model = new Model();
		Box box = new Box(0, 0, null);
		gameObjects.add(box);
		model.setGameObjects(gameObjects);
		try {
			Objetos boxtest = model.getBoxAt(0, 0);
			Boolean testCheck = false;
			if (boxtest.getX() == 0 && boxtest.getY() == 0 && boxtest.getImage() == null) {
				testCheck = true;
			}
			assertTrue(testCheck);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	// PRUEBAS FUNCION "ValidezNivel()"

	@Test
	void testValidezNivel1() throws Exception {
		Model model = new Model();
		try {
			int numberPlayers = 2;
			int numberBoxes = 1;
			int numberGoals = 1;
			Boolean check = model.validezNivel(numberPlayers, numberBoxes, numberGoals, true);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testValidezNivel2() throws Exception {
		Model model = new Model();
		try {
			int numberPlayers = 1;
			int numberBoxes = 0;
			int numberGoals = 1;
			Boolean check = model.validezNivel(numberPlayers, numberBoxes, numberGoals, false);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testValidezNivel3() throws Exception {
		Model model = new Model();
		try {
			int numberPlayers = 1;
			int numberBoxes = 1;
			int numberGoals = 0;
			Boolean check = model.validezNivel(numberPlayers, numberBoxes, numberGoals, false);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}

	@Test
	void testValidezNivel4() throws Exception {
		Model model = new Model();
		try {
			int numberPlayers = 1;
			int numberBoxes = 2;
			int numberGoals = 1;
			Boolean check = model.validezNivel(numberPlayers, numberBoxes, numberGoals, false);
			assertFalse(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}
	// PRUEBAS FUNCION "MovePlayer()"

	@Test
	void testMovePlayerUp() {
		int keyPress = KeyEvent.VK_UP;
		Model model = new Model();
		try {
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.movePlayer(keyPress);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}
	@Test
	void testMovePlayerDown() {
		int keyPress = KeyEvent.VK_DOWN;
		Model model = new Model();
		try {
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.movePlayer(keyPress);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}
	@Test
	void testMovePlayerRight() {
		int keyPress = KeyEvent.VK_RIGHT;
		Model model = new Model();
		try {
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.movePlayer(keyPress);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}
	@Test
	void testMovePlayerLeft() {
		int keyPress = KeyEvent.VK_LEFT;
		Model model = new Model();
		try {
			Floor floor = new Floor(0, 0, null);
			gameObjects.add(floor);
			model.setGameObjects(gameObjects);
			Boolean check = model.movePlayer(keyPress);
			assertTrue(check);
		} catch (IllegalArgumentException e) {
			fail("No se esperaba un IllegalArgumentException al comprobar si no hay cajas");
			logger.error("IllegalArgument raised for checking no boxes: " + e.getMessage());
		}
	}
	// PRUEBAS FUNCION "isBoxOnGoal()"

	@Test
	void testIsBoxOnGoalTrue() {
	    Model model = new Model();
	    Box box = new Box(1, 1, null);
	    Goal goal = new Goal(1, 1, null);
	    
	    List<Objetos> gameObjects = new ArrayList<>();
	    gameObjects.add(box);
	    gameObjects.add(goal);
	    model.setGameObjects(gameObjects);

	    try {
	        boolean result = model.isBoxOnGoal(box);
	        assertTrue(result, "Se esperaba que la caja estuviera en el objetivo");
	        assertTrue(box.isInGoal(), "La caja debería estar marcada como dentro del objetivo");
	        assertNotNull(box.getImage(), "La imagen de la caja debería haberse actualizado");
	    } catch (IllegalArgumentException e) {
	        fail("No se esperaba una excepción al comprobar si la caja está en el objetivo: " + e.getMessage());
	        logger.error("Exception raised for checking if box is on goal: " + e.getMessage());
	    }
	}

	@Test
	void testIsBoxOnGoalFalse() {
	    Model model = new Model();
	    Box box = new Box(1, 1, null);
	    Goal goal = new Goal(2, 2, null);
	    
	    List<Objetos> gameObjects = new ArrayList<>();
	    gameObjects.add(box);
	    gameObjects.add(goal);
	    model.setGameObjects(gameObjects);

	    try {
	        boolean result = model.isBoxOnGoal(box);
	        assertFalse(result, "No se esperaba que la caja estuviera en el objetivo");
	        assertFalse(box.isInGoal(), "La caja no debería estar marcada como dentro del objetivo");
	        assertNull(box.getImage(), "La imagen de la caja no debería haberse actualizado");
	    } catch (IllegalArgumentException e) {
	        fail("No se esperaba una excepción al comprobar si la caja está en el objetivo: " + e.getMessage());
	        logger.error("Exception raised for checking if box is on goal: " + e.getMessage());
	    }
	}

	@Test
	void testIsBoxOnGoalNoGoals() {
	    Model model = new Model();
	    Box box = new Box(1, 1, null);
	    
	    List<Objetos> gameObjects = new ArrayList<>();
	    gameObjects.add(box);
	    model.setGameObjects(gameObjects);

	    try {
	        boolean result = model.isBoxOnGoal(box);
	        assertFalse(result, "No se esperaba que la caja estuviera en el objetivo sin objetivos en el juego");
	        assertFalse(box.isInGoal(), "La caja no debería estar marcada como dentro del objetivo");
	        assertNull(box.getImage(), "La imagen de la caja no debería haberse actualizado");
	    } catch (IllegalArgumentException e) {
	        fail("No se esperaba una excepción al comprobar si la caja está en el objetivo sin objetivos en el juego: " + e.getMessage());
	        logger.error("Exception raised for checking if box is on goal with no goals: " + e.getMessage());
	    }
	}

	@Test
	void testIsBoxOnGoalMultipleGoals() {
	    Model model = new Model();
	    Box box = new Box(1, 1, null);
	    Goal goal1 = new Goal(2, 2, null);
	    Goal goal2 = new Goal(1, 1, null);
	    
	    List<Objetos> gameObjects = new ArrayList<>();
	    gameObjects.add(box);
	    gameObjects.add(goal1);
	    gameObjects.add(goal2);
	    model.setGameObjects(gameObjects);

	    try {
	        boolean result = model.isBoxOnGoal(box);
	        assertTrue(result, "Se esperaba que la caja estuviera en uno de los objetivos");
	        assertTrue(box.isInGoal(), "La caja debería estar marcada como dentro del objetivo");
	        assertNotNull(box.getImage(), "La imagen de la caja debería haberse actualizado");
	    } catch (IllegalArgumentException e) {
	        fail("No se esperaba una excepción al comprobar si la caja está en uno de los objetivos: " + e.getMessage());
	        logger.error("Exception raised for checking if box is on one of the goals: " + e.getMessage());
	    }
	}
	// PRUEBAS FUNCION "loadGame()"

	

	@Test
	void testLoadGameFileNotFound() {
	    String filePath = "nonExistentFile.dat";
	    Model model = new Model();

	    try {
	        model.loadGame(filePath);
	    } catch (IllegalArgumentException e) {
	        assertTrue(e instanceof IllegalArgumentException, "Se esperaba una IOException");
	    }
	}

	@Test
	void testLoadGameInvalidClass() {
	    String filePath = "testInvalidClass.dat";

	    // Crear un archivo con un objeto de una clase diferente
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
	        oos.writeObject("Este no es un objeto del modelo");
	    } catch (IOException e) {
	        fail("Error al preparar el archivo de prueba: " + e.getMessage());
	    }

	    Model model = new Model();

	    try {
	        model.loadGame(filePath);
	    } catch (Exception e) {
	        assertTrue(e instanceof ClassNotFoundException || e instanceof ClassCastException, "Se esperaba una ClassNotFoundException o ClassCastException");
	    } finally {
	        // Eliminar el archivo de prueba
	        File file = new File(filePath);
	        if (file.exists()) {
	            file.delete();
	        }
	    }
	}
	// PRUEBAS FUNCION "updateGameObjectImages()"
	    @Test
	    void testUpdateGameObjectImages() {
	        Model model = new Model();
	        BufferedImage boxImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage wallImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage floorImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage goalImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

	        List<Objetos> gameObjects = new ArrayList<>();
	        Box box = new Box(0, 0, null);
	        Wall wall = new Wall(1, 1, null);
	        Floor floor = new Floor(2, 2, null);
	        Goal goal = new Goal(3, 3, null);

	        gameObjects.add(box);
	        gameObjects.add(wall);
	        gameObjects.add(floor);
	        gameObjects.add(goal);

	        model.setGameObjects(gameObjects);
	        
	        model.updateGameObjectImages(boxImage, wallImage, floorImage, goalImage);

	        assertEquals(boxImage, box.getImage(), "La imagen de la caja debería actualizarse correctamente");
	        assertEquals(wallImage, wall.getImage(), "La imagen de la pared debería actualizarse correctamente");
	        assertEquals(floorImage, floor.getImage(), "La imagen del piso debería actualizarse correctamente");
	        assertEquals(goalImage, goal.getImage(), "La imagen del objetivo debería actualizarse correctamente");
	    }

	    @Test
	    void testUpdateGameObjectImagesEmptyList() {
	        Model model = new Model();
	        BufferedImage boxImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage wallImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage floorImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	        BufferedImage goalImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

	        List<Objetos> gameObjects = new ArrayList<>();
	        model.setGameObjects(gameObjects);
	        
	        model.updateGameObjectImages(boxImage, wallImage, floorImage, goalImage);

	        assertTrue(model.getGameObjects().isEmpty(), "La lista de objetos del juego debería estar vacía");
	    }

	    @Test
	    void testUpdateGameObjectImagesNullImages() {
	        Model model = new Model();

	        List<Objetos> gameObjects = new ArrayList<>();
	        Box box = new Box(0, 0, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
	        Wall wall = new Wall(1, 1, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
	        Floor floor = new Floor(2, 2, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
	        Goal goal = new Goal(3, 3, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));

	        gameObjects.add(box);
	        gameObjects.add(wall);
	        gameObjects.add(floor);
	        gameObjects.add(goal);

	        model.setGameObjects(gameObjects);
	        
	        model.updateGameObjectImages(null, null, null, null);

	        assertNull(box.getImage(), "La imagen de la caja debería ser null");
	        assertNull(wall.getImage(), "La imagen de la pared debería ser null");
	        assertNull(floor.getImage(), "La imagen del piso debería ser null");
	        assertNull(goal.getImage(), "La imagen del objetivo debería ser null");
	    }
	
	 // PRUEBAS FUNCION "saveGame()"
	    @Test
	    void testSaveGame() {
	        String filePath = "testGame.dat"; // Archivo de prueba
	        Model model = new Model(); // Crear instancia del modelo

	        // Configuración del jugador (simulación de estado)
	        Player player = new Player(1, 1, 0, null); // Ejemplo de jugador con dirección 0
	        model.setPlayer(player);

	        // Simular la configuración de gameObjects (puedes ajustar según tu implementación real)
	        Floor floor = new Floor(0, 0, null);
	        model.getGameObjects().add(floor);

	        try {
	            // Llamar al método que queremos probar
	            model.saveGame(filePath);

	            // Verificar que el archivo se haya creado correctamente
	            File savedFile = new File(filePath);
	            assertTrue(savedFile.exists(), "El archivo guardado debería existir");

	            // Verificar que el archivo se pueda leer y contenga datos válidos
	            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
	                Model loadedModel = (Model) ois.readObject();

	                // Comparar los objetos cargados con los originales (por ejemplo, player y gameObjects)
	                assertEquals(player.getDirection(), loadedModel.getPlayer().getDirection(), "La dirección del jugador debería ser la misma");
	                assertEquals(model.getGameObjects().size(), loadedModel.getGameObjects().size(), "El número de objetos de juego debería ser el mismo");

	                // Otros casos de prueba según la estructura de tu modelo
	                // Puedes agregar más aserciones para verificar otros aspectos del modelo
	            } catch (ClassNotFoundException e) {
	                fail("Error al leer el objeto del archivo: " + e.getMessage());
	            }

	        } catch (IOException e) {
	            fail("Error al guardar el juego: " + e.getMessage());
	        } finally {
	            // Eliminar el archivo de prueba después de las pruebas
	            File savedFile = new File(filePath);
	            if (savedFile.exists()) {
	                savedFile.delete();
	            }
	        }
	    }
	    // PRUEBAS FUNCION "undoMove()"
	    @Test
	    void testUndoMove() {
	    	Model model = new Model(); // Crear instancia del modelo
	        // Configurar el juego manualmente para el caso de prueba
	        setUpGame(model);
	        
	        // Estado inicial antes del movimiento
	        List<Objetos> gameObjectsBefore = new ArrayList<>(model.getGameObjects());
	        Player playerBefore = model.getPlayer(); // Obtener referencia al jugador actual
	        int moveCountBefore = model.getMoveCount();
	        int globalCountBefore = model.getGlobalCount();

	        // Realizar un movimiento
	        boolean moveResult = model.movePlayer(KeyEvent.VK_UP);
	        assertTrue(moveResult, "El movimiento del jugador debería ser válido");

	        // Estado después del movimiento
	        List<Objetos> gameObjectsAfter = new ArrayList<>(model.getGameObjects());
	        Player playerAfter = model.getPlayer(); // Obtener referencia al jugador después del movimiento
	        int moveCountAfter = model.getMoveCount();
	        int globalCountAfter = model.getGlobalCount();

	        // Deshacer el movimiento
	        model.undoMove();

	        // Verificar que se reviertan correctamente los cambios
	        assertAll("Verificar reversión del movimiento",
	            () -> assertEquals(gameObjectsBefore, model.getGameObjects(), "Los objetos del juego no se han revertido correctamente"),
	            
	            () -> assertEquals(moveCountBefore, model.getMoveCount(), "El contador de movimientos no se ha revertido correctamente"),
	            () -> assertEquals(globalCountBefore, model.getGlobalCount(), "El contador global no se ha revertido correctamente")
	        );

	        // Verificar que el estado después de deshacer el movimiento coincida con el estado original
	        assertAll("Verificar estado después de deshacer el movimiento",
	            () -> assertEquals(gameObjectsBefore, gameObjectsAfter, "El estado de los objetos después del movimiento no coincide con el esperado"),
	            () -> assertEquals(playerBefore, playerAfter, "El estado del jugador después del movimiento no coincide con el esperado")
	          
	        );
	    }

	    // Método para configurar manualmente el estado del juego
	    private void setUpGame(Model model) {
	    	
	        List<Objetos> gameObjects = new ArrayList<>();
	        gameObjects.add(new Floor(1, 1, null));
	        gameObjects.add(new Box(2, 2, null));
	        gameObjects.add(new Wall(3, 3, null));
	        model.setGameObjects(gameObjects);

	        Player player = new Player(1, 2, 0, null); // Jugador en posición (1, 2) mirando hacia abajo
	        model.setPlayer(player);

	        model.setMoveCount(0);
	        model.setGlobalContador(0);
	    }
	    // PRUEBAS FUNCION "handleLevelCompletion()"
	    @Test
	    void testHandleLevelCompletion_NextLevel() {
	        Model model = new Model();
	        model.setGlobalContador(0); // Reiniciar contador global para cada prueba

	        int initialLevel = model.getCurrentLevel();
	        int initialGlobalCount = model.getGlobalCount();

	        model.handleLevelCompletion();

	        assertEquals(initialLevel + 1, model.getCurrentLevel(), "El nivel actual no se ha incrementado correctamente");
	        assertTrue(model.getCurrentLevel() > 1, "No se ha cargado correctamente el siguiente nivel");
	        assertEquals(initialGlobalCount + model.getMoveCount(), model.getGlobalCount(), "El contador global no se ha actualizado correctamente");
	    }

	    @Test
	    void testHandleLevelCompletion_LastLevel() {
	        Model model = new Model();
	        model.setGlobalContador(0); // Reiniciar contador global para cada prueba

	        // Establecer el nivel actual al nivel máximo para simular la finalización del último nivel
	        int maxLevel = model.getMaxLevel();
	        model.setCurrentLevel(maxLevel);

	        model.handleLevelCompletion();

	        // Verificar que se ha cerrado la ventana de la aplicación
	        assertEquals(4, model.getCurrentLevel(), "No se ha establecido correctamente el nivel actual a 0 después de completar el último nivel");
	        
	    }
	 // PRUEBAS FUNCION "LoadGame()"
	  
	    
	    @Test
	     void testLoadGame() {
	        // Configurar el modelo de prueba con datos y rutas simuladas
	        Model modelUnderTest = createTestModel();
	        String testFilePath = createTestModelFile(modelUnderTest);

	        try {
	            // Ejecutar loadGame() con el archivo temporal
	            modelUnderTest.loadGame(testFilePath);

	            // Verificar que los datos se hayan cargado correctamente
	            assertNotNull(modelUnderTest.getPlayer());
	            assertNotNull(modelUnderTest.getGameObjects());
	            assertEquals(0, modelUnderTest.getPlayer().getX()); // Ajustar según la estructura del jugador
	            // Verificar que las imágenes se hayan cargado correctamente
	            // Aquí podrías tener métodos de aserción específicos para imágenes

	        } catch (IllegalArgumentException e) {
	            fail("Error cargando el juego: " + e.getMessage());
	        }
	    }

	    private Model createTestModel() {
	        // Configurar el modelo de prueba con datos simulados
	        Model model = new Model();
	        model.setPlayer(new Player(0, 0, null)); // Ejemplo, ajustar según la estructura del constructor
	        // Puedes configurar más atributos del modelo según sea necesario para la prueba
	        return model;
	    }

	    private String createTestModelFile(Model model) {
	        String testFilePath = "testModel.ser"; // Archivo temporal para pruebas

	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
	            // Serializar el modelo de prueba en el archivo temporal
	            oos.writeObject(model);
	        } catch (IOException e) {
	            fail("Error creando archivo de prueba: " + e.getMessage());
	        }

	        return testFilePath;
	    }

	    private Image loadImage(String path) {
	        // Implementación simulada de loadImage para pruebas
	        // Aquí podrías cargar una imagen real o simular la carga de imagen de manera controlada
	        return null; // Devolver una imagen simulada
	    }
}