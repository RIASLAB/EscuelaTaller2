package Vista;

import Modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class NotasPanel extends JPanel {
    private JPanel panelPrincipal;
    private JTable tablaNotas;

    public NotasPanel() {
        add(panelPrincipal);
        cargarVistaNotas();
    }

    public void cargarVistaNotas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Estudiante");
        modelo.addColumn("Curso");
        modelo.addColumn("Docente");
        modelo.addColumn("Nota");

        String sql = "SELECT * FROM vista_notas_detalle_simple";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("nombre_estudiante"),
                        rs.getString("nombre_curso"),
                        rs.getString("nombre_docente_curso"),
                        rs.getFloat("nota_curso")
                });
            }

            tablaNotas.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}