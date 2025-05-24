package controlador;

import Modelo.Conexion;
import Modelo.Docente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteControlador {
    public List<Docente> listarTodos() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Docente(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}