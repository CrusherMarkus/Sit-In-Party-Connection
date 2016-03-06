<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Eine neue Veranstaltung hinzufügen</h4>
            </div>
            <form id="veranstaltungHinzufuegenForm" action="Veranstaltungsverwaltung" method="POST" role="form" enctype="multipart/form-data">
                <div class="modal-body">
                    <p>
                        <label for="nameHinzufuegen">Name:</label>
                        <input type="text" name="nameHinzufuegen" id="nameHinzufuegen" tabindex="1" class="form-control" placeholder="Name" required>
                    </p>
                    <p>
                        <label for="startTerminHinzufuegen">Start Termin(dd.MM.yyyy HH:mm):</label>
                        <input type="text" name="startTerminHinzufuegen" id="startTerminHinzufuegen" tabindex="2" class="form-control" placeholder="dd.MM.yyyy HH:mm" required>
                    </p>
                    <p>
                        <label for="ortHinzufuegen">Ort:</label>
                        <input type="text" name="ortHinzufuegen" id="ortHinzufuegen" tabindex="3" class="form-control" placeholder="Ort" required>
                    </p>
                    <p>
                        <label for="beschreibungHinzufuegen">Beschreibung</label>
                        <input type="text" name="beschreibungHinzufuegen" id="beschreibungHinzufuegen" tabindex="4" class="form-control" placeholder="Beschreibung" required>
                    </p>
                    <p>
                        <label for="maxTeilnehmerzahlHinzufuegen">max. Teilnehmerzahl</label>
                        <input type="number" name="maxTeilnehmerzahlHinzufuegen" id="maxTeilnehmerzahlHinzufuegen" tabindex="5" class="form-control" placeholder="Max Teilnehmerzahl" required>
                    </p>  
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Abbrechen</button>
                    <input type="submit" name="veranstaltungAddTeilnehmer" id="veranstaltungAddTeilnehmer" class="btn btn-success" value="Veranstaltung hinzufügen" required>
                </div>
            </form>    
        </div>
    </div>
</div>
