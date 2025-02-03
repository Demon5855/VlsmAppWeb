/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author beren
 */
package com.vlsm;

import java.util.Arrays;

public class VLSMCalculator {

    public static class Subred {
        public String nombre;
        public int hosts;
        public String ipRed;
        public String mascara;
        public String primerHost;
        public String ultimoHost;
        public String broadcast;

        public Subred(String nombre, int hosts, String ipRed, String mascara, String primerHost, String ultimoHost, String broadcast) {
            this.nombre = nombre;
            this.hosts = hosts;
            this.ipRed = ipRed;
            this.mascara = mascara;
            this.primerHost = primerHost;
            this.ultimoHost = ultimoHost;
            this.broadcast = broadcast;
        }
    }

    public static Subred[] calcularSubredes(String ipBase, int mascaraBase, int[] hosts) {
        Subred[] subredes = new Subred[hosts.length];
        int[] ip = convertirIPaEntero(ipBase); // Convertir la IP base a un array de enteros

        for (int i = 0; i < hosts.length; i++) {
            int bitsHosts = (int) Math.ceil(Math.log(hosts[i] + 2) / Math.log(2)); // Calcular bits necesarios para hosts
            int nuevaMascara = 32 - bitsHosts; // Calcular nueva máscara
            int tamañoSubred = (int) Math.pow(2, bitsHosts); // Tamaño de la subred

            // Calcular IP de red
            String ipRed = convertirEnteroaIP(ip);

            // Calcular primer host, último host y broadcast
            int[] primerHost = Arrays.copyOf(ip, ip.length);
            primerHost[3] += 1; // Primer host = IP de red + 1

            int[] ultimoHost = Arrays.copyOf(ip, ip.length);
            ultimoHost[3] += tamañoSubred - 2; // Último host = IP de red + tamañoSubred - 2

            int[] broadcast = Arrays.copyOf(ip, ip.length);
            broadcast[3] += tamañoSubred - 1; // Broadcast = IP de red + tamañoSubred - 1

            // Guardar resultados
            subredes[i] = new Subred(
                "Subred " + (i + 1),
                hosts[i],
                ipRed,
                "/" + nuevaMascara,
                convertirEnteroaIP(primerHost),
                convertirEnteroaIP(ultimoHost),
                convertirEnteroaIP(broadcast)
            );

            // Actualizar la IP base para la siguiente subred
            ip[3] += tamañoSubred; // Sumar el tamaño de la subred al último octeto
        }

        return subredes;
    }

    // Convertir una IP en formato String a un array de enteros
    private static int[] convertirIPaEntero(String ip) {
        String[] partes = ip.split("\\.");
        int[] resultado = new int[4];
        for (int i = 0; i < 4; i++) {
            resultado[i] = Integer.parseInt(partes[i]);
        }
        return resultado;
    }

    // Convertir un array de enteros a una IP en formato String
    private static String convertirEnteroaIP(int[] ip) {
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        String ipBase = "192.168.5.0";
        int mascaraBase = 24;
        int[] hosts = {5, 4, 5};

        Subred[] subredes = calcularSubredes(ipBase, mascaraBase, hosts);

        // Mostrar resultados
        for (Subred subred : subredes) {
            System.out.println("Subred: " + subred.nombre);
            System.out.println("Hosts: " + subred.hosts);
            System.out.println("IP de Red: " + subred.ipRed);
            System.out.println("Máscara: " + subred.mascara);
            System.out.println("Primer Host: " + subred.primerHost);
            System.out.println("Último Host: " + subred.ultimoHost);
            System.out.println("Broadcast: " + subred.broadcast);
            System.out.println();
        }
    }
}