package com.senyk.volodymyr.calculator.data.repository;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.repository.EquationRepository;

import javax.inject.Inject;

public class EquationInMemoryRepository implements EquationRepository {

    private final LogicalEquation equation = new LogicalEquation();

    @Inject
    EquationInMemoryRepository() {
    }

    @Override
    public LogicalEquation getEquation() {
        return equation;
    }

    @Override
    public LogicalEquation getEquationClone() {
        return equation.clone();
    }
}
