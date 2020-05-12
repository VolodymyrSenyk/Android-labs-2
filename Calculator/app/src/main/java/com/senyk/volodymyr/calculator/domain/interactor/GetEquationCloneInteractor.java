package com.senyk.volodymyr.calculator.domain.interactor;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.repository.EquationRepository;

import javax.inject.Inject;

public class GetEquationCloneInteractor {

    private final EquationRepository repository;

    @Inject
    GetEquationCloneInteractor(EquationRepository equationRepository) {
        repository = equationRepository;
    }

    public LogicalEquation execute() {
        return repository.getEquationClone();
    }
}
