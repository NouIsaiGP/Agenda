package com.example.agenda.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agenda.R;
import com.example.agenda.model.Contacto;

import java.util.List;
/*
    Clase para adaptar y inicializar los datos de la lista de contactos he ir agregando cuando se actualize la base de datos
 */
public class ContactosAdapter extends ArrayAdapter<Contacto> {

    private Context context;
    private List<Contacto> lista;

    public ContactosAdapter(@NonNull Context context, int resource, @NonNull List<Contacto> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lista = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_contacto, parent, false);

        //Inicializacion de variables por item_contacto.xml
        TextView txtId = view.findViewById(R.id.txtid);
        TextView txtNombre = view.findViewById(R.id.txtNombre);
        TextView txtTel = view.findViewById(R.id.txttelefono);
        TextView txtCumple = view.findViewById(R.id.txtcumple);
        TextView txtNota = view.findViewById(R.id.txtnota);

        /*for (Contacto c: lista.get(position).getContactos()) {

        }*/
        //Uso de la posicion del item y contacto para inicializar 1x1 cada contacto
        txtId.setText(lista.get(position).getId() + "");
        txtCumple.setText(lista.get(position).getCumpleaños());
        txtNombre.setText(lista.get(position).getNombre());
        txtNota.setText(lista.get(position).getNota());
        txtTel.setText(lista.get(position).getTelefono());

        return view;

    }
}
