package es.upm.pproject.sokoban.sokoban;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class App {
    // Crear modelo, vista y controlador
    static Model model = new Model();
    static View view = new View(model);
    static Controller controller = new Controller(model, view);
    static JFrame frame = new JFrame("Sokoban");

    public static void main(String[] args) {

        controller.configureListeners();

        // Configurar la vista y controlador
        // Agregar la vista a un marco, establecer oyentes de eventos, etc.
        frame.setPreferredSize(new Dimension(650, 240));
        frame.setMaximumSize(new Dimension(920, 940));
        frame.setMinimumSize(new Dimension(660, 620));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        view.requestFocusInWindow();
    }
}
