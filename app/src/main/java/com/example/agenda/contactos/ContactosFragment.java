package com.example.agenda.contactos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agenda.AgendaActivity;
import com.example.agenda.R;
import com.example.agenda.db.DbContactos;
import com.example.agenda.model.Contacto;
import com.example.agenda.utils.adapters.ContactosAdapter;

import java.util.List;
import java.util.Timer;
/*
    @ContactosFragment
    es utilizado como contenedor principal de los contactos del usuario
 */

public class ContactosFragment extends Fragment {

    View view;
    ListView listView;
    private boolean toching = false;
    long lastDown = 0;
    long keyPressedDuration = 0;

    public ContactosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_contactos, container, false);
        listView = view.findViewById(R.id.listViewContactos);
        Context context = container.getContext();
        //Se traen los datos de la base de datos
        DbContactos db = new DbContactos(context);
        List<Contacto> lista = db.mostratContactos();

        //se usa la clase adapter para agregar los contactos a la lista
        ContactosAdapter contactosAdapter = new ContactosAdapter(context, R.layout.item_contacto, lista);
        listView.setAdapter(contactosAdapter);



        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == event.ACTION_DOWN){
                    toching = true;
                    lastDown = System.currentTimeMillis();
                    Log.e("time", lastDown + "");
                }else if(event.getAction() == event.ACTION_UP){
                    toching = false;
                    keyPressedDuration = System.currentTimeMillis() - lastDown;
                    Log.e("time", keyPressedDuration + "");
                }
                return false;
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (keyPressedDuration >= 4000){
                    Contacto con =(Contacto) listView.getItemAtPosition(position);
                    eliminar(con.getId());
                }else{
                    if (listView == null){
                        //Log.e("null", "null");
                    }else{
                        //Log.e("null", "not null");
                        //Al dar click en la lista se obtienen los datos del contacto seleccionado
                        Contacto con =(Contacto) listView.getItemAtPosition(position);
                        //se inicializa un bundle para pasar los datos atraves de activities, en este caso fragment
                        Bundle bundle = new Bundle();
                        //Datos del contacto
                        bundle.putInt("id", con.getId());
                        bundle.putString("nombre", con.getNombre());
                        bundle.putString("telefono", con.getTelefono());
                        bundle.putString("date", con.getCumpleaños());
                        bundle.putString("nota", con.getNota());
                        //Se cambia de fragment, junto con los datos para actualizar los datos en caso de ser necesario
                        UpdateContactoFragment fragment = new UpdateContactoFragment();
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainlayout,fragment).commit();

                    }
                }
                //Toast.makeText(context,"Contacto: " + contacto.toString(),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void eliminar(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DbContactos db = new DbContactos(getContext());
        builder.setMessage("¿Desea eliminar este contacto?")
                .setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminar(id);
                        List<Contacto> lista = db.mostratContactos();
                        ContactosAdapter contactosAdapter = new ContactosAdapter(getContext(), R.layout.item_contacto, lista);
                        listView.setAdapter(contactosAdapter);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }


}