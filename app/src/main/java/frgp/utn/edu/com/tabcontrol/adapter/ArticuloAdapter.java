package frgp.utn.edu.com.tabcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


import android.widget.TextView;
import frgp.utn.edu.com.tabcontrol.R;
import frgp.utn.edu.com.tabcontrol.clases.Articulo;

public class ArticuloAdapter extends ArrayAdapter<Articulo> {

    public ArticuloAdapter(Context context, List<Articulo> objetos) {
        super(context, R.layout.activity_main, objetos); // test
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_template, null);

        TextView tvid = (TextView) item.findViewById(R.id.idC);
        TextView tvdni = (TextView) item.findViewById(R.id.stock);
        TextView tvnombre = (TextView) item.findViewById(R.id.nombre);

        tvid.setText("Cod: "+getItem(position).getId()+"");
        tvnombre.setText("Articulo: "+getItem(position).getNombre());
        tvdni.setText("Stock: "+getItem(position).getStock()+"");


        return item;

    }
}
