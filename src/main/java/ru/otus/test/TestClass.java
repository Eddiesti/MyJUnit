package ru.otus.test;


import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClass {

    @Before
    public void before1() {
        System.out.println("before1");
    }

    @Before
    public void before2() {
        System.out.println("before2");
    }

    @Test
    public void test() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }
    @Test
    public void testE(){
        throw new RuntimeException();
    }
    @Test
    public void test3() {
        System.out.println("test3");
    }

    @After
    public void after1() {
        System.out.println("after1");
    }
    @After
    public void after2() {
        System.out.println("after2");
    }
}


