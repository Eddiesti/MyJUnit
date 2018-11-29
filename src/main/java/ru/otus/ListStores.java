package ru.otus;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

public class ListStores {
    private List<Method> beforeList;
    private List<Method> testList;
    private List<Method> afterList;

    public ListStores() {
        this.beforeList = new ArrayList<Method>();
        this.testList = new ArrayList<Method>();
        this.afterList = new ArrayList<Method>();
    }

    public List<Method> getBeforeList() {
        return beforeList;
    }

    public List<Method> getTestList() {
        return testList;
    }

    public List<Method> getAfterList() {
        return afterList;
    }
}
