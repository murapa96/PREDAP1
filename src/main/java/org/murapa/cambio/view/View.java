package org.murapa.cambio.view;

import org.murapa.cambio.model.Coin;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public void showError(String message){
        System.out.printf("!ERROR: %s\n", message);
    }

    public void showInfo(String message){
        System.out.printf("*INFO: %s\n", message);
    }

    public String readUserInput(){
        showInfo("Introduce el número de monedas que tiene tu sistema monetario: ");
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        showInfo("Introduce los valores de las monedas separados por espacios:");

        String secondLine = scanner.nextLine();
        if(secondLine.split(" ").length != Integer.parseInt(firstLine)){
            showError("El número de monedas no coincide con el número de valores");
            System.exit(1);
        }
        showInfo("Introduce la cantidad a cambiar");
        String thirdLine = scanner.nextLine();
        return firstLine + "\n" + secondLine + "\n" + thirdLine;
    }

    public void showHelp(){
        System.out.println("CAMBIO Dinamica");
        System.out.println("SINTAXIS: cambio-dinamica [-t][-] [fichero entrada] [fichero salida]");
        System.out.println("-t \t Traza el algoritmo");
        System.out.println("-h \t Muestra esta ayuda");
        System.out.println("[fichero entrada] \t Fichero de entrada");
        System.out.println("[fichero salida] \t Fichero de salida");
    }


}
