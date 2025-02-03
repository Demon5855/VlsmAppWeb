/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author beren
 */
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@WebServlet(name = "CalculadoraServlet", urlPatterns = {"/CalculadoraServlet"})
public class CalculadoraServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String ip = request.getParameter("ip");
        int mascara = Integer.parseInt(request.getParameter("mascara"));
        String[] hostsStr = request.getParameterValues("hosts");

        if (ip == null || hostsStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros.");
            return;
        }

        int[] hosts = new int[hostsStr.length];
        for (int i = 0; i < hostsStr.length; i++) {
            hosts[i] = Integer.parseInt(hostsStr[i].trim());
        }

        Vlsm calculator = new Vlsm(ip, mascara, hosts);
        List<String[]> resultado = calculator.calcular();
        List<String> resumenVLSM = calculator.generarResumenBinario();

        HttpSession session = request.getSession();
        session.setAttribute("resultados", resultado);
        session.setAttribute("resumenVLSM", resumenVLSM);

        request.getRequestDispatcher("resultados.jsp").forward(request, response);
    }
}
