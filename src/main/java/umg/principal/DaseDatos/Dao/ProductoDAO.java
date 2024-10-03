package umg.principal.DaseDatos.Dao;

import umg.principal.DaseDatos.conexion.DatabaseConnection;
import umg.principal.DaseDatos.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO tb_producto (descripcion, origen, precio, cantidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, producto.getDescripcion());
            pstmt.setString(2, producto.getOrigen());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setIdProducto(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Producto obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(rs.getInt("id_producto"), rs.getString("descripcion"), rs.getString("origen"), rs.getInt("precio"), rs.getInt("cantidad"));
                }
            }
        }
        return null;
    }



    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM tb_producto tp order by origen, descripcion";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("id_producto"), rs.getString("descripcion"), rs.getString("origen"), rs.getInt("precio"), rs.getInt("cantidad")));
            }
        }
        return productos;
    }


    public List<Producto> obtenerGenericos(String condicion) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = condicion;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("id_producto"), rs.getString("descripcion"), rs.getString("origen"), rs.getInt("precio"), rs.getInt("cantidad")));
            }
        }
        return productos;
    }

    public String opciondeconsulta(String opcion) throws SQLException {
        String sql = "SELECT * FROM tb_producto WHERE id_producto;";
        switch (opcion) {
            case "Todos los productos":
                sql = "SELECT * FROM tb_producto WHERE id_producto;";
                break;
            case "Precios menores a Q100.00":
                sql = "SELECT * FROM tb_producto WHERE  precio < 100;";
                break;
            case "Productos con menos de 30 existencias":
                sql = "SELECT * FROM tb_producto WHERE cantidad < 30;";
                break;
            case "Productos Con precios entre 200 y 400":
                sql = "SELECT * FROM tb_producto WHERE precio BETWEEN 200 AND 400;";
                break;
            case "Productos ordenandos de mayor a menor precio":
                sql = "SELECT * FROM tb_producto ORDER BY precio DESC;";
                break;
            case "Productos de menor a mayor existencias":
                sql = "SELECT * FROM tb_producto ORDER BY cantidad ASC;";
                break;

        }
        return sql;
    };


    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE tb_producto SET descripcion = ?, origen = ? WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getDescripcion());
            pstmt.setString(2, producto.getOrigen());
            pstmt.setInt(3, producto.getIdProducto());
            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tb_producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }


    public boolean eliminarCondicional(int id) throws SQLException {
        String sqlCheck = "SELECT precio FROM tb_producto WHERE id_producto = ?";
        String sqlDelete = "DELETE FROM tb_producto WHERE id_producto = ? AND precio = 0.00";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {

            // Primero, verificamos el precio del producto
            pstmtCheck.setInt(1, id);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    double precio = rs.getDouble("precio");
                    if (precio != 0.00) {
                        // El precio no es 0.00, no se puede eliminar
                        return false;
                    }
                } else {
                    // El producto no existe
                    return false;
                }
            }

            // Si llegamos aquí, el precio es 0.00, procedemos con la eliminación
            pstmtDelete.setInt(1, id);
            int rowsAffected = pstmtDelete.executeUpdate();

            // Si se eliminó al menos una fila, consideramos que fue exitoso
            return rowsAffected > 0;
        }
    } // fin de eliminarCondicional

}



