package net.dodkins.qiktip;

import android.app.Activity;
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

    private static final String KEY_COLOUR = "KEY_COLOUR";
    private static final String KEY_TOTAL = "KEY_TOTAL";
    private static final String KEY_TIP = "KEY_TIP";
    private ConstraintLayout constraintLayout;
    private EditText billTotalInput;
    private Button calcTipButton;
    private TextView tipView;
    private int mColor;
    private double mTotalBill;

    boolean roundUp = false;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTotalBill = savedInstanceState.getDouble(KEY_TOTAL);
        mColor = savedInstanceState.getInt(KEY_COLOUR);
        constraintLayout.setBackgroundColor(mColor);
        calcTipButton.setTextColor(mColor);
        tipView.setText(savedInstanceState.getString(KEY_TIP));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble(KEY_TOTAL, mTotalBill);
        outState.putInt(KEY_COLOUR, mColor);
        outState.putString(KEY_TIP, tipView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.layout);
        calcTipButton = findViewById(R.id.calcGoodTipButton);
        ColorWheel colorWheel = new ColorWheel();
        mColor = colorWheel.getColor();
        constraintLayout.setBackgroundColor(mColor);
        calcTipButton.setTextColor(mColor);
        tipView = findViewById(R.id.tipView);
        billTotalInput = findViewById(R.id.billTotalInput);

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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void calculateTip(Double totalBill, Double percentageAsDecimal) {
        //tipView = findViewById(R.id.tipView);
        Double tip = totalBill * percentageAsDecimal;

        if (roundUp) {
            tip = Math.ceil(tip);
        }
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance();
        tipView.setText(numberFormat.format(tip));
    }


    public void TipButtonHandler(View view) {
            hideKeyboard(MainActivity.this);

            // Calculate the tip to offer
            try {
                mTotalBill = Double.valueOf(billTotalInput.getText().toString());
                String viewTag = view.getTag().toString();
                if(viewTag.equals("calcOkTipButton"))
                {
                    calculateTip(mTotalBill, 0.1);
                }
                else if(viewTag.equals("calcGoodTipButton"))
                {
                    calculateTip(mTotalBill, 0.125);
                }
                else
                {
                    calculateTip(mTotalBill, 0.15);
                }

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "If it's free, just get up and leave!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }



    }
}
