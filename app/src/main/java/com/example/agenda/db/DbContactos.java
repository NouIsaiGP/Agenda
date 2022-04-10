package com.example.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.agenda.model.Contacto;

import java.util.ArrayList;
/*

    clase basica CRUD para actualizar base de datos atraves de SQLITE

 */

public class DbContactos extends DBHelper{

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    //funcion para agregar un nuevo contacto a la lista y base de datos, regresa un ID diferente a 0 en caso de que la insercion se halla realizado con axito
    public long insertContacto(String nombre,String cumple,String tel,String nota){
        long id = 0;

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //Valores que se necesitan para actualizar la base de datos, en este caso agregar un nuevo contacto
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", tel);
            values.put("cumple", cumple);
            values.put("nota", nota);

            id = db.insert(TABLE_CONTACTOS, null, values);

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    //funcion para obtener una lista actualizada de los contactos en la base de datos, regresa una ArrayList lleno con los contactos (La lista es de tipo @Contacto)
    public ArrayList<Contacto> mostratContactos(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contacto> listaContactos = new ArrayList<>();
        Contacto contacto = null;
        Cursor cursor = null;
        //Script SQL para obtencion de datos, @TABLE_CONTACTOS es el nombre de la base de datos
        cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS,null);
        if (cursor.moveToFirst()){
            do{
                contacto = new Contacto();
                contacto.setId(cursor.getInt(0));
                contacto.setNombre(cursor.getString(1));
                contacto.setTelefono(cursor.getString(2));
                contacto.setCumpleaños(cursor.getString(3));
                contacto.setNota(cursor.getString(4));
                //Log.e("datos: ", "nombre " + contacto.getNombre() + " cumple: " + contacto.getCumpleaños());
                listaContactos.add(contacto);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listaContactos;
    }

    //Funcion para editar un contacto existente atraves del id, regresa un boolean, TRUE (@flag) en caso de que se halla actualizado con exito la base de datos
    public boolean editarContacto(int id,String nombre,String cumple,String telefono,String nota){
        boolean flag = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Log.e("id error? ", id +" nom " + nombre + " cum " + cumple + " tel " + telefono + " "+ nota);

        try {
            //Script SQL para actualizar Contacto existente
            db.execSQL("UPDATE " + TABLE_CONTACTOS +
                    " SET nombre = '" + nombre +"', telefono = '" + telefono + "', cumple = '" + cumple + "', nota = '" + nota + "' WHERE id = '" + id + "'");
            flag = true;
        }catch (Exception ex){
            ex.toString();
            flag = false;
        }finally {
            db.close();
        }
        return flag;
    }

    public boolean eliminar(int id){
        boolean flag = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            //Script SQL para actualizar Contacto existente
            db.execSQL("DELETE FROM "+ TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            flag = true;
        }catch (Exception ex){
            ex.toString();
            flag = false;
        }finally {
            db.close();
        }
        return flag;
    }
}
