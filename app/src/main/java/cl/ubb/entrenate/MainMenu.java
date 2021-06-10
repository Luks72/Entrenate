package cl.ubb.entrenate;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.ui.contextmenu.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainMenu extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    ImageView imageView;
    TextView nombre, correo;
    Button cerrar;
    String correoUsuario, nombreUsuario, urlUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.ic_baseline_add_24);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, MainActivity.class));
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ejercicios, R.id.nav_rutinas, R.id.nav_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        imageView = headerView.findViewById(R.id.header_image);
        nombre = headerView.findViewById(R.id.header_nombre);
        correo = headerView.findViewById(R.id.header_correo);

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        correoUsuario = prefs.getString("correo", null);
        nombreUsuario = prefs.getString("nombre", null);
        urlUsuario = prefs.getString("url", null);
        while(nombreUsuario==null){
            correoUsuario = prefs.getString("correo", null);
            nombreUsuario = prefs.getString("nombre", null);
            urlUsuario = prefs.getString("url", null);
        }

        correo.setText(correoUsuario);
        nombre.setText(nombreUsuario);
        Picasso.get().load(urlUsuario).into(imageView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(MainMenu.this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
                getSharedPreferences("credenciales", 0).edit().clear().commit();
                startActivity(new Intent(MainMenu.this,LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}