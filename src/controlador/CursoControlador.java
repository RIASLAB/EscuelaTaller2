package controlador;

import Modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoControlador {
    private DocenteControlador docenteControlador = new DocenteControlador();

    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT c.cod_curso, c.nom_curso, d.cod_docente, d.nom_docente " +
                "FROM cursos c JOIN docentes d ON c.cod_docente = d.cod_docente";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Docente docente = new Docente(rs.getInt(3), rs.getString(4));
                Curso curso = new Curso(rs.getInt(1), rs.getString(2), docente);
                lista.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(Curso c) {
        String sql = "INSERT INTO cursos (cod_curso, nom_curso, cod_docente) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getCodigo());
            ps.setString(2, c.getNombre());
            ps.setInt(3, c.getDocente().getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Curso c) {
        String sql = "UPDATE cursos SET nom_curso = ?, cod_docente = ? WHERE cod_curso = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getDocente().getCodigo());
            ps.setInt(3, c.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int codigo) {
        String sql = "DELETE FROM cursos WHERE cod_curso = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
