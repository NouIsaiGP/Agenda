package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.agenda.contactos.ContactosFragment;
import com.example.agenda.contactos.UpdateContactoFragment;
import com.example.agenda.db.DBHelper;
import com.example.agenda.model.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*
    MainActivity de la aplicacion
    el cual funciona como puente entre dos fragments principales, @ContactosFragment y @ UpdateContactoFragment
    Cuenta con un boton flotante para manejar los fragment
 */
public class AgendaActivity extends AppCompatActivity {

    // Boton flotante
    public static FloatingActionButton btnAdd;
    // @flag se utiliza para mantener el funcionamiento del boton flotante atraves de los diferentes fragments de la aplicacion
    public static boolean flag = false;
    public static ImageView backIcon;
    public static ImageView addIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        backIcon = new ImageView(this);
        addIcon = new ImageView(this);

        // Se inicializa la lista de contactos del usuario
        replaceFragment2(new ContactosFragment());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambia de fragment para agregar un nuevo contacto
                if(flag==false){
                    replaceFragment(new UpdateContactoFragment());
                    // Actualiza el boton flotante
                    backIcon.setImageResource(R.drawable.ic_baseline_arrow_back_24);
                    Drawable icon = backIcon.getDrawable();
                    btnAdd.setImageDrawable(icon);
                    flag=true;
                // Regresa a la lista de contactos del usuario
                }else if (flag==true){
                    replaceFragment2(new ContactosFragment());
                    // Actualiza el boton flotante
                    addIcon.setImageResource(R.drawable.ic_baseline_add_24);
                    Drawable icon = addIcon.getDrawable();
                    btnAdd.setImageDrawable(icon);
                    flag=false;
                }
            }
        });

        // Inicializa la base de datos
        DBHelper dbHelper = new DBHelper(AgendaActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    /*
        Metodos para actualizar el fragment con el boton flotante
     */

    public void replaceFragment(UpdateContactoFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainlayout, fragment);
        fragmentTransaction.commit();

    }

    private void replaceFragment2(ContactosFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainlayout, fragment);
        fragmentTransaction.commit();

    }
}