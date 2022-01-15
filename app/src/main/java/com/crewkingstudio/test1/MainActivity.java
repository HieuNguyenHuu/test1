package com.crewkingstudio.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.awt.font.TextAttribute;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor, sensorAcce, sensorMag, sensorGys;

    TextView textViewAcce, textViewMag, textViewGys;

    int plot = 1;
    int plotMag = 1;
    LineGraphSeries<DataPoint> seriesAcceX = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesAcceY = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesAcceZ = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesAcce = new LineGraphSeries<DataPoint>();

    LineGraphSeries<DataPoint> seriesMagX = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesMagY = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesMagZ = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesMag = new LineGraphSeries<DataPoint>();

    LineGraphSeries<DataPoint> seriesGysX = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesGysY = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesGysZ = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> seriesGys = new LineGraphSeries<DataPoint>();

    Viewport viewportAcce, viewportMag, viewportGys;

    Button buttonstop;

    Button buttonstart;

    ToggleButton buttonscrollable;

    Scrollviewcustom scrollView;

    FloatingActionButton floatingActionButton;

    Boolean stop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAcce = (TextView) findViewById(R.id.acce);
        textViewMag = (TextView) findViewById(R.id.Mag);
        textViewGys = (TextView) findViewById(R.id.Gys);

        buttonstop = (Button) findViewById(R.id.stop);
        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop = false;
            }
        });

        buttonstart = (Button) findViewById(R.id.start);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop = true;
            }
        });

        scrollView = (Scrollviewcustom)  findViewById(R.id.scrollView);

        buttonscrollable = (ToggleButton)  findViewById(R.id.scrollable);
        buttonscrollable.setChecked(true);

        buttonscrollable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();

                scrollView.setEnableScrolling(checked);

            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorAcce = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, sensorAcce, SensorManager.SENSOR_DELAY_NORMAL);

        sensorMag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(MainActivity.this, sensorMag, SensorManager.SENSOR_DELAY_NORMAL);

        sensorGys = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this, sensorGys, SensorManager.SENSOR_DELAY_NORMAL);

        GraphView graphAcce = (GraphView) findViewById(R.id.graphAcce);
        viewportAcce = graphAcce.getViewport();
        viewportAcce.scrollToEnd();
        viewportAcce.setXAxisBoundsManual(true);
        graphAcce.addSeries(seriesAcceX);
        graphAcce.addSeries(seriesAcceY);
        graphAcce.addSeries(seriesAcceZ);
        graphAcce.addSeries(seriesAcce);

        GraphView graphMag = (GraphView) findViewById(R.id.graphMag);
        viewportMag = graphMag.getViewport();
        viewportMag.scrollToEnd();
        viewportMag.setXAxisBoundsManual(true);
        graphMag.addSeries(seriesMagX);
        graphMag.addSeries(seriesMagY);
        graphMag.addSeries(seriesMagZ);
        graphMag.addSeries(seriesMag);

        GraphView graphGys = (GraphView) findViewById(R.id.graphGys);
        viewportGys = graphGys.getViewport();
        viewportGys.scrollToEnd();
        viewportGys.setXAxisBoundsManual(true);
        graphGys.addSeries(seriesGysX);
        graphGys.addSeries(seriesGysY);
        graphGys.addSeries(seriesGysZ);
        graphGys.addSeries(seriesGys);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        plot++;

        if(plot > 10000){
            plot = 1;
            seriesAcceX.resetData( new DataPoint[]{new DataPoint( 1, 0)});
            seriesAcceY.resetData( new DataPoint[]{new DataPoint(1, 0)});
            seriesAcceZ.resetData(new DataPoint[]{new DataPoint(1, 0)});
            seriesMagX.resetData( new DataPoint[]{new DataPoint(1, 0)});
            seriesMagY.resetData( new DataPoint[]{new DataPoint(1, 0)});
            seriesMagZ.resetData(new DataPoint[]{new DataPoint(1, 0)});
            seriesGysX.resetData( new DataPoint[]{new DataPoint(1, 0)});
            seriesGysY.resetData( new DataPoint[]{new DataPoint(1, 0)});
            seriesGysZ.resetData(new DataPoint[]{new DataPoint(1, 0)});
        }

        if(stop) {

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                double acce = Math.sqrt(Math.pow(sensorEvent.values[0] , 2) +  Math.pow(sensorEvent.values[1] , 2) + Math.pow(sensorEvent.values[2] , 2));

                String text = "ACCELEROMETER sensor :<br><font color=#ff4c4c>X: " + sensorEvent.values[0] + "</font><br><font color=#89CFF0>Y: " + sensorEvent.values[1] + "</font><br><font color=#00FF7F>Z: " + sensorEvent.values[2] + "</font><br><font color=#808080>ACCE: " + acce + "</font><br>";
                textViewAcce.setText(Html.fromHtml(text));

                seriesAcceX.appendData(new DataPoint(plot, (int) sensorEvent.values[0]), true, plot);
                seriesAcceX.setColor(Color.parseColor("#ff4c4c"));
                seriesAcceX.setDrawDataPoints(true);
                seriesAcceX.setDataPointsRadius(10);

                seriesAcceY.appendData(new DataPoint(plot, (int) sensorEvent.values[1]), true, plot);
                seriesAcceY.setColor(Color.parseColor("#89CFF0"));
                seriesAcceY.setDrawDataPoints(true);
                seriesAcceY.setDataPointsRadius(10);

                seriesAcceZ.appendData(new DataPoint(plot, (int) sensorEvent.values[2]), true, plot);
                seriesAcceZ.setColor(Color.parseColor("#00FF7F"));
                seriesAcceZ.setDrawDataPoints(true);
                seriesAcceZ.setDataPointsRadius(10);

                seriesAcce.appendData(new DataPoint(plot, acce), true, plot);
                seriesAcce.setColor(Color.parseColor("#808080"));
                seriesAcce.setDrawDataPoints(true);
                seriesAcce.setDataPointsRadius(10);

                viewportAcce.setMaxX(plot + 20);
                viewportAcce.setMinX(plot - 50);
                viewportAcce.setScalable(true);
                //viewportAcce.setScalableY(true);
                viewportAcce.setScrollable(true);
                //viewportAcce.setScrollableY(true);
            }
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                double mag = Math.sqrt(Math.pow(sensorEvent.values[0] , 2) +  Math.pow(sensorEvent.values[1] , 2) + Math.pow(sensorEvent.values[2] , 2));

                String text = "MAGNETIC_FIELD sensor :<br><font color=#ff4c4c>X: " + sensorEvent.values[0] + "</font><br><font color=#89CFF0>Y: " + sensorEvent.values[1] + "</font><br><font color=#00FF7F>Z: " + sensorEvent.values[2] + "</font><br><font color=#808080>MAG: " + mag + "</font><br>";
                textViewMag.setText(Html.fromHtml(text));

                seriesMagX.appendData(new DataPoint(plot, (int) sensorEvent.values[0]), true, plot);
                seriesMagX.setColor(Color.parseColor("#ff4c4c"));
                seriesMagX.setDrawDataPoints(true);
                seriesMagX.setDataPointsRadius(6);

                seriesMagY.appendData(new DataPoint(plot, (int) sensorEvent.values[1]), true, plot);
                seriesMagY.setColor(Color.parseColor("#89CFF0"));
                seriesMagY.setDrawDataPoints(true);
                seriesMagY.setDataPointsRadius(6);

                seriesMagZ.appendData(new DataPoint(plot, (int) sensorEvent.values[2]), true, plot);
                seriesMagZ.setColor(Color.parseColor("#00FF7F"));
                seriesMagZ.setDrawDataPoints(true);
                seriesMagZ.setDataPointsRadius(6);

                seriesMag.appendData(new DataPoint(plot, mag), true, plot);
                seriesMag.setColor(Color.parseColor("#808080"));
                seriesMag.setDrawDataPoints(true);
                seriesMag.setDataPointsRadius(10);

                viewportMag.setMaxX(plot + 20);
                viewportMag.setMinX(plot - 50);
                viewportMag.setScalable(true);
                //viewportMag.setScalableY(true);
                viewportMag.setScrollable(true);
                //viewportMag.setScrollableY(true);

            }
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                double gys = Math.sqrt(Math.pow(sensorEvent.values[0] , 2) +  Math.pow(sensorEvent.values[1] , 2) + Math.pow(sensorEvent.values[2] , 2));

                String text = "GYROSCOPE sensor :<br><font color=#ff4c4c>X: " + sensorEvent.values[0] + "</font><br><font color=#89CFF0>Y: " + sensorEvent.values[1] + "</font><br><font color=#00FF7F>Z: " + sensorEvent.values[2] + "</font><br><font color=#808080>GYS: " + gys + "</font><br>";
                textViewGys.setText(Html.fromHtml(text));

                seriesGysX.appendData(new DataPoint(plot, (int) sensorEvent.values[0]), true, plot);
                seriesGysX.setColor(Color.parseColor("#ff4c4c"));
                seriesGysX.setDrawDataPoints(true);
                seriesGysX.setDataPointsRadius(6);

                seriesGysY.appendData(new DataPoint(plot, (int) sensorEvent.values[1]), true, plot);
                seriesGysY.setColor(Color.parseColor("#89CFF0"));
                seriesGysY.setDrawDataPoints(true);
                seriesGysY.setDataPointsRadius(6);

                seriesGysZ.appendData(new DataPoint(plot, (int) sensorEvent.values[2]), true, plot);
                seriesGysZ.setColor(Color.parseColor("#00FF7F"));
                seriesGysZ.setDrawDataPoints(true);
                seriesGysZ.setDataPointsRadius(6);

                seriesGys.appendData(new DataPoint(plot, (int) gys), true, plot);
                seriesGys.setColor(Color.parseColor("#808080"));
                seriesGys.setDrawDataPoints(true);
                seriesGys.setDataPointsRadius(10);

                viewportGys.setMaxX(plot + 20);
                viewportGys.setMinX(plot - 50);
                viewportGys.setScalable(true);
                //viewportGys.setScalableY(true);
                viewportGys.setScrollable(true);
                //viewportGys.setScrollableY(true);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}