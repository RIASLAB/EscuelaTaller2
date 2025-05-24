package Vista;

import controlador.CursoControlador;
import controlador.DocenteControlador;
import Modelo.Curso;
import Modelo.Docente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CursosPanel extends JPanel {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<Docente> comboDocentes;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable tablaCursos;

    private CursoControlador cursoControlador = new CursoControlador();
    private DocenteControlador docenteControlador = new DocenteControlador();

    public CursosPanel() {
        add(panelPrincipal);
        cargarDocentes();
        cargarTabla();

        btnAgregar.addActionListener(e -> agregarCurso());
        btnActualizar.addActionListener(e -> actualizarCurso());
        btnEliminar.addActionListener(e -> eliminarCurso());

        tablaCursos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaCursos.getSelectedRow();
                if (fila != -1) {
                    txtCodigo.setText(tablaCursos.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaCursos.getValueAt(fila, 1).toString());
                    String docenteNombre = tablaCursos.getValueAt(fila, 2).toString();
                    seleccionarDocentePorNombre(docenteNombre);
                }
            }
        });
    }

    private void agregarCurso() {
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

            Docente docente = (Docente) comboDocentes.getSelectedItem();
            cursoControlador.insertar(new Curso(codigo, nombre, docente));
            mostrar("Curso registrado.");
            cargarTabla();
            limpiar();
        } catch (Exception ex) {
            mostrar("Error al agregar: " + ex.getMessage());
        }
    }

    private void actualizarCurso() {
        try {
            if (tablaCursos.getSelectedRow() == -1) {
                mostrar("Selecciona un curso.");
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            Docente docente = (Docente) comboDocentes.getSelectedItem();

            cursoControlador.actualizar(new Curso(codigo, nombre, docente));
            mostrar("Curso actualizado.");
            cargarTabla();
            limpiar();
        } catch (Exception ex) {
            mostrar("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarCurso() {
        int fila = tablaCursos.getSelectedRow();
        if (fila != -1) {
            int codigo = Integer.parseInt(tablaCursos.getValueAt(fila, 0).toString());
            cursoControlador.eliminar(codigo);
            mostrar("Curso eliminado.");
            cargarTabla();
            limpiar();
        } else {
            mostrar("Selecciona un curso para eliminar.");
        }
    }

    private void cargarDocentes() {
        comboDocentes.removeAllItems();
        List<Docente> lista = docenteControlador.listarTodos();
        for (Docente d : lista) {
            comboDocentes.addItem(d);
        }
    }

    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Docente");

        for (Curso c : cursoControlador.listarTodos()) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    c.getNombre(),
                    c.getDocente().getNombre()
            });
        }

        tablaCursos.setModel(modelo);
    }

    private void seleccionarDocentePorNombre(String nombre) {
        for (int i = 0; i < comboDocentes.getItemCount(); i++) {
            if (comboDocentes.getItemAt(i).getNombre().equals(nombre)) {
                comboDocentes.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        comboDocentes.setSelectedIndex(0);
        tablaCursos.clearSelection();
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
