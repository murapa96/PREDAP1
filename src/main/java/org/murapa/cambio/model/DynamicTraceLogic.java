package org.murapa.cambio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamicTraceLogic implements Logic {


    ArrayList<Coin> availableCoins;

    int amount;
    int coinNumber;

    int[][] changeTable;

    // Trace
    StringBuilder trace = new StringBuilder();

    public DynamicTraceLogic(int amount, ArrayList<Coin> coins) {
        this.availableCoins = coins;
        this.amount = amount;
        this.coinNumber = this.availableCoins.size();

        //Instanciamos la tabla y el vector de cambio
        changeTable = new int[coinNumber][amount + 1];

        //Inicializamos la primera columna de la tabla a 0
        for (int i = 0; i < coinNumber; i++) {
            changeTable[i][0] = 0;
        }
    }

    public String change() {

        boolean isChangeable = computeTable();

        if (!isChangeable) {
            this.trace.append("!ERROR: No hay solución posible");
            return this.trace.toString();
        }

        ArrayList<Coin> change = getChange();

        StringBuilder result = new StringBuilder();
        result.append(change.size());
        result.append("\n");
        for (Coin coin : change) {
            result.append(coin.getValue());
            result.append(" ");
        }

        return trace.toString() + result.toString();
    }

    ;

    private boolean computeTable() {
        //Basandonos en el algoritmo de la sección 5.3 del texto base.

        for (int j = 1; j <= this.amount; j++) {
          for(int i = 0; i < this.coinNumber; i++) {
                Coin coin = availableCoins.get(i);
                this.trace.append("?TRACE: j = " + j + ", coin = " + coin.getValue() + "\n");
                if (i == 0 && coin.getValue() > j) {
                    // Si es la primera moneda y el valor de la moneda es mayor que la cantidad a cambiar, no se puede cambiar
                    this.trace.append("!TRACE: INFINITO, SIN SOLUCION\n");
                } else {
                    if (i == 0) {
                        this.trace.append("!TRACE:  i = 0 && coin.getValue() <= j\n");
                        //Si es la primera moneda y el valor de la moneda es menor que la cantidad a cambiar, calculamos el cambio
                        changeTable[i][j] = 1 + changeTable[i][j - coin.getValue()];
                    } else {
                        if (j < coin.getValue()) {
                            this.trace.append("!TRACE: j < coin.getValue()\n");
                            //Si el valor de la moneda es mayor que la cantidad a cambiar, ponemos el mismo valor que en la fila anterior
                            changeTable[i][j] = changeTable[i - 1][j];
                        } else {
                            this.trace.append("!TRACE: j >= coin.getValue()\n");
                            //Se elige al número más pequeño entre el valor de la fila anterior y el valor de la fila actual, en la columna de cantidad - valor de la moneda
                            changeTable[i][j] = Math.min(changeTable[i - 1][j], changeTable[i][j - coin.getValue()] + 1);
                        }
                    }
                }
            }
        }
        return true;
    }

    private ArrayList<Coin> getChange() {
        ArrayList<Coin> change = new ArrayList<Coin>();
        this.trace.append("?TRACE: OBTENIENDO CAMBIO DE LA TABLA \n");
        int i = coinNumber - 1;
        int j = amount;

        while (j > 0) {
            this.trace.append("?TRACE: j = " + j + ", i = " + i + "\n");
            if (i > 1 && changeTable[i][j] == changeTable[i - 1][j]) {
                this.trace.append("!TRACE: i > 1 && changeTable[i][j] == changeTable[i - 1][j]\n");
                i--;
            } else {
                this.trace.append("!TRACE: i <= 1 || changeTable[i][j] != changeTable[i - 1][j]\n");
                change.add(availableCoins.get(i));
                j = j - availableCoins.get(i).getValue();
            }
        }

        return change;
    }

}
