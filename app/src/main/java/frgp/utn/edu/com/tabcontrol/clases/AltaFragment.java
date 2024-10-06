package frgp.utn.edu.com.tabcontrol.clases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import frgp.utn.edu.com.tabcontrol.R;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

public class AltaFragment extends Fragment {

    private EditText etId;
    private EditText etNombre;
    private EditText etStock;
    private Spinner spinnerCategoria;
    private Button btnAgregar;

    private ArrayList<Categoria> listaCategorias;
    private int categoriaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta, container, false);

        etId = view.findViewById(R.id.etId);
        etNombre = view.findViewById(R.id.etNombre);
        etStock = view.findViewById(R.id.etStock);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        cargarCategorias();

        btnAgregar.setOnClickListener(v -> agregarArticulo());

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaId = listaCategorias.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoriaId = -1;
            }
        });

        return view;
    }

    private void cargarCategorias() {
        listaCategorias = new ArrayList<>();
        listaCategorias.add(new Categoria(1, "Categoría 1"));
        listaCategorias.add(new Categoria(2, "Categoría 2"));
        listaCategorias.add(new Categoria(3, "Categoría 3"));

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    private void agregarArticulo() {
        String idStr = etId.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String stockStr = etStock.getText().toString().trim();

        if (!idStr.isEmpty() && !nombre.isEmpty() && !stockStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            int stock = Integer.parseInt(stockStr);
            DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
            dataMainActivity.agregarArticulo(id, nombre, stock, categoriaId);
            Toast.makeText(getContext(), "Artículo agregado", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etId.setText("");
        etNombre.setText("");
        etStock.setText("");
        spinnerCategoria.setSelection(0);
    }
}