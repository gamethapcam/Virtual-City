package main;

import framework.core.architecture.Program;
import virtualcity3d.VirtualCity;

public class Main {
    public static void main(String[] argv) {
        createProgram().run();
    }

    public static final Program createProgram() {
        return new VirtualCity();
    }
}