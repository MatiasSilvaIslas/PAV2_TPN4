package frgp.utn.edu.com.tabcontrol.conexion;

import android.content.Context;
import android.widget.ListView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import android.widget.Toast;
import frgp.utn.edu.com.tabcontrol.adapter.ArticuloAdapter;
import frgp.utn.edu.com.tabcontrol.clases.Articulo;
import frgp.utn.edu.com.tabcontrol.clases.Categoria;


public class DataMainActivity {

    private ListView lvArticulo;
    private Context context;


    public DataMainActivity(ListView lv, Context ct) {
        lvArticulo = lv;
        context = ct;
    }

    public void fetchData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Articulo> listaArticulo = new ArrayList<>();

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM articulo");

                while (rs.next()) {
                    Articulo Articulo = new Articulo();
                    Articulo.setId(rs.getInt("id"));
                    Articulo.setNombre(rs.getString("nombre"));
                    Articulo.setStock(rs.getInt("stock"));
                    listaArticulo.add(Articulo);
                }

                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Hay un problema", Toast.LENGTH_SHORT).show();
            }

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {

                ArticuloAdapter adapter = new ArticuloAdapter(context, listaArticulo);
                lvArticulo.setAdapter(adapter);
                Toast.makeText(context, "Conexion exitosa", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void agregarArticulo(int id, String nombre, int stock, int categoriaId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean success = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

                String query = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, id);
                pst.setString(2, nombre);
                pst.setInt(3, stock);
                pst.setInt(4, categoriaId);
                int rowsAffected = pst.executeUpdate();

                success = rowsAffected > 0;

                pst.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Error al agregar artículo", Toast.LENGTH_SHORT).show();
                });
                return;
            }

            boolean finalSuccess = success;
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                if (finalSuccess) {
                    Toast.makeText(context, "Artículo agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al agregar artículo", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    public ArrayList<Categoria> obtenerCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            String query = "SELECT id, descripcion FROM categoria";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                categorias.add(new Categoria(id, descripcion));
            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }


}
