package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner sourceSpinner = findViewById(R.id.spinner_source_unit);
        final Spinner destinationSpinner = findViewById(R.id.spinner_destination_unit);
        final EditText valueEditText = findViewById(R.id.editText_value);
        Button convertButton = findViewById(R.id.button_convert);
        final TextView resultTextView = findViewById(R.id.textView_result);

        // Extending units to include length, weight, and temperature
        String[] units = {"Inch", "Foot", "Yard", "Mile", "Cm", "Km", // Length
                "Pound", "Ounce", "Ton", "Kg", "Gram", // Weight
                "Celsius", "Fahrenheit", "Kelvin"}; // Temperature
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        sourceSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceUnit = sourceSpinner.getSelectedItem().toString();
                String destinationUnit = destinationSpinner.getSelectedItem().toString();
                String valueStr = valueEditText.getText().toString();
                if (!valueStr.isEmpty()) {
                    try {
                        double value = Double.parseDouble(valueStr);
                        double result = convertUnits(sourceUnit, destinationUnit, value);
                        resultTextView.setText(String.format("Result: %.2f", result));
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Please enter a valid value", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double convertUnits(String sourceUnit, String destinationUnit, double value) {
        switch (sourceUnit + " to " + destinationUnit) {
            // Lengths
            case "Inch to Cm":
                return value * 2.54;
            case "Foot to Cm":
                return value * 30.48;
            case "Yard to Cm":
                return value * 91.44;
            case "Mile to Km":
                return value * 1.60934;
            case "Cm to Inch":
                return value / 2.54;
            case "Cm to Foot":
                return value / 30.48;
            case "Cm to Yard":
                return value / 91.44;
            case "Km to Mile":
                return value / 1.60934;

            // Weights
            case "Pound to Kg":
                return value * 0.453592;
            case "Ounce to Gram":
                return value * 28.3495;
            case "Ton to Kg":
                return value * 907.185;
            case "Kg to Pound":
                return value / 0.453592;
            case "Gram to Ounce":
                return value / 28.3495;
            case "Kg to Ton":
                return value / 907.185;

            // Temperatures
            case "Celsius to Fahrenheit":
                return (value * 1.8) + 32;
            case "Fahrenheit to Celsius":
                return (value - 32) / 1.8;
            case "Celsius to Kelvin":
                return value + 273.15;
            case "Kelvin to Celsius":
                return value - 273.15;
            case "Fahrenheit to Kelvin":
                return (value - 32) / 1.8 + 273.15;
            case "Kelvin to Fahrenheit":
                return ((value - 273.15) * 1.8) + 32;

            default:
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "This conversion is not supported YET!", Toast.LENGTH_SHORT).show());
                return 0;
        }
    }

}

