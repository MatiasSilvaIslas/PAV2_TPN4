package frgp.utn.edu.com.tabcontrol;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

public class MainActivity extends AppCompatActivity {



    private ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvClientes = (ListView) this.findViewById(R.id.lvClientes);
        DataMainActivity dataMainActivity = new DataMainActivity(lvClientes, this);
        dataMainActivity.fetchData();

    }


}