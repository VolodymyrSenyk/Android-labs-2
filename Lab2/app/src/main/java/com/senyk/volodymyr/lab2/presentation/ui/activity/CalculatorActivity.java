package com.senyk.volodymyr.lab2.presentation.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textview.MaterialTextView;
import com.senyk.volodymyr.lab2.R;
import com.senyk.volodymyr.lab2.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.lab2.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.lab2.presentation.viewmodel.factory.ViewModelFactory;
import com.senyk.volodymyr.lab2.presentation.viewmodel.mapper.SymbolMapper;
import com.senyk.volodymyr.lab2.presentation.viewmodel.util.ResourcesProvider;

import javax.script.ScriptEngineManager;

public class CalculatorActivity extends AppCompatActivity {

    private static final String SCRIPT_ENGINE_SHORT_NAME = "rhino";

    private CalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(
                new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_SHORT_NAME),
                new ResourcesProvider(getResources()),
                new SymbolMapper(getApplicationContext())
        )).get(CalculatorViewModel.class);

        setContentView(R.layout.activity_calculator);

        setObservers();
        setClickListeners();
    }

    private void setObservers() {
        viewModel.currentEquation().observe(this, inputString -> {
            MaterialTextView field = findViewById(R.id.inputField);
            field.setText(inputString);
        });
        viewModel.currentResult().observe(this, outputString -> {
            MaterialTextView field = findViewById(R.id.outputField);
            field.setText(outputString);
        });
        viewModel.message().observe(this, message ->
                Toast.makeText(CalculatorActivity.this, message, Toast.LENGTH_LONG).show()
        );
    }

    private void setClickListeners() {
        setOnPartOfNumberClickListeners();
        setOnHelpersClickListeners();
        setOnOperatorClickListeners();
        setOnDelClickListeners();
        setOnFunctionClickListeners();
    }

    private void setOnPartOfNumberClickListeners() {
        findViewById(R.id.calcButton0).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.ZERO));
        findViewById(R.id.calcButton1).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.ONE));
        findViewById(R.id.calcButton2).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.TWO));
        findViewById(R.id.calcButton3).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.THREE));
        findViewById(R.id.calcButton4).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.FOUR));
        findViewById(R.id.calcButton5).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.FIVE));
        findViewById(R.id.calcButton6).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.SIX));
        findViewById(R.id.calcButton7).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.SEVEN));
        findViewById(R.id.calcButton8).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.EIGHT));
        findViewById(R.id.calcButton9).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.NINE));

        findViewById(R.id.calcButtonComa).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.COMA));
    }

    private void setOnOperatorClickListeners() {
        findViewById(R.id.calcButtonPlus).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.PLUS));
        findViewById(R.id.calcButtonMinus).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.MINUS));
        findViewById(R.id.calcButtonMult).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.MULTIPLICATION));
        findViewById(R.id.calcButtonDiv).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.DIVISION));
    }

    private void setOnHelpersClickListeners() {
        findViewById(R.id.calcButtonBrackets).setOnClickListener((v) -> viewModel.onBracketsClick());
        findViewById(R.id.calcButtonInverse).setOnClickListener((v) -> viewModel.onInverseClick());

        findViewById(R.id.calcButtonEqu).setOnClickListener((v) -> viewModel.onEquClick());
    }

    private void setOnDelClickListeners() {
        findViewById(R.id.calcButtonDel).setOnClickListener((v) -> viewModel.onDeleteClick());
        findViewById(R.id.calcButtonClear).setOnClickListener((v) -> viewModel.onClearClick());
    }

    private void setOnFunctionClickListeners() {
        findViewById(R.id.calcButtonRoot).setOnClickListener((v) -> viewModel.onSymbolClick(LogicalSymbol.ROOT));
    }
}
