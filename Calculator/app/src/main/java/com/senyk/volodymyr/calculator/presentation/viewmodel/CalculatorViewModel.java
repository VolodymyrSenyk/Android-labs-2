package com.senyk.volodymyr.calculator.presentation.viewmodel;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;
import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.domain.exception.NotValidEquationException;
import com.senyk.volodymyr.calculator.domain.exception.NotValidPowUsageException;
import com.senyk.volodymyr.calculator.domain.interactor.EquationCalculateInteractor;
import com.senyk.volodymyr.calculator.domain.interactor.EquationInputInteractor;
import com.senyk.volodymyr.calculator.domain.interactor.GetEquationCloneInteractor;
import com.senyk.volodymyr.calculator.domain.interactor.HistoryInteractor;
import com.senyk.volodymyr.calculator.domain.repository.SettingsRepository;
import com.senyk.volodymyr.calculator.presentation.viewmodel.entity.HistoryEntryUi;
import com.senyk.volodymyr.calculator.presentation.viewmodel.mapper.CursorMapper;
import com.senyk.volodymyr.calculator.presentation.viewmodel.mapper.EquationMapper;
import com.senyk.volodymyr.calculator.presentation.viewmodel.mapper.HistoryMapper;
import com.senyk.volodymyr.calculator.presentation.viewmodel.util.ResourcesProvider;
import com.senyk.volodymyr.calculator.presentation.viewmodel.util.SingleEventLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CalculatorViewModel extends ViewModel {
    private final static String TAG = CalculatorViewModel.class.toString();
    private final static String ERROR_LOG_PATTERN = "An error occurred: ";
    private static final int LIGHT_THEME = AppCompatDelegate.MODE_NIGHT_NO;
    private static final int DARK_THEME = AppCompatDelegate.MODE_NIGHT_YES;

    private static final String EMPTY_STRING = "";
    private static final Integer DEFAULT_CURSOR_POSITION = 0;

    private static final Boolean DEFAULT_UNIT_DEGREES = true;

    private final SingleEventLiveData<Integer> appTheme = new SingleEventLiveData<>();

    public LiveData<Integer> appTheme() {
        return appTheme;
    }

    private final MutableLiveData<Boolean> currentThemeNight = new MutableLiveData<>(false);

    public LiveData<Boolean> isCurrentThemeNight() {
        return currentThemeNight;
    }

    private final MutableLiveData<Spannable> currentEquation = new MutableLiveData<>(new SpannableString(EMPTY_STRING));

    public LiveData<Spannable> currentEquation() {
        return currentEquation;
    }

    private final MutableLiveData<String> currentResult = new MutableLiveData<>(EMPTY_STRING);

    public LiveData<String> currentResult() {
        return currentResult;
    }

    private final MutableLiveData<Integer> currentCursorPosition = new MutableLiveData<>(DEFAULT_CURSOR_POSITION);

    public LiveData<Integer> currentCursorPosition() {
        return currentCursorPosition;
    }

    private final MutableLiveData<Boolean> currentModeFull = new MutableLiveData<>(false);

    public LiveData<Boolean> isCurrentModeFull() {
        return currentModeFull;
    }

    private final MutableLiveData<Boolean> currentUnitDegrees = new MutableLiveData<>(DEFAULT_UNIT_DEGREES);

    public LiveData<Boolean> isCurrentUnitDegrees() {
        return currentUnitDegrees;
    }

    private final MutableLiveData<Boolean> showingHistory = new MutableLiveData<>(false);

    public LiveData<Boolean> isShowingHistory() {
        return showingHistory;
    }

    private final MutableLiveData<List<HistoryEntryUi>> history = new MutableLiveData<>();

    public LiveData<List<HistoryEntryUi>> history() {
        return history;
    }

    private final SingleEventLiveData<String> message = new SingleEventLiveData<>();

    public LiveData<String> message() {
        return message;
    }

    private final ResourcesProvider resourcesProvider;
    private final SettingsRepository settingsRepository;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final EquationMapper equationMapper;
    private final CursorMapper cursorMapper;
    private final HistoryMapper historyMapper;

    private final GetEquationCloneInteractor getEquationCloneInteractor;
    private final EquationInputInteractor equationInputInteractor;
    private final EquationCalculateInteractor equationCalculateInteractor;
    private final HistoryInteractor historyInteractor;

    private List<HistoryEntry> historyList = new ArrayList<>();

    @Inject
    public CalculatorViewModel(
            ResourcesProvider resourcesProvider,
            SettingsRepository settingsRepository,
            HistoryMapper historyMapper,
            EquationMapper equationMapper,
            CursorMapper cursorMapper,
            GetEquationCloneInteractor getEquationCloneInteractor,
            EquationInputInteractor equationInputInteractor,
            EquationCalculateInteractor equationCalculateInteractor,
            HistoryInteractor historyInteractor) {
        this.resourcesProvider = resourcesProvider;
        this.settingsRepository = settingsRepository;
        this.equationMapper = equationMapper;
        this.cursorMapper = cursorMapper;
        this.historyMapper = historyMapper;
        this.getEquationCloneInteractor = getEquationCloneInteractor;
        this.equationInputInteractor = equationInputInteractor;
        this.equationCalculateInteractor = equationCalculateInteractor;
        this.historyInteractor = historyInteractor;
        getHistory();
    }

    public void onAppThemeApply() {
        appTheme.setValue(settingsRepository.getAppTheme(LIGHT_THEME));
    }

    public void onAppThemeChanged(final Resources.Theme theme) {
        equationMapper.getSymbolMapper().setTheme(theme);
        historyMapper.getSymbolMapper().setTheme(theme);
        handleChangeThemeButtonIcon();
        updateUi();
    }

    public void onAppThemeChangeClick() {
        if (settingsRepository.getAppTheme(LIGHT_THEME) == LIGHT_THEME) {
            settingsRepository.saveAppTheme(DARK_THEME);
            appTheme.setValue(DARK_THEME);
        } else {
            settingsRepository.saveAppTheme(LIGHT_THEME);
            appTheme.setValue(LIGHT_THEME);
        }
    }

    public void onModeChangeClick() {
        showingHistory.setValue(false);
        if (currentModeFull.getValue() != null && currentModeFull.getValue()) {
            currentModeFull.setValue(false);
        } else {
            currentModeFull.setValue(true);
        }
    }

    public void onShowHistoryClick() {
        showingHistory.setValue(true);
    }

    public void onHideHistoryClick() {
        showingHistory.setValue(false);
    }

    public void onClearHistoryClick() {
        historyInteractor.clearHistory();
        showingHistory.setValue(false);
    }

    public void onHistoryEntryClick(String uiEntry) {
        try {
            equationInputInteractor.addOtherEquation(historyMapper.convertToLogical(uiEntry));
        } catch (NotValidPowUsageException e) {
            message.setValue(resourcesProvider.getNotValidPowUsageMessage());
        }
        updateUi();
    }

    public void onCursorPositionChanged(int newCursorPosition) {
        equationInputInteractor.changeCursorPosition(newCursorPosition);
    }

    public void onUnitChangeClick() {
        if (currentUnitDegrees.getValue() != null) {
            equationInputInteractor.changeUnit(!currentUnitDegrees.getValue());
            currentUnitDegrees.setValue(!currentUnitDegrees.getValue());
        }
        updateUi();
    }

    public void onSymbolClick(LogicalSymbol symbol) {
        try {
            equationInputInteractor.addSymbol(symbol);
        } catch (NotValidPowUsageException e) {
            message.setValue(resourcesProvider.getNotValidPowUsageMessage());
        }
        updateUi();
    }

    public void onInverseClick() {
        equationInputInteractor.inverse();
        updateUi();
    }

    public void onBracketsClick() {
        equationInputInteractor.addBracket();
        updateUi();
    }

    public void onDeleteClick() {
        equationInputInteractor.delete();
        updateUi();
    }

    public void onTextPasted(String pastedText) {
        if (equationMapper == null) {
            return;
        }
        List<LogicalSymbol> pastedEquation = equationMapper.convertToLogical(pastedText);
        try {
            equationInputInteractor.addOtherEquation(pastedEquation);
        } catch (NotValidPowUsageException e) {
            message.setValue(resourcesProvider.getNotValidPowUsageMessage());
        }
        updateUi();
    }

    public void onEquClick() {
        performCalculations();
        updateUi();
    }

    public void onClearClick() {
        equationInputInteractor.resetEquation();
        updateUi();
    }

    // todo: not implemented
    public void onReplaceFunctionsClick() {
        message.setValue(resourcesProvider.getNotImplementedMessage());
    }

    private void handleChangeThemeButtonIcon() {
        int currentAppTheme = settingsRepository.getAppTheme(LIGHT_THEME);
        currentThemeNight.setValue(currentAppTheme != LIGHT_THEME);
    }

    private void updateUi() {
        LogicalEquation logicalEquation = getEquationCloneInteractor.execute();
        currentEquation.setValue(equationMapper.convertToUi(logicalEquation));
        currentCursorPosition.setValue(cursorMapper.convertToUi(logicalEquation));
        history.setValue(historyMapper.convertToUi(historyList));
        performRuntimeCalculations();
    }

    private void performRuntimeCalculations() {
        try {
            List<LogicalSymbol> logicalResult = equationCalculateInteractor.execute();
            if (logicalResult == null) {
                currentResult.setValue(EMPTY_STRING);
            } else {
                String uiResult = equationMapper.convertToUi(logicalResult).toString();
                currentResult.setValue(uiResult);
            }
        } catch (NotValidEquationException e) {
            currentResult.setValue(EMPTY_STRING);
        }
    }

    private void performCalculations() {
        try {
            currentResult.setValue(EMPTY_STRING);
            List<LogicalSymbol> logicalResult = equationCalculateInteractor.execute();
            if (logicalResult == null) {
                message.setValue(resourcesProvider.getNotValidEquationErrorMessage());
            } else {
                LogicalEquation logicalEquationClone = getEquationCloneInteractor.execute();
                if (!logicalEquationClone.getText().equals(logicalResult)) {
                    historyInteractor.addHistoryEntry(historyMapper.convertToDomain(logicalEquationClone.getText(), logicalResult));
                }
                equationInputInteractor.resetEquation();
                equationInputInteractor.addOtherEquation(logicalResult);
            }
        } catch (NotValidPowUsageException e) {
            message.setValue(resourcesProvider.getNotValidPowUsageMessage());
        } catch (NotValidEquationException e) {
            message.setValue(resourcesProvider.getNotValidEquationErrorMessage());
        }
    }

    private void getHistory() {
        historyInteractor.getHistory().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HistoryEntry>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<HistoryEntry> historyEntries) {
                        historyList = historyEntries;
                        history.setValue(historyMapper.convertToUi(historyList));
                    }

                    @Override
                    public void onError(Throwable exception) {
                        Log.e(TAG, ERROR_LOG_PATTERN + exception.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
