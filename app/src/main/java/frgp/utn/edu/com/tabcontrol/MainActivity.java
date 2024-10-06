package frgp.utn.edu.com.tabcontrol;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import frgp.utn.edu.com.tabcontrol.adapter.ViewPageAdapter;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

public class MainActivity extends AppCompatActivity {

    private ListView lvClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        ViewPageAdapter adapter = new ViewPageAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("ALTA");
                            break;
                        case 1:
                            tab.setText("MODIFICACION");
                            break;
                        case 2:
                            tab.setText("LISTADO");
                            break;
                    }
                }).attach();
    }

}