package edu.pdx.cs410J.jgolds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(view -> addOperandsAndDisplaySum());

    }

    private void addOperandsAndDisplaySum(){
        EditText leftOperand = findViewById(R.id.left_operand);
        EditText rightOperand = findViewById(R.id.right_operand);

        double leftNumber = Double.parseDouble(leftOperand.getText().toString());
        double rightNumber = Double.parseDouble(rightOperand.getText().toString());

        TextView sum = findViewById(R.id.sum);
        sum.setText(String.valueOf(leftNumber + rightNumber));

    }

}











