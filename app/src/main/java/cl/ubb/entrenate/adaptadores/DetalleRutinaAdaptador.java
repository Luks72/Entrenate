package cl.ubb.entrenate.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.DetalleEjercicioRutina;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

public class DetalleRutinaAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<DetalleEjercicioRutina> detallerEjercicio;
    private ArrayList<String> nombres;
    FirebaseFirestore bdd;


    public DetalleRutinaAdaptador(Context context, ArrayList<DetalleEjercicioRutina> detallerEjercicio) {
        this.context = context;
        this.detallerEjercicio = detallerEjercicio;
    }

    @Override
    public int getCount() {
        return detallerEjercicio.size();
    }

    @Override
    public Object getItem(int position) {
        return detallerEjercicio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView nombreEjercicio;
        TextView series;
        TextView repeticiones;
        TextView descanso;
        TextView instrucciones;
        Button detalles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        bdd=FirebaseFirestore.getInstance();
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.detallerutina_grid, null);
        }

        holder.nombreEjercicio = (TextView) convertView.findViewById(R.id.nombre_detalleRutina);
        holder.series = (TextView) convertView.findViewById(R.id.series_detalleRutina);
        holder.repeticiones = (TextView) convertView.findViewById(R.id.repeticiones_detalleRutina);
        holder.descanso = (TextView) convertView.findViewById(R.id.descanso_detalleRutina);
        holder.instrucciones = (TextView) convertView.findViewById(R.id.instrucciones_detalleRutina);
        holder.detalles = (Button) convertView.findViewById(R.id.detalle_detalleRutina);
        DetalleEjercicioRutina det = detallerEjercicio.get(position);

        holder.detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdd.collection("clasificacion")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                                               bdd.collection("clasificacion").document(documentSnapshots.getString("nombre")).collection("ejercicios")
                                                                       .whereEqualTo("nombre", det.getNombre())
                                                                       .get()
                                                                       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                  @Override
                                                                                                  public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                                      if (task1.isSuccessful()) {
                                                                                                          for (QueryDocumentSnapshot documentSnapshots1 : task1.getResult()) {
                                                                                                              Intent intent = new Intent(context.getApplicationContext(), DetalleEjercicio.class)
                                                                                                                      .putExtra("nombre", documentSnapshots1.getString("nombre"))
                                                                                                                      .putExtra("descripcion", documentSnapshots1.getString("descripcion"))
                                                                                                                      .putExtra("url", documentSnapshots1.getString("url"))
                                                                                                                      .putExtra("video", documentSnapshots1.getString("video"));
                                                                                                              context.startActivity(intent);

                                                                                                          }
                                                                                                      }
                                                                                                  }
                                                                                              }

                                                                       );
                                                           }

                                                       }
                                                   }
                                               }

                        );

            }
        });



        holder.nombreEjercicio.setText(det.getNombre());
        holder.series.setText("Series: " +det.getSeries());
        holder.repeticiones.setText("Repeticiones: " +det.getRepeticiones());
        holder.descanso.setText("Descanso: "+det.getDescanso());
        holder.instrucciones.setText("instrucciones: "+det.getInstrucciones());

        return convertView;
    }
}