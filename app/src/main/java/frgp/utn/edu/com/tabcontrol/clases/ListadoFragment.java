package frgp.utn.edu.com.tabcontrol.clases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import frgp.utn.edu.com.tabcontrol.R;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

public class ListadoFragment extends Fragment {

    private ListView lvArticulo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_template, container, false);
        lvArticulo = view.findViewById(R.id.lvArticulo);
        DataMainActivity dataMainActivity = new DataMainActivity(lvArticulo, requireActivity());
        dataMainActivity.fetchData();

        return view;
    }

}

