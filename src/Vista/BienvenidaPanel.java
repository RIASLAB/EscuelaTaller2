package Vista;

import javax.swing.*;
import java.awt.*;

public class BienvenidaPanel extends JPanel {
    public BienvenidaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // disposición vertical

        // Texto centrado arriba
        JLabel mensaje = new JLabel("¡Bienvenido al sistema de gestion de la Escuela RIASMAJ!", SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 30));
        mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Imagen centrada abajo
        ImageIcon icono = new ImageIcon(getClass().getResource("/Img/descarga.png"));
        JLabel imagenLabel = new JLabel(icono);
        imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema de gestión académica", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 28));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);



        // Espaciado opcional
        add(Box.createVerticalStrut(40)); // espacio arriba
        add(mensaje);
        add(Box.createVerticalStrut(30)); // espacio entre texto e imagen
        add(imagenLabel);
        add(Box.createVerticalGlue()); // empuja todo hacia el centro
        add(subtitulo);
        add(Box.createVerticalGlue());
    }
}
