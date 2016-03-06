<%@ include file="header.jsp" %>
<a href='Home' class='btn btn-default btn-primary'><i class="glyphicon glyphicon-arrow-left"></i> Zurück</a>
<br><br>
${message}
<h3>Alle Benutzer anzeigen</h3>
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
            <th>Benutzername</th>
            <th>Passwort</th>
            <th>Vorname</th>
            <th>Nachname</th>
            <th>E-Mail</th>
            <th>istAdmin</th>
        </tr>
    </thead><tbody>
        <%
            Map<String, Benutzer> alleBenutzer;
            alleBenutzer = client.getAlleBenutzer();
            // Sortierte Map
            Map<String, Benutzer> treeMap = new TreeMap<String, Benutzer>(alleBenutzer);
            for (Benutzer benutzer : treeMap.values()) {
                out.println("<tr><td> " + benutzer.getBenutzername() + "</td>"
                        + "<td> " + benutzer.getPasswort() + "</td>\n"
                        + "<td> " + benutzer.getVorname() + "</td>\n"
                        + "<td> " + benutzer.getNachname() + "</td>\n"
                        + "<td> " + benutzer.getEmail() + "</td>\n"
                        + "<td> " + benutzer.getIstAdmin() + "</td>\n"
                        + "</tr>");
            }
        %>
    </tbody></table>
</div>
</div>


<div class="box1">
    <div class="jumbotron">
        <h3>Benutzer hinzufügen</h3>
        <form id="benutzerHinzufuegenForm" action="Benutzerverwaltung" method="POST" role="form">
            <p>
            <div class="form-group">
                <input type="text" name="benutzernameHinzufuegen" id="benutzernameHinzufuegen" tabindex="1" class="form-control" placeholder="Benutzername" required>
            </div>
            </p>
            <p>
            <div class="form-group">
                <input type="password" name="passwortHinzufuegen" id="passwortHinzufuegen" tabindex="2" class="form-control" placeholder="Passwort" required>
            </div>
            </p>
            <p>
            <div class="form-group">
                <input type="text" name="vornameHinzufuegen" id="vornameHinzufuegen" tabindex="3" class="form-control" placeholder="Vorname" required>
            </div>
            </p>
            <p>
            <div class="form-group">
                <input type="text" name="nachnameHinzufuegen" id="nachnameHinzufuegen" tabindex="4" class="form-control" placeholder="Nachname" required>
            </div>
            </p>
            <p>
            <div class="form-group">
                <input type="email" name="emailHinzufuegen" id="emailHinzufuegen" tabindex="4" class="form-control" placeholder="E-Mail" required>
            </div>     
            </p>
            <p>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="istAdminHinzufuegen" id="istAdminHinzufuegen" tabindex="5">Administrator?
                </label>
            </div> 
            </p>
            <div class="form-group">
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3">
                        <input type="submit" name="benutzerHinzufuegen" id="benutzerHinzufuegen" tabindex="6" class="form-control btn btn-success" value="Benutzer hinzufügen">
                    </div>
                </div>
            </div>
        </form>                
    </div>
</div>
<div class="box2">
    <div class="jumbotron">
        <h3>Benutzer löschen</h3>
        <form id="benutzerLoeschenForm" action="Benutzerverwaltung" method="post" role="form">
            <div class="form-group">
                <input type="text" name="benutzernameLoeschen" id="benutzernameLoeschen" tabindex="1" class="form-control" placeholder="Benutzername">
                <br>
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3">
                        <input type="submit" name="benutzerLoeschen" id="benutzerLoeschen" tabindex="2" class="form-control btn btn-danger" value="Löschen">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>


