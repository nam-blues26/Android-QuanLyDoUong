package edu.xda.adn.view.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.LoginActivity;

public class HomeFragment extends Fragment {

    private Toolbar toolbar;

    private BarChart barChart;

    private SimpleDateFormat sdf = new SimpleDateFormat(MyString.DATE_FORMAT);

    private DecimalFormat formatter = new DecimalFormat(MyString.PRICE_FORMAT);

    private TextView tv_total_bill, tv_total_revenue, tvDateFrom, tvDateTo;

    private Calendar calendarDateFrom, calendarDateTo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            init(view);
            createEvent();
//            createGraph(calendarDateFrom, calendarDateTo);
            statistics(calendarDateFrom, calendarDateTo);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {
        barChart = view.findViewById(R.id.barChar);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_total_bill = view.findViewById(R.id.tv_total_bill);
        tv_total_revenue = view.findViewById(R.id.tv_total_revenue);
        calendarDateFrom = Calendar.getInstance();
        calendarDateFrom.set(Calendar.DAY_OF_MONTH, calendarDateFrom.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendarDateTo = Calendar.getInstance();
        calendarDateTo.set(Calendar.DAY_OF_MONTH, calendarDateTo.getActualMaximum(Calendar.DAY_OF_MONTH));
        tvDateFrom = view.findViewById(R.id.tvDateFrom);
        tvDateTo = view.findViewById(R.id.tvDateTo);
        tvDateFrom.setText(calendarDateFrom.get(Calendar.DAY_OF_MONTH) + "/" + (calendarDateFrom.get(Calendar.MONTH) + 1) + "/" + calendarDateFrom.get(Calendar.YEAR));
        tvDateTo.setText(calendarDateTo.get(Calendar.DAY_OF_MONTH) + "/" + (calendarDateTo.get(Calendar.MONTH) + 1) + "/" + calendarDateTo.get(Calendar.YEAR));
    }

    private void createEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        tvDateFrom.setOnClickListener(e -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                try {
                    Calendar calendarDateFromTemplate = Calendar.getInstance();
                    calendarDateFromTemplate.set(i, i1, i2);//Todo: datefrom >= dateto
                    if(calendarDateFromTemplate.before(calendarDateTo)){
                        calendarDateFrom = calendarDateFromTemplate;
                    }else{
                        Toast.makeText(getContext(), R.string.date_from_after_date_to, Toast.LENGTH_SHORT).show();
                    }
                    tvDateFrom.setText(calendarDateFrom.get(Calendar.DAY_OF_MONTH) + "/" + (calendarDateFrom.get(Calendar.MONTH) + 1) + "/" + calendarDateFrom.get(Calendar.YEAR));
//                    createGraph(calendarDateFrom, calendarDateTo);
                    statistics(calendarDateFrom, calendarDateTo);
                } catch (Exception ex) {
                    Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        tvDateTo.setOnClickListener(e -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                try {
                    Calendar calendarDateToTemplate = Calendar.getInstance();
                    calendarDateToTemplate.set(i, i1, i2);
                    if(calendarDateToTemplate.after(calendarDateFrom)){
                        calendarDateTo = calendarDateToTemplate;
                    }else{
                        Toast.makeText(getContext(), R.string.date_to_before_date_from, Toast.LENGTH_SHORT).show();
                    }
                    tvDateTo.setText(calendarDateTo.get(Calendar.DAY_OF_MONTH) + "/" + (calendarDateTo.get(Calendar.MONTH) + 1) + "/" + calendarDateTo.get(Calendar.YEAR));
//                    createGraph(calendarDateFrom, calendarDateTo);
                    statistics(calendarDateFrom, calendarDateTo);
                } catch (Exception ex) {
                    Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

//    private void createGraph(Calendar dateFrom, Calendar dateTo) {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        Map<String, Float> map = createDataForGraph(dateFrom, dateTo);
//        if(map.size() <= 0){
//            Toast.makeText(getContext(),R.string.chart_message,Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String[] nameValue = new String[map.size()];
//        Set<String> set = map.keySet();
//        int i = 0;
//        for (String ld : set) {
//            nameValue[i] = ld;
//            barEntries.add(new BarEntry(i, map.get(ld)));
//            i++;
//        }
//
//        barChart.setDrawValueAboveBar(true);
//        barChart.setPinchZoom(true);
//        barChart.setDrawGridBackground(true);
//        barChart.setNoDataText(MyString.NO_DATA_TEXT);
//
//        BarDataSet barDataSet = new BarDataSet(barEntries, MyString.UNIT_NOTE_BARCHAR);
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16.0f);
//
//        BarData barData = new BarData(barDataSet);
//        barData.setBarWidth(0.5f);
//        barChart.setFitBars(true);
//        barChart.setData(barData);
//        barChart.getDescription().setEnabled(false);
//        barChart.setVisibleXRangeMaximum(4);
//        barChart.moveViewToX(1);
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setDrawGridLines(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1.0f);
//        xAxis.setDrawLabels(true);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameValue));
//
//    }

    private Map<String, Float> createDataForGraph(Calendar dateFrom, Calendar dateTo) {
        Set<String> localDateSet = new LinkedHashSet<>();
        Map<String, Float> map = new LinkedHashMap<>();
        for (Invoice invoice : LoginActivity.getInvoices()) {
            String localDate = sdf.format(invoice.getCreateDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(invoice.getCreateDate());
            if (!localDateSet.contains(localDate)
                    && calendar.get(Calendar.DAY_OF_MONTH) >= dateFrom.get(Calendar.DAY_OF_MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) <= dateTo.get(Calendar.DAY_OF_MONTH)
                    && calendar.get(Calendar.MONTH) + 1 >= dateFrom.get(Calendar.MONTH) + 1
                    && calendar.get(Calendar.MONTH) + 1 <= dateTo.get(Calendar.MONTH) + 1
                    && calendar.get(Calendar.YEAR) >= dateFrom.get(Calendar.YEAR)
                    && calendar.get(Calendar.YEAR) <= dateTo.get(Calendar.YEAR)) {
                localDateSet.add(localDate);
            }
        }

        for (String localDate : localDateSet) {
            double total = 0;
            for (Invoice invoice : LoginActivity.getInvoices()) {
                String ld = sdf.format(invoice.getCreateDate());
                if (localDate.equals(ld)) {
                    total += invoice.getTotalPrice();
                }
            }
            map.put(localDate, (float) total);
        }
        return map;
    }

    private void statistics(Calendar dateFrom, Calendar dateTo) {
        int totalInvoice = 0;
        double totalPrice = 0.0d;
//        for (Invoice invoice : LoginActivity.getInvoices()) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(invoice.getCreateDate());
//            if (calendar.get(Calendar.DAY_OF_MONTH) >= dateFrom.get(Calendar.DAY_OF_MONTH)
//                    && calendar.get(Calendar.DAY_OF_MONTH) <= dateTo.get(Calendar.DAY_OF_MONTH)
//                    && calendar.get(Calendar.MONTH) + 1 >= dateFrom.get(Calendar.MONTH) + 1
//                    && calendar.get(Calendar.MONTH) + 1 <= dateTo.get(Calendar.MONTH) + 1
//                    && calendar.get(Calendar.YEAR) >= dateFrom.get(Calendar.YEAR)
//                    && calendar.get(Calendar.YEAR) <= dateTo.get(Calendar.YEAR)) {
//                totalInvoice++;
//                totalPrice += invoice.getTotalPrice();
//            }
//        }
        if (totalInvoice != 0) {
            tv_total_bill.setText(String.valueOf(totalInvoice));
            tv_total_revenue.setText(formatter.format(totalPrice));
        }else {
            tv_total_bill.setText("0");
            tv_total_revenue.setText(formatter.format(totalPrice));
        }
    }

}
