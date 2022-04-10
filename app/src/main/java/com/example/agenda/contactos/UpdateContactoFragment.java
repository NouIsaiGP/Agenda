package com.example.agenda.contactos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.AgendaActivity;
import com.example.agenda.R;
import com.example.agenda.db.DbContactos;
import com.example.agenda.model.Contacto;

/*
     Fragment oara actualizar y agregar nuevos contactos
 */
public class UpdateContactoFragment extends Fragment {

    View view;
    EditText txtNombre, txtTelefono, txtCumple, txtNota, txtId;
    Button btnAdd, btnDate;
    private int id;
    private boolean flag = false;
    Contacto contacto;

    public UpdateContactoFragment() {
    }

    public UpdateContactoFragment(Contacto contacto) {
        this.contacto = contacto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializacion de las variables utilizadas
        view = inflater.inflate(R.layout.fragment_update_contacto, container, false);

        txtId = view.findViewById(R.id.txtId);
        txtCumple = view.findViewById(R.id.txtdate);
        txtNombre = view.findViewById(R.id.txtname);
        txtNota = view.findViewById(R.id.txtnote);
        txtTelefono = view.findViewById(R.id.txttel);
        btnAdd = view.findViewById(R.id.btnAgregar);
        btnDate = view.findViewById(R.id.btnFecha);

        // En caso de que se envie un contacto para actualizar se hacen modificaciones el Fragment
        if (this.getArguments() != null){
            Bundle bundle = this.getArguments();
            txtId.setText(bundle.getInt("id") + "");
            id = (int) bundle.getSerializable("id");
            txtNombre.setText(bundle.getString("nombre"));
            txtTelefono.setText(bundle.getString("telefono"));
            Log.e("hola", bundle.getString("date") + "");
            txtCumple.setText(bundle.getString("date"));
            txtNota.setText(bundle.getString("nota"));
            /*
                Se modifica variables publicas staticas de la @AgendaActivity
                para mantener el funcionamiento del boton flotante
             */
            AgendaActivity.addIcon.setImageResource(R.drawable.ic_baseline_arrow_back_24);
            Drawable icon = AgendaActivity.addIcon.getDrawable();
            AgendaActivity.btnAdd.setImageDrawable(icon);
            AgendaActivity.flag = true;
            btnAdd.setText("GUARDAR");
        }
        //Log.e("bundle",this.getArguments().is() + "");
        // Boton para mostrar un fragment de seleccion de fecha
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Boton para agregar o actualizar Contactos a la base de datos
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = container.getContext();
                DbContactos db = new DbContactos(context);
                // Si se tiene argumentos establecidos, en este caso datos de un contacto existente
                if (getArguments() != null){
                    //Log.e("id2 error? ", id +" nom " + txtNombre.getText() + " cum " + txtCumple.getText() + " tel " + txtTelefono.getText() + " "+ txtNota.getText());
                    // Se pasan los datos ingresados en los campos de texto de la aplicacion para actualizar el usuario anteriormente seleccionado, con una ID oculta en la interfaz de usuario
                    if (isEmpty() == false) {
                        boolean flag = db.editarContacto(id, txtNombre.getText().toString(), txtCumple.getText().toString(), txtTelefono.getText().toString(), txtNota.getText().toString());
                    }
                    /*
                        Se modifica variables publicas staticas de la @AgendaActivity
                        para mantener el funcionamiento del boton flotante
                    */
                    AgendaActivity.addIcon.setImageResource(R.drawable.ic_baseline_add_24);
                    Drawable icon = AgendaActivity.addIcon.getDrawable();
                    AgendaActivity.btnAdd.setImageDrawable(icon);
                    btnAdd.setText("AGREGAR");
                    AgendaActivity.flag = false;
                    // Se cambia de fragment para mostrar los datos realizado a la base de datos
                    ContactosFragment fragment = new ContactosFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainlayout,fragment).commit();

                    // En caso de que no se halla seleccionado un contacto existente se agregara un nuevo contacto
                }else {
                    if (isEmpty() == false) {
                        long id = db.insertContacto(txtNombre.getText().toString(), txtCumple.getText().toString(), txtTelefono.getText().toString(), txtNota.getText().toString());
                    }
                    //Log.e("prueba", "No entro, " + id);
                }
                // Validacion de regristro de usuario
                if (id > 0 ){
                    Toast.makeText(context, "Registro Guardado", Toast.LENGTH_LONG).show();
                    // Se retorna al la lista de contactos para mostrar los cambios realizados a la base de datos
                    ContactosFragment fragment = new ContactosFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainlayout,fragment).commit();
                }else {
                    Toast.makeText(context, "Error al Guardar Registro", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    // Funcion para llamar el fragment de seleccion de fecha
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txtCumple.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    

    // Metodo para validar campos de texto
    private boolean isEmpty(){
        if (txtCumple.getText().equals("") | txtNota.getText().equals("") | txtNombre.getText().equals("") | txtTelefono.getText().equals("")) {
            return true;
        }
        return false;
    }
}