package frgp.utn.edu.com.tabcontrol.conexion;

import android.content.Context;
import android.widget.ListView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import android.widget.Toast;
import frgp.utn.edu.com.tabcontrol.adapter.ArticuloAdapter;
import frgp.utn.edu.com.tabcontrol.clases.Articulo;



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

            // Actualiza la UI en el hilo principal
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {

                ArticuloAdapter adapter = new ArticuloAdapter(context, listaArticulo);
                lvArticulo.setAdapter(adapter);
                Toast.makeText(context, "Conexion exitosa", Toast.LENGTH_SHORT).show();
            });
        });
    }
}
