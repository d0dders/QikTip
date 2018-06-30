package net.dodkins.qiktip;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    boolean roundUp = false;

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
                // Hide the damn keyboard
               ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        menu.findItem(R.id.roundUp).setChecked(roundUp);
           return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.roundUp:
               roundUp = !roundUp;
                item.setChecked(roundUp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void calculateTip(Double totalBill) {
        tipView = findViewById(R.id.tipView);
        Double tip = totalBill * 0.125;

        if (roundUp) {
            tip = Math.ceil(tip);
        }
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance();
        tipView.setText(numberFormat.format(tip));
    }


}
