package com.senyk.volodymyr.calculator.domain.interactor;

import androidx.annotation.Nullable;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.domain.exception.NotValidEquationException;
import com.senyk.volodymyr.calculator.presentation.viewmodel.mapper.EquationMapper;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class EquationCalculateInteractor {

    private static final String EMPTY_STRING = "";
    private static final int RESULT_NUMBER_SCALE = 10;

    private final ScriptEngine scriptEngine;
    private final EquationMapper equationMapper;
    private final GetEquationCloneInteractor getEquationCloneInteractor;

    @Inject
    EquationCalculateInteractor(ScriptEngine scriptEngine, EquationMapper equationMapper, GetEquationCloneInteractor getEquationCloneInteractor) {
        this.scriptEngine = scriptEngine;
        this.equationMapper = equationMapper;
        this.getEquationCloneInteractor = getEquationCloneInteractor;
    }

    @Nullable
    public List<LogicalSymbol> execute() throws NotValidEquationException {
        LogicalEquation logicalEquationClone = getEquationCloneInteractor.execute();
        if (logicalEquationClone.isEmpty()) {
            return null;
        }
        String result;
        try {
            result = getCalculationsResult(logicalEquationClone);
            String formattedResult = formatResult(result);
            List<LogicalSymbol> logicalResult = equationMapper.convertToLogical(formattedResult);
            equationMapper.fixLogicalEquation(logicalEquationClone);
            return logicalResult;
        } catch (ScriptException e) {
            throw new NotValidEquationException();
        }
    }

    private String getCalculationsResult(final LogicalEquation logicalEquation) throws NotValidEquationException, ScriptException {
        String equation = equationMapper.convertToEquation(logicalEquation);
        Object scriptResult = scriptEngine.eval(equation);
        if (scriptResult == null) {
            throw new NotValidEquationException();
        } else {
            return scriptResult.toString();
        }
    }

    private String formatResult(String result) {
        try {
            BigDecimal decimal = new BigDecimal(result);
            decimal = decimal.setScale(RESULT_NUMBER_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
            if (decimal.compareTo(new BigDecimal(0)) == 0) {
                result = "0";
            } else {
                result = decimal.toPlainString();
            }
        } catch (NumberFormatException e) {
            result = EMPTY_STRING;
        }
        return result;
    }
}
