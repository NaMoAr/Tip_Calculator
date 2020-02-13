package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {


    //Instance variables
    //bridge the view and model
    private Bill currentBill;
    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;

    //instance variables to format output (currency and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //"wire up" instance variables (initiate them)
        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        //let's set the current tip percentage
        currentBill.setTipPercent(percentSeekBar.getProgress()/ 100.0);

//implement the interface for the EditText
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Read inout frn EditText and store in the currentBill(Model)
                try {
                    double amount = Double.parseDouble(amountEditText.getText().toString());
                    //store the amount in the bill
                    currentBill.setAmount(amount);
                }catch(NumberFormatException e){
                    currentBill.setAmount(0.0);
                }
                calculateBill();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //implement the interface for SeekBar
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //update tip percent
                currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);
                //update the view
                percentTextView.setText(percent.format(currentBill.getTipPercent()));
                calculateBill();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calculateBill(){

        //update the tioTextView and totalTextView
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));



    }


}
