package com.termproject.graphs;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            response.setContentType("application/json");
            response.getWriter().write("New file " + fileName + " created at " + path);
            response.sendRedirect("/index.html");
            writer.println("New file " + fileName + " created at " + path);
        } catch (FileNotFoundException fne) {
            response.setContentType("application/json");
            response.getWriter().write("You either did not specify a file to upload or are "
                            + "trying to upload a file to a protected or nonexistent "
                            + "location." + "<br/> ERROR: " + fne.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}