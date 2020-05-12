package com.senyk.volodymyr.lab3.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.senyk.volodymyr.lab3.data.entity.Question;
import com.senyk.volodymyr.lab3.data.util.FileReaderWriter;
import com.senyk.volodymyr.lab3.domain.repository.QuestionRepository;

public class QuestionFileRepository implements QuestionRepository {

    private static final String FILENAME = "question";

    private final FileReaderWriter readerWriter;

    public QuestionFileRepository(@NonNull final FileReaderWriter readerWriter) {
        this.readerWriter = readerWriter;
    }

    @Nullable
    @Override
    public Question getQuestion() {
        return readerWriter.readSerializableObjectFromFile(FILENAME);
    }

    @Override
    public void saveQuestion(@NonNull Question question) {
        readerWriter.writeSerializableObjectToFile(FILENAME, question);
    }

    @Override
    public void deleteQuestion() {
        readerWriter.writeSerializableObjectToFile(FILENAME, null);
    }
}
