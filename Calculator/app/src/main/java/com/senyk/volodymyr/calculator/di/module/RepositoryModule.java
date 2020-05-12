package com.senyk.volodymyr.calculator.di.module;

import com.senyk.volodymyr.calculator.data.repository.EquationInMemoryRepository;
import com.senyk.volodymyr.calculator.data.repository.HistoryRoomRepository;
import com.senyk.volodymyr.calculator.data.repository.SettingsSharedPrefsRepository;
import com.senyk.volodymyr.calculator.domain.repository.EquationRepository;
import com.senyk.volodymyr.calculator.domain.repository.HistoryRepository;
import com.senyk.volodymyr.calculator.domain.repository.SettingsRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {PersistenceModule.class})
public interface RepositoryModule {

    @Singleton
    @Binds
    SettingsRepository bindSettingsRepository(SettingsSharedPrefsRepository repository);

    @Singleton
    @Binds
    HistoryRepository bindHistoryRepository(HistoryRoomRepository repository);

    @Singleton
    @Binds
    EquationRepository bindEquationRepository(EquationInMemoryRepository repository);
}
