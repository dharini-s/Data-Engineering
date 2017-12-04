package com.termproject.graphs;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Class to create a sample graph and execute the methods in the Graph class
 */

@WebServlet("/hamiltonian/*")
public class GraphRunner extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String csvFile = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        String keyStr = request.getParameter("key");
        Integer key = null;
        if(keyStr != null)  {
            key = Integer.parseInt(keyStr);
        }

        String variation = request.getParameter("variation");

        BufferedReader br = null;
//        String rows[] = csvFile.split(System.getProperty("line.separator"));
        String line = "";
        String cvsSplitBy = ",";
        List<int[]> edges = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(filePath + "/" + csvFile));
            System.out.println("file path is" + filePath + "/" + csvFile);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] field = line.split(cvsSplitBy);
                int fields[] = new int[]{Integer.parseInt(field[0]),
                        Integer.parseInt(field[1]),
                        Integer.parseInt(field[2])};
                edges.add(fields);
                System.out.println("Data is: " + field[0] + " " + field[1] + " " + field[2]);
            }

            String res = processData(edges, variation, key);
            if(res.equals("false")) {
                String message="[\"Not Hamiltonian or does not satisfy the variation conditions\"]";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
//                response.setContentType("application/text");
//                response.getWriter().write();
            }
            System.out.println("Sending response");
            response.setContentType("application/json");
            response.getWriter().write(res);

        } catch (NumberFormatException e) {
            System.out.println("Incorrect input format!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }

    private String processData(List<int[]> edges, String variation, Integer key) {
        Graph<Integer> graph = null;
        int degree = 1;
        int weightLimit = 1;
        System.out.println("Variation is" + variation);

        if(key != null) {
            if(key == 1)    {
                graph = new Graph<>(true);
            }
            else if(key == 2 && variation != null) {
                degree = Integer.parseInt(variation);
                graph = new Graph<>(false);
            }
            else if(key == 3 && variation != null)   {
                weightLimit = Integer.parseInt(variation);
                graph = new Graph<>(false);
            }
        }
        else
            graph = new Graph<>(false);

        for (int[] row : edges) {
            graph.addEdge(row[0], row[1], row[2]);
        }

        HamiltonianCycle<Integer> hamil = new HamiltonianCycle<Integer>();
        List<Vertex<Integer>> result = new ArrayList<>();

        boolean isHamiltonian = hamil.getHamiltonianCycle(graph, result, degree, weightLimit);
        System.out.println(isHamiltonian);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (isHamiltonian) {
            for (Vertex<Integer> v : result) {
                sb.append(v.getId());
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("]");
        }
        if (isHamiltonian) {
            return sb.toString();
        } else {
            return "false";
        }
    }
}
