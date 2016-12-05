package com.ehsandev.cs2340.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.api.ReportHandler;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.task.QualityReportTask;
import com.ehsandev.cs2340.util.ErrorThrower;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;

public class GraphFragment extends Fragment implements QualityReportTask.AsyncTaskCompleteListener {
    private QualityReport[] reports;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_graph, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        Spinner s = (Spinner) getActivity().findViewById(R.id.which);
        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(getActivity(), R.array.ppmType, android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapterC);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        Button b = (Button) getActivity().findViewById(R.id.button_graph);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGraphUpdate();
            }
        });
        new QualityReportTask(this, getContext()).execute(sp.getString("cookie", null));
    }
    @Override
    public void onGotData(Response<QualityReport[]> s) {
        reports = s.getData();
    }
    public void onGraphUpdate(){
        EditText eminlat = (EditText)(getActivity().findViewById(R.id.minlat));
        EditText emaxlat = (EditText)(getActivity().findViewById(R.id.maxlat));

        EditText eminlon = (EditText)(getActivity().findViewById(R.id.minlon));

        EditText emaxlon = (EditText)(getActivity().findViewById(R.id.maxlon));

        EditText eyear = (EditText) getActivity().findViewById(R.id.year);

        Spinner s = (Spinner) getActivity().findViewById(R.id.which);
        String which = (String) s.getSelectedItem();

        ScatterChart chart =  (ScatterChart) getActivity().findViewById(R.id.chart);
        chart.clear();
        chart.invalidate();
        List<Entry> entries = new ArrayList<Entry>();
        if (emaxlat.length() == 0 || emaxlat.length() == 0 || eminlon.length() == 0 || emaxlat.length() == 0 || eyear.length() == 0) {
            DialogFragment alert = (DialogFragment) ErrorThrower
                    .newInstance(
                            "Please fill out all fields",
                            false);
            alert.show(getActivity().getSupportFragmentManager(), "unexpectederror");
        }
        else {
            double minLat = Double.parseDouble(eminlat.getText().toString());
            double maxLat = Double.parseDouble(emaxlat.getText().toString());
            double minLon = Double.parseDouble(eminlon.getText().toString());
            double maxLon = Double.parseDouble(emaxlon.getText().toString());
            int yearNum = Integer.parseInt(eyear.getText().toString());

            // Loop through reports
            for (QualityReport report : reports) {
                LocalDate dateTime = LocalDate.parse(report.getDate(), ISODateTimeFormat.dateTime());
                if (report.getLat() > minLat && report.getLat() < maxLat && report.getLon() > minLon && report.getLon() < maxLon && dateTime.getYear() == yearNum) {
                    if (which.equals("Virus")) {
                        entries.add(new Entry(dateTime.getDayOfYear(), report.getVirus()));
                    } else {
                        entries.add(new Entry(dateTime.getDayOfYear(), report.getContaminant()));
                    }
                }
            }
            if (entries.size() > 0) {
                ScatterDataSet dataSet = new ScatterDataSet(entries, "Reports");
                ScatterData data = new ScatterData(dataSet);
                Description d = new Description();
                d.setText("Day of Year");
                chart.setDescription(d);
                chart.setData(data);
                chart.invalidate();
            }
        }
    }
}
