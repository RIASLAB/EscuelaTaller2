package Vista;


import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Principal() {
        setTitle("Sistema de Gestión Académica");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear paneles
        BienvenidaPanel bienvenidaPanel = new BienvenidaPanel();
        EstudiantesPanel estudiantesPanel = new EstudiantesPanel();
        CursosPanel cursosPanel = new CursosPanel();
        MatriculasPanel matriculasPanel = new MatriculasPanel();
        NotasPanel notasPanel = new NotasPanel();

        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Gestión");

        JMenuItem itemInicio = new JMenuItem("Inicio");
        JMenuItem itemEstudiantes = new JMenuItem("Estudiantes");
        JMenuItem itemCursos = new JMenuItem("Cursos");
        JMenuItem itemMatriculas = new JMenuItem("Matrículas");
        JMenuItem itemNotas = new JMenuItem("Notas");
        JMenuItem itemSalir = new JMenuItem("Salir");

        menu.add(itemInicio);
        menu.addSeparator();
        menu.add(itemEstudiantes);
        menu.add(itemCursos);
        menu.add(itemMatriculas);
        menu.add(itemNotas);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        menu.addSeparator(); // línea separadora
        menu.add(itemSalir);


        // Crear panel central con CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(bienvenidaPanel, "Bienvenida");
        cardPanel.add(estudiantesPanel, "Estudiantes");
        cardPanel.add(cursosPanel, "Cursos");
        cardPanel.add(matriculasPanel, "Matrículas");
        cardPanel.add(notasPanel, "Notas");

        add(cardPanel, BorderLayout.CENTER);

        // Acciones del menú
        itemInicio.addActionListener(e -> cardLayout.show(cardPanel, "Bienvenida"));
        itemEstudiantes.addActionListener(e -> cardLayout.show(cardPanel, "Estudiantes"));
        itemCursos.addActionListener(e -> cardLayout.show(cardPanel, "Cursos"));
        itemMatriculas.addActionListener(e -> {
            matriculasPanel.recargarCombos();
            cardLayout.show(cardPanel, "Matrículas");
        });
        itemNotas.addActionListener(e -> {
            notasPanel.cargarVistaNotas(); // refrescar la tabla
            cardLayout.show(cardPanel, "Notas");
        });

        itemSalir.addActionListener(e -> System.exit(0));


        // Mostrar pantalla de bienvenida por defecto
        cardLayout.show(cardPanel, "Bienvenida");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal().setVisible(true));
    }
}