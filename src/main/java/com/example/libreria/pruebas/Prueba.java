package com.example.libreria.pruebas;

import com.example.libreria.modelo.entidades.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prueba {
    private int rango = 0;
    private int[][] L;
    private int[] C;
    public int[] D;
    private int trango = 0;

    public Prueba(int paramRango, int[][] paramArreglo) {
        rango = paramRango;
        L = new int[rango][rango];
        C = new int[rango];
        D = new int[rango];

        for (int i = 0; i < rango; i++) {
            for (int j = 0; j < rango; j++) {
                L[i][j] = paramArreglo[i][j];
            }
        }

        for (int i = 0; i < rango; i++) {
            D[i] = L[0][i];
        }
    }

    public void SolDijkstra() {
        int minValor = Integer.MAX_VALUE;
        int minNodo = 0;

        for (int i = 0; i < rango; i++) {
            if (C[i] == -1) {
                continue;
            }

            if (D[i] > 0 && D[i] < minValor) {
                minValor = D[i];
                minNodo = i;
            }
        }

        C[minNodo] = -1;

        for (int i = 0; i < rango; i++) {
            if (L[minNodo][i] == 0) {
                continue;
            }

            if (D[i] < 0) {
                D[i] = minValor + L[minNodo][i];
                continue;
            }

            if ((D[minNodo] + L[minNodo][i]) <= D[i]) {
                D[i] = minValor + L[minNodo][i];
            }
        }
    }

    public void CorrerDijkstra() {
        for (trango = 1; trango < rango; trango++) {
            SolDijkstra();
            System.out.println("Iteracion No." + trango);
            System.out.println("Matriz de distancias: ");

            for (int i = 0; i < rango; i++) {
                System.out.print(i + " ");
            }

            System.out.println();

            for (int i = 0; i < rango; i++) {
                System.out.print(D[i] + " ");
            }

            System.out.println();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] L = {
                {-1, 10, 18, -1, -1, -1, -1},
                {-1, -1, 6, -1, 3, -1, -1},
                {-1, -1, -1, 3, -1, 20, -1},
                {-1, -1, 2, -1, -1, -1, 2},
                {-1, -1, -1, 6, -1, -1, 10},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, 10, -1, -1, 5, -1}
        };

        Prueba prueba = new Prueba((int) Math.sqrt(L.length), L);
        prueba.CorrerDijkstra();

        System.out.println("La solucion de la ruta mas corta tomando como nodo inicial el NODO 1 es: ");
        int nodo = 1;

        for (int i : prueba.D) {
            System.out.println("Distancia minima a nodo " + nodo + " es " + i);
            nodo++;
        }

        System.out.println();
        System.out.println("Presione la tecla Enter para salir.");
        // Java does not have Console.Read(), so you may use Scanner or other methods for input if needed.
    }


}
