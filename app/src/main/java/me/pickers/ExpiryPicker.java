package me.pickers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

import static me.pickers.DatePicker.MONTHS;
import static me.pickers.DatePicker.pickMonthMaxDays;

public class ExpiryPicker extends AppCompatActivity {

    View picker;
    Button pickdate;
    NumberPicker day, month, year;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);

        picker = findViewById(R.id.dp_picker);
        pickdate = findViewById(R.id.dp_pick);

        day = picker.findViewById(R.id.day);
        month = picker.findViewById(R.id.month);
        year = picker.findViewById(R.id.year);

        calendar = Calendar.getInstance();

        day.setMinValue(selectDayMinValue(calendar, calendar.get(Calendar.YEAR), getCurrentMonth(calendar.get(Calendar.MONTH))));
        year.setMinValue(calendar.get(Calendar.YEAR));

        day.setMaxValue(pickMonthMaxDays(calendar.get(Calendar.YEAR), getCurrentMonth(calendar.get(Calendar.MONTH))));
        year.setMaxValue(2099);
        month.setMaxValue(12);

        year.setValue(calendar.get(Calendar.YEAR));
        month.setValue(getCurrentMonth(calendar.get(Calendar.MONTH)));
        day.setValue(calendar.get(Calendar.DAY_OF_MONTH));

        month.setDisplayedValues(MONTHS);

        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month.setMinValue(selectMonthMinValue(calendar, newVal));
                day.setMinValue(selectDayMinValue(calendar, newVal, month.getValue()));
                day.setMaxValue(pickMonthMaxDays(calendar.get(Calendar.YEAR), getCurrentMonth(calendar.get(Calendar.MONTH))));
            }
        });

        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day.setMinValue(selectDayMinValue(calendar, year.getValue(), newVal));
                day.setMaxValue(pickMonthMaxDays(calendar.get(Calendar.YEAR), getCurrentMonth(calendar.get(Calendar.MONTH))));
            }
        });

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Selected Date is: " +
                        day.getValue() + "-" + month.getValue() + "-" + year.getValue(), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    //for some reason calendar is returning 0 for JAN, 1 for FEB and so on...
    public static int getCurrentMonth(int month) {
            month++
        return month;
    }

    int selectDayMinValue(Calendar calendar, int year, int month) {
        if (year == calendar.get(Calendar.YEAR) && month == getCurrentMonth(calendar.get(Calendar.MONTH))) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
        return 1;
    }

    int selectMonthMinValue(Calendar calendar, int year) {
        if (year == calendar.get(Calendar.YEAR)) {
            return getCurrentMonth(calendar.get(Calendar.MONTH));
        }
        return 1;
    }

}
