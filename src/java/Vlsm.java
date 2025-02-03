/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author beren
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Vlsm {
    private String ip;
    private int mascara;
    private int[] hosts;

    public Vlsm(String ip, int mascara, int[] hosts) {
        this.ip = ip;
        this.mascara = mascara;
        this.hosts = hosts;
    }

    public List<String[]> calcular() {
        List<String[]> resultado = new ArrayList<>();

        // Ordenar los hosts en orden descendente para asignar primero los más grandes
        Integer[] sortedHosts = Arrays.stream(hosts).boxed().toArray(Integer[]::new);
        Arrays.sort(sortedHosts, Collections.reverseOrder());

        String baseIp = ajustarDireccionRed(ip); // Asegurar que se empieza desde .0
        
        for (int i = 0; i < sortedHosts.length; i++) {
            int host = sortedHosts[i];
            int bits = (int) Math.ceil(Math.log(host + 2) / Math.log(2));
            int nuevaMascara = 32 - bits;
            String mascaraIP = convertirMascara(nuevaMascara);

            String ipRed = baseIp;
            String ipPrimera = incrementarIP(ipRed, 1);
            String ipUltima = incrementarIP(ipRed, (int) Math.pow(2, bits) - 2);
            String ipBroadcast = incrementarIP(ipRed, (int) Math.pow(2, bits) - 1);

            resultado.add(new String[]{
                "Subred " + (i + 1),
                ipRed,
                ipPrimera,
                ipUltima,
                ipBroadcast,
                mascaraIP
            });

            // La siguiente base IP debe ser la siguiente después del broadcast
            baseIp = incrementarIP(ipBroadcast, 1);
        }
        return resultado;
    }

    public List<String> generarResumenBinario() {
        List<String> resumen = new ArrayList<>();
        resumen.add("Desglose Binario del Cálculo VLSM:");
        resumen.add(" ");

        String baseIp = ajustarDireccionRed(ip);
        int bitsIniciales = mascara;
        int nivel = 0;

        for (int i = bitsIniciales; i <= 32; i++) { // Asegura que siga cada nivel hasta /32
            resumen.add(tabular(nivel) + convertirABinario(baseIp, i) + " /" + i);
            nivel++;
        }

        for (int i = 0; i < hosts.length; i++) {
            int bits = (int) Math.ceil(Math.log(hosts[i] + 2) / Math.log(2));
            int nuevaMascara = 32 - bits;

            String ipRed = baseIp;
            String ipPrimera = incrementarIP(ipRed, 1);
            String ipUltima = incrementarIP(ipRed, (int) Math.pow(2, bits) - 2);
            String ipBroadcast = incrementarIP(ipRed, (int) Math.pow(2, bits) - 1);

            resumen.add(tabular(nivel) + convertirABinario(ipRed, nuevaMascara) + " /" + nuevaMascara + " --> Subred " + (i + 1));
            nivel++;

            baseIp = incrementarIP(ipBroadcast, 1);
        }

        for (int i = 28; i >= bitsIniciales; i--) { // Regresa al nivel inicial
            nivel--;
            resumen.add(tabular(nivel) + convertirABinario(baseIp, i) + " /" + i + " <-- Regresando");
        }

        return resumen;
    }

    private String ajustarDireccionRed(String ip) {
        String[] octetos = ip.split("\\.");
        return octetos[0] + "." + octetos[1] + "." + octetos[2] + ".0";
    }

    private String convertirABinario(String ip, int prefijo) {
        String[] octetos = ip.split("\\.");
        StringBuilder binario = new StringBuilder();
        int bitSeparador = prefijo / 8;

        for (int i = 0; i < 4; i++) {
            if (i == bitSeparador) binario.append("|"); // Marca visual en el punto de subnetting
            binario.append(String.format("%8s", Integer.toBinaryString(Integer.parseInt(octetos[i]))).replace(' ', '0'));
            if (i < 3) binario.append(".");
        }
        return binario.toString();
    }

    private String convertirMascara(int prefijo) {
        int mask = 0xFFFFFFFF << (32 - prefijo);
        return ((mask >>> 24) & 0xFF) + "." +
               ((mask >>> 16) & 0xFF) + "." +
               ((mask >>> 8) & 0xFF) + "." +
               (mask & 0xFF);
    }

    private String incrementarIP(String ip, int incremento) {
        String[] octetos = ip.split("\\.");
        int[] octetosInt = new int[4];
        for (int i = 0; i < 4; i++) {
            octetosInt[i] = Integer.parseInt(octetos[i]);
        }

        int ipEntero = (octetosInt[0] << 24) | (octetosInt[1] << 16) | (octetosInt[2] << 8) | octetosInt[3];
        ipEntero += incremento;

        return ((ipEntero >> 24) & 0xFF) + "." + ((ipEntero >> 16) & 0xFF) + "." + ((ipEntero >> 8) & 0xFF) + "." + (ipEntero & 0xFF);
    }

    private String tabular(int nivel) {
        return " ".repeat(nivel * 4);
    }
}
