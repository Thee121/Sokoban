package es.upm.pproject.sokoban.sokoban;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class View extends JPanel  implements Serializable{
	private Model model;
	private static final long serialVersionUID = 9L;
	private JLabel movLabel;
	private JPanel movPanel;
	private JPanel topPanel;
	private JLabel levelLabel;
	private JPanel levelPanel;
	private JButton undoButton;
	private JButton restartButton;
	private JButton optionsButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton restartGameButton;
	private JButton exitGameButton;

	public View(Model model) {
		this.model = model;

		// Configurar el panel
		setFocusable(true);	    
		setLayout(new BorderLayout());

		// Crear el panel principal para el marcador de nivel y el botón de reinicio
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());


		// Panel para el marcador de nivel
		levelPanel = new JPanel();
		levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
		String niv = "Nivel: " + model.getCurrentLevel();
		levelLabel = new JLabel(niv);
		levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
		levelPanel.add(levelLabel);
		topPanel.add(levelPanel, BorderLayout.WEST);

		// Panel para el contador de movimientos
		movPanel = new JPanel();
		movPanel.setLayout(new BorderLayout());
		JPanel movAuxPanel = new JPanel();
		movAuxPanel.setLayout(new BoxLayout(movAuxPanel, BoxLayout.Y_AXIS));
		String movs = Integer.toString(model.getMoveCount()) + "     " + Integer.toString(model.getGlobalCount());
		movLabel = new JLabel(movs);
		movLabel.setFont(new Font("Arial", Font.BOLD, 20));
		movAuxPanel.add(movLabel);
		movPanel.add(movAuxPanel, BorderLayout.WEST);


		// Panel para el botón de undo
		JPanel undoPanel = new JPanel();
		undoButton = new JButton("Undo");
		undoButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
		undoPanel.add(undoButton);
		topPanel.add(undoPanel, BorderLayout.CENTER);

		// Panel para el botón de reinicio
		JPanel restartPanel = new JPanel();
		restartButton = new JButton("Restart");
		restartButton.setAlignmentX(Component.CENTER_ALIGNMENT); 

		restartPanel.add(restartButton);
		topPanel.add(restartPanel, BorderLayout.EAST);

		JPanel optionsButtonPanel = new JPanel();
		optionsButton = new JButton("Opciones");		
		saveButton = new JButton("Guardar partida");				
		loadButton = new JButton("Cargar partida");				
		restartGameButton = new JButton("Reiniciar juego");
		exitGameButton = new JButton("Salir del juego");
		exitGameButton.setBackground(Color.RED);
				
				
		optionsButtonPanel.add(optionsButton);
		movPanel.add(optionsButtonPanel, BorderLayout.EAST);
		topPanel.add(movPanel, BorderLayout.SOUTH);

		add(topPanel, BorderLayout.NORTH);
		setVisible(true);
		
	}


	@SuppressWarnings("static-access")
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Objetos o : model.getGameObjects()) {
			if (o instanceof Floor) {
				o.draw(g, model.TILE_SIZE);
			}
		}
		for (Objetos o : model.getGameObjects()) {
			if (!(o instanceof Floor)) {
				o.draw(g, model.TILE_SIZE);
			}
		}
		if (model.getPlayer() != null) {
			model.getPlayer().draw(g, model.TILE_SIZE);
		}
	}

	public void actuCont() {
		String movs1 = Integer.toString(model.getMoveCount()) + "     " + Integer.toString(model.getGlobalCount());
		movLabel.setText(movs1);
		movPanel.add(movLabel);
		topPanel.add(movPanel, BorderLayout.SOUTH);

	}

	public void resetCount() {
		model.setMoveCount(0);
	}

	public void resetGlobalCount() {
		model.setGlobalContador(0);
	}
	
	public JButton getUndoButton() {
        return undoButton;
    }
	
	public JButton getRestartButton() {
        return restartButton;
    }
	
	public JButton getOptionsButton() {
		return optionsButton;
	}
	
	public JButton getSaveButton() {
		return saveButton;
	}
	
	public JButton getLoadButton() {
		return loadButton;
	}
	
	public JButton getRestartGameButton() {
		return restartGameButton;
	}
	
	public JButton getExitGameButton() {
        return exitGameButton;
    }

	public void actuLevel() {
		String niv = "Nivel: " + model.getCurrentLevel();
		levelLabel.setText(niv);
		levelPanel.add(levelLabel);
		topPanel.add(levelPanel, BorderLayout.WEST);

	}

}
