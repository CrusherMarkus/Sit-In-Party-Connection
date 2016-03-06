/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.Hilfsklasse.ANSI_RED;
import static client.Hilfsklasse.ANSI_RESET;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Markus
 */
public class Eingabe {

    private static BufferedReader input;

    /**
     *
     * @param nachricht
     * @return
     */
    public static boolean leseBoolean(String nachricht) {
        System.out.print(nachricht);
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
        }

        boolean ergebnis;
        try {
            ergebnis = Boolean.valueOf(leseString("(true/false)"));
        } catch (NumberFormatException e) {
            System.out.println("Bitte nur 'true' oder 'false' eingeben");
            return leseBoolean(nachricht);
        }

        return ergebnis;
    }

    static Date leseDatum(String nachricht) throws ParseException {
        System.out.print(nachricht + ": ");
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
        }
        int tag = leseInt("\nTag",1,31);
        int monat = leseInt("Monat",1,12);
        int jahr = leseInt("Jahr",0,9999);
        int stunde = leseInt("Stunde",0,24);
        int minute = leseInt("Minute",0,60);

        StringBuilder sb = new StringBuilder();
        sb.append(tag + "." + monat + "." + jahr + " " + stunde + ":" + minute);
        String ergebnis = sb.toString();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Date startTermin = df.parse(ergebnis);
        String newDateString = df.format(startTermin);
        //System.out.println("newDateString: " + newDateString);
        //System.out.println("startTermin: " + startTermin);
        return startTermin;
    }

    /**
     *
     * @param untereGrenze
     * @param obereGrenze
     * @return
     */
    public static int leseInt(int untereGrenze, int obereGrenze) {
        return Eingabe.leseInt("Zahl zwischen " + untereGrenze + " und " + obereGrenze + " eingeben", untereGrenze, obereGrenze);
    }

    /**
     *
     * @param nachricht
     * @param untereGrenze
     * @param obereGrenze
     * @return
     */
    public static int leseInt(String nachricht, int untereGrenze, int obereGrenze) {
        System.out.print(nachricht + ": ");
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
        }
        int ergebnis;
        try {
            ergebnis = Integer.decode(input.readLine());
        } catch (NumberFormatException | IOException e) {
            ergebnis = -1;
        }
        if (ergebnis < untereGrenze) {
            System.out.println(ANSI_RED + "Der Wert muss mindestens " + untereGrenze + " betragen." + ANSI_RESET);
            return leseInt(untereGrenze, obereGrenze);
        }
        if (ergebnis > obereGrenze) {
            System.out.println(ANSI_RED + "Der Wert darf höchstens " + obereGrenze + " betragen." + ANSI_RESET);
            return leseInt(untereGrenze, obereGrenze);
        }
        return ergebnis;
    }

    /**
     *
     * @param nachricht
     * @return
     */
    public static String lesePasswort(String nachricht) {
        Console console = System.console();
        if (console != null) {
            return new String(console.readPassword(nachricht));
        } else {
            return Eingabe.leseString(nachricht);
        }
    }

    /**
     *
     * @param nachricht
     * @return
     */
    public static String leseString(String nachricht) {
        System.out.print(nachricht + ": ");
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
        }
        String ergebnis;
        try {
            ergebnis = input.readLine();
        } catch (IOException e) {
            ergebnis = "";
        }
        return ergebnis;
    }

}
