package cl.ubb.entrenate.ui.home;

import android.app.Notification;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.gauge.pointers.Bar;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import cl.ubb.entrenate.AgregarUsuario;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.LoginActivity;
import cl.ubb.entrenate.R;

import static android.content.Context.MODE_PRIVATE;

import static cl.ubb.entrenate.adaptadores.NotificationHelper.CHANNEL_1_ID;
import static cl.ubb.entrenate.adaptadores.NotificationHelper.CHANNEL_2_ID;
import cl.ubb.entrenate.adaptadores.NotificationHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CardView cardView;
    TextView txt_nombre, txt_desc;
    FirebaseFirestore bdd;
    String nombreRutina, correoUsuario;
    private NotificationManagerCompat notificationManager;
   HorizontalBarChart chart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        bdd= FirebaseFirestore.getInstance();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("credenciales", MODE_PRIVATE);
        correoUsuario = prefs.getString("correo", null);

        cardView = root.findViewById(R.id.card_home);
        txt_nombre = root.findViewById(R.id.txt_home_nombreRutinaActual);
        chart = root.findViewById(R.id.chart);
        //txt_desc = root.findViewById(R.id.txt_home_descRutinaActual);
        notificationManager = NotificationManagerCompat.from(this.getActivity());
        String titulo= "para probar el titulo";
        String desc = "para probar la descripcion";

        bdd.collection("usuarios").document(correoUsuario).collection("rutinaActual")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().size()<1){
                            txt_nombre.setText("Parece que aún no comienzas una rutina");
                        }else{
                            for (DocumentSnapshot document : task.getResult()) {
                                txt_nombre.setText(document.getString("rutinaActual"));
                            }
                        }
                    }
                });

        /*Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) root.findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);*/
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(1, 5));
        BarDataSet set = new BarDataSet(data, "valores");
        BarData data1 = new BarData(set);
        chart.setData(data1);
        return root;
    }

}