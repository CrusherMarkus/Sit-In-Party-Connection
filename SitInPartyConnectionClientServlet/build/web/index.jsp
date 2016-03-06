<%@ include file="header.jsp" %>

<center><h2>Sit-In Party Connection</h2></center>
<div class="row">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-login">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-6">
                        <a href="#" class="active" id="login-form-link">Anmelden</a>
                    </div>
                    <div class="col-xs-6">
                        <a href="#" id="register-form-link">Registrieren</a>
                    </div>
                </div>
                <hr>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="login-form" action="Login" method="post" role="form" style="display: block;">
                            <div class="form-group">
                                <label for="benutzername">Benutzername:</label>
                                <input type="text" name="benutzername" id="benutzername" tabindex="1" class="form-control" placeholder="Benutzername">
                            </div>
                            <div class="form-group">
                                <label for="passwort">Passwort:</label>
                                <input type="password" name="passwort" id="passwort" tabindex="2" class="form-control" placeholder="Passwort">
                            </div>
                            ${message}
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="login-submit" id="login-submit" tabindex="3" class="form-control btn btn-login" value="Anmelden">
                                    </div>
                                </div>
                            </div>
                        </form>
                        <form id="register-form" action="Login" method="post" role="form" style="display: none;">
                            <div class="form-group">
                                <label for="benutzername">Benutzername:</label>
                                <input type="text" name="benutzername" id="benutzername" tabindex="1" class="form-control" placeholder="Benutzername">
                            </div>
                            <div class="form-group">
                                <label for="passwort">Passwort:</label>
                                <input type="password" name="passwort" id="passwort" tabindex="2" class="form-control" placeholder="Passwort">
                            </div>
                            <div class="form-group">
                                <label for="confirm-password">Passwort wiederholen:</label>
                                <input type="password" name="passwortconfirm" id="confirm-password" tabindex="3" class="form-control" placeholder="Passwort wiederholen">
                            </div>
                            <div class="form-group">
                                <label for="vorname">Vorname:</label>
                                <input type="text" name="vorname" id="vorname" tabindex="4" class="form-control" placeholder="Vorname">
                            </div>
                            <div class="form-group">
                                <label for="nachname">Nachname</label>
                                <input type="text" name="nachname" id="nachname" tabindex="5" class="form-control" placeholder="Nachname">
                            </div>
                            <div class="form-group">
                                <label for="email">E-Mail</label>
                                <input type="email" name="email" id="email" tabindex="6" class="form-control" placeholder="E-Mail">
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registrieren">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>