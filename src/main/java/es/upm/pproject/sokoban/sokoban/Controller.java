package es.upm.pproject.sokoban.sokoban;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Controller {
	private Model model;
	private View view;
	private JFrame optionsFrame;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void configureListeners() {
		// Agregar un ActionListener al botón Undo
		view.getUndoButton().addActionListener(e -> {
		    if (model.getMoveCount() != 0) {
		        view.removeKeyListener(view.getKeyListeners()[0]);
		        model.undoMove();
		        addAKeyListener();
		    }
		    view.requestFocusInWindow();
		});

		// Agregar un ActionListener al botón Restart
		view.getRestartButton().addActionListener(e -> {
		    view.removeKeyListener(view.getKeyListeners()[0]);
		    String nivel = "/home/pproject/eclipse-workspace/sokoban/src/main/resources/levels/Level_"
		            + model.getCurrentLevel() + ".txt";
		    model.loadLevel(nivel);
		    model.setGlobalContador(model.getultCont());
		    addAKeyListener();

		    view.requestFocusInWindow();
		});

		// Configurar el botón de opciones
		view.getOptionsButton().addActionListener(e -> {
		    // Si la ventana de opciones ya está visible, no hacer nada
		    if (optionsFrame != null && optionsFrame.isShowing()) {
		        return;
		    }

		    // Crear y configurar la ventana de opciones si no existe
		    if (optionsFrame == null) {
		        optionsFrame = new JFrame("Opciones");
		        optionsFrame.setSize(300, 260);
		        optionsFrame.setLayout(new GridLayout(4, 1));

		        optionsFrame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		                view.requestFocusInWindow();
		            }
		        });

		        view.getSaveButton().addActionListener(saveEvent -> {
		            JFileChooser fileChooser = new JFileChooser();
		            int returnValue = fileChooser.showSaveDialog(null);
		            if (returnValue == JFileChooser.APPROVE_OPTION) {
		                String filePath = fileChooser.getSelectedFile().getPath();
		                model.saveGame(filePath);
		            }
		        	optionsFrame.dispose();
		        });

		        view.getLoadButton().addActionListener(loadEvent -> {
		            JFileChooser fileChooser = new JFileChooser();
		            int returnValue = fileChooser.showOpenDialog(null);
		            if (returnValue == JFileChooser.APPROVE_OPTION) {
		                String filePath = fileChooser.getSelectedFile().getPath();
		                model.loadGame(filePath);
		                view.actuCont();
		                view.actuLevel();
		                view.repaint();
		                view.requestFocusInWindow();
			        	optionsFrame.dispose();

		            }
		        });

		        view.getRestartGameButton().addActionListener(restartEvent -> {
		            view.removeKeyListener(view.getKeyListeners()[0]);
		            String nivel = "/home/pproject/eclipse-workspace/sokoban/src/main/resources/levels/Level_1.txt";
		            model.setCurrentLevel(1);
		            model.loadLevel(nivel);
		            model.setGlobalContador(0);
		            model.setultCont(0);
		            addAKeyListener();

		            view.requestFocusInWindow();
		            optionsFrame.dispose(); // Cerrar la ventana de opciones
		        });

		        view.getExitGameButton().addActionListener(exitEvent -> {
		        	App.frame.dispose();
		        	optionsFrame.dispose();
		    });

		        optionsFrame.add(view.getSaveButton());
		        optionsFrame.add(view.getLoadButton());
		        optionsFrame.add(view.getRestartGameButton());
		        optionsFrame.add(view.getExitGameButton());
		    }

		    // Mostrar la ventana de opciones
		    optionsFrame.setVisible(true);
		});

		// Agregar un KeyListener al JFrame (para mover al jugador)
		addAKeyThatListens();
	}

	private void addAKeyListener() {
		view.actuCont();
		view.repaint();
		addAKeyThatListens();
	}

	private void addAKeyThatListens() {
		view.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				model.movePlayer(e.getKeyCode());
				view.actuCont();
				view.repaint();
				model.checkLevelComplete();
				view.actuLevel();
				view.actuCont();
				view.repaint();
			}
		});

	}
}
