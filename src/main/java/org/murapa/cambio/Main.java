package org.murapa.cambio;

import org.murapa.cambio.controller.Controller;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Creamos un nuevo controlador y le pasamos los argumentos.
        Controller controller = new Controller();
        controller.execute(args);
    }
}