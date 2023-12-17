package org.murapa.cambio.controller;
import org.murapa.cambio.model.*;
import org.murapa.cambio.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Controller {

    boolean isTraceEnabled = false;
    int amount;
    ArrayList<Coin> availableCoins;

    View view = new View();
    Logic logic;


    String inputFilePath;
    String outputFilePath;



    public void execute(String args[]) {

        //Leemos los argumentos de la linea de comandos
        this.parseArgs(args);

        //Creamos el StringBuilder con los datos de entrada, si no hay fichero de entrada, se leen de la entrada estandar
        StringBuilder inputData = new StringBuilder();


        if(inputFilePath != null){
            try {
                File inputFile = new File(inputFilePath);
            //Leemos el fichero de entrada
            //Snippet de: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
                Scanner sc = new Scanner(inputFile);
                while (sc.hasNextLine())
                    inputData.append(sc.nextLine() + "\n");
                sc.close();
            }catch (FileNotFoundException e){
                view.showError("No se ha encontrado el fichero de entrada");
                System.exit(1);
            }
        }else{
            inputData.append(view.readUserInput());
        }

        //Parseamos los datos de entrada
        this.parseInput(inputData.toString());

        if(isTraceEnabled){
            //Creamos el algoritmo con traza
            view.showInfo("Ejecutando algoritmo con traza");
            logic = new DynamicTraceLogic(this.amount, this.availableCoins);
        }else{
            //Creamos el algoritmo sin traza
            view.showInfo("Ejecutando algoritmo sin traza");
            logic = new DynamicLogic(this.amount, this.availableCoins);
        }

        //Ejecutamos el algoritmo
        String output = logic.change();

        if(outputFilePath != null){
            //Escribimos en el fichero de salida
            File outputFile = new File(outputFilePath);
            try {
                //Snippet de https://www.w3schools.com/java/java_files_create.asp
                outputFile.createNewFile();
                FileWriter writer = new FileWriter(outputFile);
                writer.write(output);
                writer.close();
            }catch (Exception e){
                view.showError("No se ha podido crear el fichero de salida");
                System.exit(1);
            }
        }
            //Escribimos en la salida estandar
        System.out.println(output);

    }


    public int getAmount(){
        return amount;
    }

    public ArrayList<Coin> getAvailableCoins(){
        return availableCoins;
    }

    public boolean getisTraceEnabled(){
        return isTraceEnabled;
    }

    public void setisTraceEnabled(boolean isTraceEnabled){
        this.isTraceEnabled = isTraceEnabled;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setAvailableCoins(ArrayList<Coin> availableCoins){
        this.availableCoins = availableCoins;
    }



    private void parseArgs(String[] args) {

        //Creamos una copia de los argumentos, pero en un ArrayList.
        //El motivo de esto es para ir borrando los argumentos que ya hemos procesado
        // Los dos ultimos argumentos son los ficheros de entrada y salida.
        ArrayList<String> argsList = new ArrayList<String>();

        if(args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-t":
                        isTraceEnabled = true;
                        break;
                    case "-h":
                        view.showHelp();
                        System.exit(0);
                    default:
                        argsList.add(args[i]);
                        break;
                }
            }
        }

        //Si hay mas de dos argumentos, es que hay fichero de entrada y salida
        if(argsList.size() > 1){
            inputFilePath = argsList.get(argsList.size()-2);
            outputFilePath = argsList.get(argsList.size()-1);
        }

        //Si hay un solo argumento, es que es el fichero de entrada
        if(argsList.size() == 1){
            inputFilePath = argsList.get(argsList.size()-1);
        }

        //Si no hay argumentos, es que no hay fichero de entrada ni salida
        if(argsList.size() == 0){
            inputFilePath = null;
            outputFilePath = null;
        }
    }

    private void parseInput(String inputData){
        //Separamos las lineas en un array de strings
        String[] lines = inputData.split("\n");
        System.out.println(inputData);
        System.out.flush();
        //La primera linea es el numero de monedas, solo lo comprobamos en la vista.
        //Parseamos las monedas
        String[] coins = lines[1].split(" ");
        availableCoins = new ArrayList<Coin>();
        for(int i = 0; i < coins.length; i++){
            availableCoins.add(new Coin(Integer.parseInt(coins[i])));
        }

        //Parseamos la cantidad a cambiar
        amount = Integer.parseInt(lines[2]);
    }



}
