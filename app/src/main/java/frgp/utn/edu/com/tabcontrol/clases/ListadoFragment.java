package frgp.utn.edu.com.tabcontrol.clases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import frgp.utn.edu.com.tabcontrol.R;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

/*
public class ListadoFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_template, container, false);
        ListView lvArticulo = view.findViewById(R.id.lvArticulo);
        DataMainActivity dataMainActivity = new DataMainActivity(lvArticulo, requireActivity());
        dataMainActivity.fetchData();

        return view;
    }

}
*/
public class ListadoFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_template, container, false);

        ListView lvArticulo = view.findViewById(R.id.lvArticulo);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(requireContext(), "cargando...", Toast.LENGTH_LONG).show();
        ListView lvArticulo = getView().findViewById(R.id.lvArticulo);
        lvArticulo.setAdapter(null); // Limpiar la lista antes de cargar los datos
        DataMainActivity dataMainActivity = new DataMainActivity(lvArticulo, requireActivity());
        dataMainActivity.fetchData(); // Refrescar los datos cada vez que el fragmento es visible
    }
}

