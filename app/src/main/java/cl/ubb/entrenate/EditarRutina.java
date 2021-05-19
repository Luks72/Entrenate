package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarRutina extends AppCompatActivity {

    EditText editar_nombre, editar_descripcion, editar_vxs;
    private AdminSQLiteAdminHelper db;
    Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rutina);
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        editar_nombre = (EditText) findViewById(R.id.itxt_editarRutina_nombre);
        editar_descripcion = (EditText) findViewById(R.id.itxt_editarRutina_descripcion);
        editar_vxs = (EditText) findViewById(R.id.itxt_editarRutina_vxs);
        btn_aceptar = (Button) findViewById(R.id.btn_editarRutina_aceptar);

        int id_rutina=(int) getIntent().getIntExtra("id",0);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc=editar_descripcion.getText().toString();
                db.editar_rutina(id_rutina, desc);
                finish();
            }
        });




    }
}