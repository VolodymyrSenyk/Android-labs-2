package com.senyk.volodymyr.calculator.domain.repository;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;

public interface EquationRepository {

    LogicalEquation getEquation();

    LogicalEquation getEquationClone();
}
