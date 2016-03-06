<%@page import="entity.Artikel"%>
<%@ include file="header.jsp" %>
<a href='Home' class='btn btn-default btn-primary'><i class="glyphicon glyphicon-arrow-left"></i> Zurück</a>
<br><br>
${message}
<h3>Alle Veranstaltungen anzeigen</h3>
<%
    AdminClient client = (AdminClient) session.getAttribute("client");
    if (client == null) {
        out.println("<html><head><title>Session Fehler</title></head>");
        out.close();
    }
%>
<div class="table-responsive">
<table class="table"><thead align="left" style="display: table-header-group">
        <tr>
            <th>Name</th>
            <th>Start Termin</th>
            <th>Ort</th>
            <th>Beschreibung</th>
            <th>Veranstalter</th>
            <th>Max Teilnehmerzahl</th>
            <th>Teilnehmende Benutzer</th>
            <th>Benötigte Artikel</th>
            <th>Vorhandene Artikel</th>                        
        </tr>
    </thead><tbody>
        <%
            Map<String, Veranstaltung> alleVeranstaltungen;
            alleVeranstaltungen = client.getAlleVeranstaltungen();
            // Sortierte Map
            Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(alleVeranstaltungen);

            for (Veranstaltung veranstaltung : treeMap.values()) {
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                out.println("<tr><td> " + veranstaltung.getName() + "</td>"
                        + "<td> " + df.format(veranstaltung.getStartTermin()) + "</td>\n"
                        + "<td> " + veranstaltung.getOrt() + "</td>\n"
                        + "<td> " + veranstaltung.getBeschreibung() + "</td>\n"
                        + "<td> " + veranstaltung.getVeranstalter().getBenutzername() + "</td>\n"
                        + "<td> " + veranstaltung.getMaxTeilnehmerzahl() + "</td>\n"
                        + "<td> ");
                if (veranstaltung.getTeilnehmendeBenutzer() != null) {
                    for (Benutzer benutzer : veranstaltung.getTeilnehmendeBenutzer()) {
                        out.println(benutzer.getBenutzername() + ", ");
                    }
                }
                out.println("</td>");
                out.println("<td> ");
                if (veranstaltung.getBenoetigteArtikel() != null) {
                                Map<String, Artikel> treeMap1 = new TreeMap<String, Artikel>(veranstaltung.getBenoetigteArtikel());
                    for (Artikel artikel : treeMap1.values()) {
                        out.println(artikel.getBezeichnung() + ", ");
                    }
                }
                out.println("</td>");                
                out.println("<td> ");
                if (veranstaltung.getVorhandeneArtikel() != null) {
                                Map<String, Artikel> treeMap2 = new TreeMap<String, Artikel>(veranstaltung.getVorhandeneArtikel());
                    for (Artikel artikel : treeMap2.values()) {
                        out.println(artikel.getBezeichnung() + ", ");
                    }
                }
                out.println("</td>");
                out.println("</tr>");
            }
        %>
    </tbody></table>
</div>
</div>

<div class="box1">
    <div class="jumbotron">
        <h3>Veranstaltung hinzufügen</h3>
        <form id="veranstaltungHinzufuegenForm" action="Veranstaltungsverwaltung" method="POST" role="form">
            <p>
                <input type="text" name="nameHinzufuegen" id="nameHinzufuegen" tabindex="1" class="form-control" placeholder="Name">
            </p>
            <p>
                <input type="text" name="startTerminHinzufuegen" id="startTerminHinzufuegen" tabindex="2" class="form-control" placeholder="dd.MM.yyyy HH:mm">
            </p>
            <p>
                <input type="text" name="ortHinzufuegen" id="ortHinzufuegen" tabindex="3" class="form-control" placeholder="Ort">
            </p>
            <p>
                <input type="text" name="beschreibungHinzufuegen" id="beschreibungHinzufuegen" tabindex="4" class="form-control" placeholder="Beschreibung">
            </p>
            <p>
                <input type="number" name="maxTeilnehmerzahlHinzufuegen" id="maxTeilnehmerzahlHinzufuegen" tabindex="5" class="form-control" placeholder="Max Teilnehmerzahl">
            </p>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <input type="submit" name="veranstaltungHinzufuegen" id="veranstaltungHinzufuegen" tabindex="6" class="form-control btn btn-success" value="Veranstaltung hinzufügen">
                </div>
            </div>
        </form>                
    </div>
</div>
<div class="box2">
    <div class="jumbotron">
        <h3>Veranstaltung löschen</h3>
        <form id="veranstaltungLoeschenForm" action="Veranstaltungsverwaltung" method="post" role="form">
            <div class="form-group">
                <input type="text" name="veranstaltungnameLoeschen" id="veranstaltungnameLoeschen" tabindex="1" class="form-control" placeholder="Name">
                <br>
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3">                  
                        <input type="submit" name="veranstaltungLoeschen" id="veranstaltungLoeschen" tabindex="2" class="form-control btn btn-danger" value="Löschen">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>