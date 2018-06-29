package net.dodkins.qiktip;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private EditText billTotalInput;
    private Button calcTipButton;
    private TextView tipView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.layout);
        calcTipButton = findViewById(R.id.calcTipButton);
        ColorWheel colorWheel = new ColorWheel();
        int color = colorWheel.getColor();
        constraintLayout.setBackgroundColor(color);
        calcTipButton.setTextColor(color);

        billTotalInput = findViewById(R.id.billTotalInput);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calculate the tip to offer
                try {
                    Double totalBill = Double.valueOf(billTotalInput.getText().toString());
                    calculateTip(totalBill);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "If it's free, just get up and leave!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        };
        calcTipButton.setOnClickListener(listener);
    }

    private void calculateTip(Double totalBill) {
        tipView = findViewById(R.id.tipView);
        Double tip = totalBill * 0.125;

        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance();
        tipView.setText(numberFormat.format(tip));
    }
}
