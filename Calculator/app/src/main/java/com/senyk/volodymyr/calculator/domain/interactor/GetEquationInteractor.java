package com.senyk.volodymyr.calculator.domain.interactor;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.repository.EquationRepository;

import javax.inject.Inject;

public class GetEquationInteractor {

    private final EquationRepository repository;

    @Inject
    GetEquationInteractor(EquationRepository equationRepository) {
        repository = equationRepository;
    }

    // only interactors can change equation, so assess is package-private
    LogicalEquation execute() {
        return repository.getEquation();
    }
}
