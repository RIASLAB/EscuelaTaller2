package Vista;

import controlador.EstudianteControlador;
import Modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EstudiantesPanel extends JPanel {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable tablaEstudiantes;

    private EstudianteControlador estudianteControlador = new EstudianteControlador();

    public EstudiantesPanel() {
        add(panelPrincipal);
        cargarTabla();

        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnActualizar.addActionListener(e -> actualizarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());

        tablaEstudiantes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaEstudiantes.getSelectedRow();
                if (fila != -1) {
                    txtCodigo.setText(tablaEstudiantes.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaEstudiantes.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private void agregarEstudiante() {
        try {
            String input = txtCodigo.getText().trim();
            if (!input.matches("\\d+")) {
                mostrar("El código debe contener solo números.");
                return;
            }

            int codigo = Integer.parseInt(input);
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                mostrar("El nombre no puede estar vacío.");
                return;
            }

            estudianteControlador.insertar(new Estudiante(codigo, nombre));
            mostrar("Estudiante registrado.");
            cargarTabla();
            limpiar();
        } catch (Exception ex) {
            mostrar("Error al agregar: " + ex.getMessage());
        }
    }

    private void actualizarEstudiante() {
        try {
            if (tablaEstudiantes.getSelectedRow() == -1) {
                mostrar("Selecciona un estudiante.");
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                mostrar("El nombre no puede estar vacío.");
                return;
            }

            estudianteControlador.actualizar(new Estudiante(codigo, nombre));
            mostrar("Estudiante actualizado.");
            cargarTabla();
            limpiar();
        } catch (Exception ex) {
            mostrar("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila != -1) {
            int codigo = Integer.parseInt(tablaEstudiantes.getValueAt(fila, 0).toString());
            estudianteControlador.eliminar(codigo);
            mostrar("Estudiante eliminado.");
            cargarTabla();
            limpiar();
        } else {
            mostrar("Selecciona un estudiante para eliminar.");
        }
    }

    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");

        for (Estudiante e : estudianteControlador.listarTodos()) {
            modelo.addRow(new Object[]{e.getCodigo(), e.getNombre()});
        }

        tablaEstudiantes.setModel(modelo);
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        tablaEstudiantes.clearSelection();
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
