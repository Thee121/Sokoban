package es.upm.pproject.sokoban.sokoban;

public class Board{
	
    private Object[][] grid; // Matrix representing the game board

    // Constructor to initialize the board with the given dimensions
    public Board(int rows, int cols) {
        this.grid = new Object[rows][cols];
        // Initialize the grid with empty cells
        initialize();
    }

    // Method to initialize the grid with empty cells
    private void initialize() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = null; // Create a new empty cell
            }
        }
    }
    
    public Object[][] getGrid() {
    	return this.grid;
    }

    // Method to place an object on the board at the specified position
    public void placeObject(int row, int col, Object object) {
        grid[row][col] = object;
    }

    // Method to retrieve the object at the specified position on the board
    public Object getObject(int row, int col) {
        return grid[row][col];
    }

    // Method to get the number of rows in the board
    public int getNumRows() {
        return grid.length;
    }

    // Method to get the number of columns in the board
    public int getNumCols() {
        return grid[0].length;
    }
}
