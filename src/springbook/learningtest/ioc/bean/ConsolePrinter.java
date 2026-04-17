package springbook.learningtest.ioc.bean;

import springbook.learningtest.ioc.bean.Printer;

public class ConsolePrinter implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
