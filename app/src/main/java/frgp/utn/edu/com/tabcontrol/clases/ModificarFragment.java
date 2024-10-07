package frgp.utn.edu.com.tabcontrol.clases;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
                Categoria categoriaSeleccionada = listaCategorias.get(position);

                // Si la categoría seleccionada es "Seleccione una categoría", no permite la selección
                if (categoriaSeleccionada.getId() == 1) {
                    Toast.makeText(requireContext(), "Por favor, seleccione una categoría válida", Toast.LENGTH_SHORT).show();
                    categoriaId = -1; // Valor no válido
                } else {
                    // Categoría válida seleccionada
                    categoriaId = categoriaSeleccionada.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoriaId = -1;
            }
        });

        // Al crear la vista, el botón modificar estará deshabilitado
        btnModificar.setEnabled(false);

        return view;
    }

    private void cargarCategorias() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
            listaCategorias = dataMainActivity.obtenerCategorias();

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(requireContext(), android.R.layout.simple_spinner_item, listaCategorias) {
                    @Override
                    public boolean isEnabled(int position) {
                        // Deshabilita la opción "Seleccione una categoría" si tiene el id 1
                        return listaCategorias.get(position).getId() != 1;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView textView = (TextView) view;

                        // Cambia el color del texto para la opción "Seleccione una categoría"
                        if (listaCategorias.get(position).getId() == 1) {
                            textView.setTextColor(Color.GRAY);
                        } else {
                            textView.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapter);
            });
        });
    }

    private void buscarArticulo() {

        if (etId.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese un ID de artículo", Toast.LENGTH_SHORT).show();
            // Limpia las variables si el ID está vacío
            etNombre.setText("");
            etStock.setText("");
            spinnerCategoria.setSelection(0); // Restablecer el spinner a la primera opción

            Toast.makeText(requireContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show();
            btnModificar.setEnabled(false); // Deshabilitar el botón Modificar
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                int articuloId = Integer.parseInt(etId.getText().toString());
                DataMainActivity dataMainActivity = new DataMainActivity(null, requireContext());
                Articulo articulo = dataMainActivity.buscarArticuloPorId(articuloId);

                if (articulo != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        btnModificar.setEnabled(true); // Habilitar el botón Modificar
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
                        etNombre.setText("");
                        etStock.setText("");
                        spinnerCategoria.setSelection(0); // Restablecer el spinner a la primera opción

                        Toast.makeText(requireContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show();
                        btnModificar.setEnabled(false); // Deshabilitar el botón Modificar
                    });
                }

            } catch (NumberFormatException e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    Toast.makeText(requireContext(), "ID de artículo inválido", Toast.LENGTH_SHORT).show();
                });
                return;
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

