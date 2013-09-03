package main;

import framework.classes.Program;
import program2d.program.Program2D;
import virtualcity3d.VirtualCity;

public class Main {
    public static void main(String[] argv) {
        createProgram().run();
    }

    public static final Program createProgram() {
        return new VirtualCity();
    }
}