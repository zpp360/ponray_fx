package com.ponray.task;

import com.ponray.entity.TestData;
import com.ponray.main.Main;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class DataTask extends ScheduledService<List<TestData>> {
    @Override
    protected Task<List<TestData>> createTask() {
        Task<List<TestData>> task = new Task<List<TestData>>() {
            @Override
            protected List<TestData> call() throws Exception {
                List<TestData> list = new ArrayList<>();
                return Main.dataList;
            }
        };
        return task;
    }
}
