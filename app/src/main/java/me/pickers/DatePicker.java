package me.pickers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

import static me.pickers.ExpiryPicker.getCurrentMonth;

public class DatePicker extends AppCompatActivity {

    View picker;
    Button pickdate;
    NumberPicker day, month, year;
    Calendar calendar;
    public static String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

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

        day.setMinValue(1);
        year.setMinValue(1900);

        year.setMaxValue(calendar.get(Calendar.YEAR));
        month.setMaxValue(selectMonthMaxValue(calendar, year.getValue()));
        day.setMaxValue(selectDayMaxValue(calendar, year.getValue(), month.getValue()));

        year.setValue(1991);
        month.setValue(1);
        day.setValue(1);

        month.setDisplayedValues(MONTHS);

        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month.setMaxValue(selectMonthMaxValue(calendar, newVal));
                day.setMaxValue(selectDayMaxValue(calendar, newVal, month.getValue()));
            }
        });

        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day.setMaxValue(selectDayMaxValue(calendar, year.getValue(), newVal));
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

    public static int selectMonthMaxValue(Calendar calendar, int yearValue) {
        if (yearValue == calendar.get(Calendar.YEAR)) {
            return getCurrentMonth(calendar.get(Calendar.MONTH));
        }
        return 12;
    }

    public static int selectDayMaxValue(Calendar calendar, int yearValue, int monthValue) {
        if (yearValue == calendar.get(Calendar.YEAR) && monthValue == getCurrentMonth(calendar.get(Calendar.MONTH))) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
        return pickMonthMaxDays(yearValue, monthValue);
    }

    public static int pickMonthMaxDays(int year, int month) {
        switch (month) {
            case 2:
                if (year % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
                return 30;
            case 6:
                return 30;
            case 9:
                return 30;
            case 11:
                return 30;
            default:
                return 31;
        }
    }
}
