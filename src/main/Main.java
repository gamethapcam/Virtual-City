package main;

import framework.classes.Program;
import program2d.program.Program2D;

public class Main {
    public static void main(String[] argv) {
        createProgram().run();
    }

    public static final Program createProgram() {
        return new Program2D();
    }
}