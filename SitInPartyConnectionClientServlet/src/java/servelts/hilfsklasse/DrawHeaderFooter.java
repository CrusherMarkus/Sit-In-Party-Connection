/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts.hilfsklasse;

import java.io.PrintWriter;

/**
 *
 * @author Markus
 */
public class DrawHeaderFooter {

    /**
     *
     * @param out
     */
    public void drawFooter(PrintWriter out) {
        out.println("</div> <!-- /jumbotron -->");
        out.println("</div> <!-- /container -->");
        out.println("<footer>");
        out.println("<p class='copyright'>Copyright © 2015 Markus Klemann, Simon Wächter<br>Verteilte Systeme</p>");
        out.println("</footer>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     *
     * @param out
     */
    public void drawHeader(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet SitInConnectionServlet</title>");
        out.println("<link rel='stylesheet' type='text/css' media='all' href='css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' type='text/css' media='all' href='css/bootstrap-datetimepicker.min.css'>");
        out.println("<link rel='stylesheet' type='text/css' media='all' href='css/style.css'>");
        out.println("<script src='js/jquery-1.11.3.min.js'></script>");
        out.println("<script src='js/moment.min.js'></script>");
        out.println("<script src='js/bootstrap.min.js'></script>");
        out.println("<script src='js/bootstrap-datetimepicker.min.js'></script>");
        out.println("<script src='js/myScript.js'></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='jumbotron'>");
    }

}
