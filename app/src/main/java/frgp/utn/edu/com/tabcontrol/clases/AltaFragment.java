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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                categoriaId = 0;
            }
        });

        return view;
    }

    private void cargarCategorias() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
            listaCategorias = dataMainActivity.obtenerCategorias();

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaCategorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapter);
            });
        });
    }
    private void agregarArticulo() {
        String idStr = etId.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String stockStr = etStock.getText().toString().trim();

        if (!nombre.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(getContext(), "El nombre solo puede contener letras", Toast.LENGTH_SHORT).show();
            return;
        }

        int idc = Integer.parseInt(idStr);
        DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
        boolean idExiste = dataMainActivity.articuloExiste(idc);

        if (idExiste) {
            Toast.makeText(getContext(), "El ID del artículo ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!idStr.isEmpty() && !nombre.isEmpty() && !stockStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            int stock = Integer.parseInt(stockStr);
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