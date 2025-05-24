package Vista;

import controlador.CursoControlador;
import controlador.EstudianteControlador;
import controlador.MatriculaControlador;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatriculasPanel extends JPanel {
    private JPanel panelPrincipal;
    private JComboBox<Estudiante> comboEstudiantes;
    private JComboBox<Curso> comboCursos;
    private JTextField txtNota;
    private JButton btnMatricular;
    private JButton btnEliminar;
    private JTable tablaMatricula;

    private EstudianteControlador estudianteControlador = new EstudianteControlador();
    private CursoControlador cursoControlador = new CursoControlador();
    private MatriculaControlador matriculaControlador = new MatriculaControlador();

    public MatriculasPanel() {
        add(panelPrincipal);
        cargarCombos();
        cargarTabla();

        btnMatricular.addActionListener(e -> matricular());
        btnEliminar.addActionListener(e -> eliminarMatricula());

        tablaMatricula.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaMatricula.getSelectedRow();
                if (fila != -1) {
                    txtNota.setText(tablaMatricula.getValueAt(fila, 2).toString());
                }
            }
        });
    }

    private void cargarCombos() {
        comboEstudiantes.removeAllItems();
        comboCursos.removeAllItems();
        estudianteControlador.listarTodos().forEach(comboEstudiantes::addItem);
        cursoControlador.listarTodos().forEach(comboCursos::addItem);
    }
    public void recargarCombos() {
        cargarCombos();
    }


    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Estudiante");
        modelo.addColumn("Curso");
        modelo.addColumn("Nota");

        for (Matricula m : matriculaControlador.listarTodas()) {
            modelo.addRow(new Object[]{
                    m.getEstudiante().getNombre(),
                    m.getCurso().getNombre(),
                    m.getNota()
            });
        }

        tablaMatricula.setModel(modelo);
    }

    private void matricular() {
        try {
            float nota = Float.parseFloat(txtNota.getText().trim());
            if (nota < 0 || nota > 5) {
                mostrar("La nota debe estar entre 0 y 5.");
                return;
            }

            Estudiante est = (Estudiante) comboEstudiantes.getSelectedItem();
            Curso curso = (Curso) comboCursos.getSelectedItem();

            matriculaControlador.insertar(new Matricula(est, curso, nota));
            mostrar("Matrícula registrada.");
            cargarTabla();
            txtNota.setText("");
        } catch (NumberFormatException ex) {
            mostrar("La nota debe ser un número válido.");
        }
    }

    private void eliminarMatricula() {
        int fila = tablaMatricula.getSelectedRow();
        if (fila != -1) {
            String nombreEst = tablaMatricula.getValueAt(fila, 0).toString();
            String nombreCurso = tablaMatricula.getValueAt(fila, 1).toString();

            Estudiante est = estudianteControlador.listarTodos().stream()
                    .filter(e -> e.getNombre().equals(nombreEst)).findFirst().orElse(null);
            Curso cur = cursoControlador.listarTodos().stream()
                    .filter(c -> c.getNombre().equals(nombreCurso)).findFirst().orElse(null);

            if (est != null && cur != null) {
                matriculaControlador.eliminar(est.getCodigo(), cur.getCodigo());
                mostrar("Matrícula eliminada.");
                cargarTabla();
                txtNota.setText("");
            }
        } else {
            mostrar("Selecciona una matrícula para eliminar.");
        }
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}