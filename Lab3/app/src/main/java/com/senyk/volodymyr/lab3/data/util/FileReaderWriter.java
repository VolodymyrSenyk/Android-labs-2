package com.senyk.volodymyr.lab3.data.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileReaderWriter {
    private static final String TAG = FileReaderWriter.class.getSimpleName();
    private static final String ERROR_OUTPUT_PATTERN = "An error occurred: ";

    private final Context context;

    public FileReaderWriter(@NonNull final Context context) {
        this.context = context;
    }

    public <T> boolean writeSerializableObjectToFile(@NonNull String filename, @Nullable T object) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException exception) {
            Log.e(TAG, ERROR_OUTPUT_PATTERN + exception.getMessage());
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T readSerializableObjectFromFile(@NonNull String filename) {
        T object = null;
        try (FileInputStream fileInputStream = context.openFileInput(filename)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                object = (T) objectInputStream.readObject();
            }
        } catch (ClassNotFoundException | IOException exception) {
            Log.e(TAG, ERROR_OUTPUT_PATTERN + exception.getMessage());
        }
        return object;
    }
}
