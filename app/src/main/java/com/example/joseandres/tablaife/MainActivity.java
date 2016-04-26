package com.example.joseandres.tablaife;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private EditText edt1,edt2,edt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1=(EditText)findViewById(R.id.edt1);
        edt2=(EditText)findViewById(R.id.edt2);
        edt3=(EditText)findViewById(R.id.edt3);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//La clase SQLiteOpenHelper nos permite crear la base de datos
// y actualizar la estructura de tablas y datos iniciales.
    //Cuando se presiona el botón "ALTA" se ejecuta el método "alta"
    public void alta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt1.getText().toString();
        String direc = edt2.getText().toString();
        String cas = edt3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("direccion", direc);
        registro.put("casilla", cas);
        bd.insert("votante", null, registro);
        bd.close();
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        Toast.makeText(this, "Se cargaron los datos del votante",
                Toast.LENGTH_SHORT).show();
    }
//Cuando se presiona el botón "CONSULTA POR CODIGO",
// se ejecuta el método consultaporcodigo.
    public void consultaporcodigo(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt1.getText().toString();
        Cursor fila = bd.rawQuery(
                "select direccion,numero from votante where codigo=" + cod, null);
        //llamamos al método moveToFirst() de la clase Cursos y retorna true en caso
        // de existir un articulo con el codigo ingresado, en caso contrario retorna cero.
        if (fila.moveToFirst()) {
            //Para recuperar los datos propiamente dichos que queremos consultar llamamos al
            // método getString y le pasamos la posición del campo a recuperar.
            edt2.setText(fila.getString(0));
            edt3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un votante con dicho código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }
//Cuando se presiona el botón "CONSULTA POR DIRECCION"
// se ejecuta el método consultapordireccion.
    public void consultapordireccion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descri = edt2.getText().toString();
        Cursor fila = bd.rawQuery(
                "select codigo,numero from votante where direccion='" + descri +"'", null);
        if (fila.moveToFirst()) {
            edt1.setText(fila.getString(0));
            edt3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un votante con dicha direccion",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }
//Para borrar uno o más registros la clase SQLiteDatabase tiene un método que le pasamos
// en el primer parámetro el nombre de la tabla y en el segundo la condición que debe
// cumplirse para que se borre la fila de la tabla.El método delete retorna un entero
// que indica la cantidad de registros borrados
    public void bajaporcodigo(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod= edt1.getText().toString();
        int cant = bd.delete("votante", "numero=" + cod, null);
        bd.close();
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró al votante con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe ningun votante con dicho código",
                    Toast.LENGTH_SHORT).show();
    }
//En la modificación de datos debemos crear un objeto de la clase ContentValues
// y mediante el método put almacenar los valores para cada campo que será modificado.
// Luego se llama al método update de la clase SQLiteDatabase pasando el nombre de la
// tabla, el objeto de la clase ContentValues y la condición del where.
    public void modificacion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt1.getText().toString();
        String descri = edt2.getText().toString();
        String pre = edt3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("direccion", descri);
        registro.put("numero", pre);
        int cant = bd.update("votante", registro, "codigo=" + cod, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "No existe ningun votante con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }

}
