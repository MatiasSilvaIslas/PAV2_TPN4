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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import frgp.utn.edu.com.tabcontrol.R;
import frgp.utn.edu.com.tabcontrol.conexion.DataMainActivity;

public class ModificarFragment extends Fragment {
    private EditText etId, etNombre, etStock;
    private Spinner spinnerCategoria;
    private Button btnBuscar, btnModificar;
    private List<Categoria> listaCategorias;
    private int categoriaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar, container, false);

        etId = view.findViewById(R.id.etId);
        etNombre = view.findViewById(R.id.etNombre);
        etStock = view.findViewById(R.id.etStock);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnModificar = view.findViewById(R.id.btnModificar);

        cargarCategorias();

        btnBuscar.setOnClickListener(v -> buscarArticulo());

        btnModificar.setOnClickListener(v -> modificarArticulo());

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

    private void buscarArticulo() {

        if (etId.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese un ID de artículo", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int articuloId = Integer.parseInt(etId.getText().toString());
            DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
            Articulo articulo = dataMainActivity.buscarArticuloPorId(articuloId);

            if (articulo != null) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    etNombre.setText(articulo.getNombre());
                    etStock.setText(String.valueOf(articulo.getStock()));

                    for (int i = 0; i < listaCategorias.size(); i++) {
                        if (listaCategorias.get(i).getId() == articulo.getIdCategoria()) {
                            spinnerCategoria.setSelection(i);
                            break;
                        }
                    }
                });
            } else {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    Toast.makeText(requireContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void modificarArticulo() {

        if (etId.getText().toString().isEmpty() || etNombre.getText().toString().isEmpty() || etStock.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos antes de modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int id = Integer.parseInt(etId.getText().toString());
            String nombre = etNombre.getText().toString();
            int stock = Integer.parseInt(etStock.getText().toString());

            DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
            boolean success = dataMainActivity.modificarArticulo(id, nombre, stock, categoriaId);

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                if (success) {
                    Toast.makeText(requireContext(), "Artículo modificado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error al modificar el artículo", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
