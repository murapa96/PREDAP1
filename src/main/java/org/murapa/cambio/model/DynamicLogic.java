package org.murapa.cambio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamicLogic implements Logic {


    ArrayList<Coin> availableCoins;

    int amount;
    int coinNumber;

    int[][] changeTable;

    public DynamicLogic(int amount, ArrayList<Coin> coins) {
        this.availableCoins = coins;
        this.amount = amount;
        this.coinNumber = this.availableCoins.size();

        //Instanciamos la tabla y el vector de cambio
        changeTable = new int[coinNumber][amount + 1 ];

        //Inicializamos la tabla a MAX_VALUE

        for (int i = 0; i < coinNumber; i++) {
            for (int j = 0; j < amount + 1; j++) {
                changeTable[i][j] = Integer.MAX_VALUE;
            }
        }

        //Inicializamos la primera columna de la tabla a 0
        for (int i = 0; i < coinNumber; i++) {
            changeTable[i][0] = 0;
        }


    }

    public String change() {

        this.computeTable();

        ArrayList<Coin> change = getChange();

        StringBuilder result = new StringBuilder();
        result.append(change.size());
        result.append("\n");
        for (Coin coin : change) {
            result.append(coin.getValue());
            result.append(" ");
        }

        return result.toString();
    }

    ;

    private void computeTable() {
        //Basandonos en el algoritmo de la sección 5.3 del texto base.

        for (int j = 1; j <= this.amount; j++) {
            for (int i = 0; i < this.coinNumber; i++) {
                Coin coin = this.availableCoins.get(i);
                if (i == 0  && coin.getValue() < j) {
                    // Si es la primera moneda y el valor de la moneda es mayor que la cantidad a cambiar, no se puede cambiar
                    changeTable[i][j] = Integer.MAX_VALUE;
                } else {
                    if (i == 0) {
                        //Si es la primera moneda y el valor de la moneda es menor que la cantidad a cambiar, calculamos el cambio
                        changeTable[i][j] = 1 + changeTable[0][j - coin.getValue()];
                    } else {
                        if (j < coin.getValue()) {
                            //Si el valor de la moneda es mayor que la cantidad a cambiar, ponemos el mismo valor que en la fila anterior
                            changeTable[i][j] = changeTable[i - 1][j];
                        } else {
                            //Se elige al número más pequeño entre el valor de la fila anterior y el valor de la fila actual.
                            changeTable[i][j] = Math.min(changeTable[i - 1][j], changeTable[i][j - coin.getValue()]+1);
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Coin> getChange() {
        ArrayList<Coin> change = new ArrayList<Coin>();

        int i = coinNumber - 1;
        int j = amount;

        while (j > 0) {
            if (i > 1 && changeTable[i][j] == changeTable[i - 1][j]) {
                i--;
            } else {
                change.add(availableCoins.get(i));
                j = j - availableCoins.get(i).getValue();
            }
        }

        return change;
    }

}
