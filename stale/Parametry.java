package zad1.stale;

import zad1.roby.Instrukcja;
import zad1.roby.Program;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Klasa przechowująca parametry dane na wejściu, a ponadto numer tury oraz zmienną los do enerowania pseudolosowych
 * wartości.
 * Uwaga: zgodnie z zezwoleniem na forum nie zakładamy, że wszystkie instrukcje z programu początkowego muszą
 * należeć do spisu instrukcji - wtedy spis instrukcji odpowiada za jedyne instrukcje, które można otrzymać w
 * wyniku mutacji.
 * Przyjmujemy ponadto, że spis_instr i pocz_progr mogą być puste. Żeby osiągnąć taki efekt należy wpisac
 * "spis_instr " bądź "pocz_progr " w pliku z parametrami.
 */

public class Parametry {
    private int numerTury = 0;
    private int ileTur;
    private int coIleWypisz;
    private int poczatkowoIleRobow;
    private int ileDajeJedzenie;
    private int poczatkowaEnergia;
    private int kosztTury;
    private int limitPowielania;
    private float prawdopodobienstwoPowielenia;
    private float prawdopodobienstwoDodaniaInstrukcji;
    private float prawdopodobienstwoUsunieciaInstrukcji;
    private float prawdopodobienstwoZmianyInstrukcji;
    private float ulamekEnergiiRodzica;
    private int ileRosnieJedzenie;
    private Program poczatkowyProgram;
    private Instrukcja[] spisInstrukcji;
    private final Random los = new Random();

    private void sprawdzCzyKolejneZWejsciaJestCalkowite(String wartoscZmiennej, String nazwaZmiennej) {
        Scanner skaner = new Scanner(wartoscZmiennej);
        if (!skaner.hasNextInt()) {
            System.err.println("Wartość zmiennej " + nazwaZmiennej +
                    " nie została podana bądź nie jest liczbą całkowitą.");
            System.exit(1);
        }
    }

    private void sprawdzCzyNieujemne(String nazwaZmiennej, int zmienna) {
        if (zmienna < 0) {
            System.err.println("Wartość zmiennej " + nazwaZmiennej + " nie może być ujemna.");
            System.exit(1);
        }
    }

    private void sprawdzCzyWartoscJestZmiennoprzecinkowa(String wartoscZmiennej, String nazwaZmiennej) {
        Scanner skaner = new Scanner(wartoscZmiennej);
        if (!skaner.hasNextFloat()) {
            System.err.println("Wartość zmiennej " + nazwaZmiennej +
                    " nie została podana bądź nie jest liczbą zmiennoprzecinkową.");
            System.exit(1);
        }
    }

    private void sprawdzCzyOdZeraDoJeden(String nazwaZmiennej, float zmienna) {
        if (zmienna < 0 || zmienna > 1) {
            System.err.println("Wartość zmiennej " + nazwaZmiennej + " nie może nie należeć do przedziału [0, 1]");
            System.exit(1);
        }
    }

    public Parametry(File plik, Scanner skaner) {
        int ileZmiennychDoWpisania = 15;
        ArrayList<String> zmienneUstawione = new ArrayList<>();
        int numerWiersza = 0;
        while (skaner.hasNextLine()) {
            numerWiersza++;
            String wiersz = skaner.nextLine();
            int liczbaSpacji = 0;
            for (int i = 0; i < wiersz.length(); i++) {
                if (wiersz.charAt(i) == ' ') {
                    liczbaSpacji++;
                    if (liczbaSpacji > 1) {
                        System.err.println("Więcej niż jedna spacja w wierszu " + numerWiersza + " w pliku " +
                                plik + ".");
                        System.exit(1);
                    }
                }
            }
            String nazwaZmiennej, wartoscZmiennej;
            String[] zawartoscWiersza = wiersz.split(" ");
            if (!(zawartoscWiersza.length == 2 || (zawartoscWiersza.length == 1 &&
                    (zawartoscWiersza[0].equals("pocz_progr") || zawartoscWiersza[0].equals("spis_instr"))))) {
                System.err.println("Niepoprawna struktura wiersza " + numerWiersza +" w pliku " + plik + ".");
                System.exit(1);
            }
            nazwaZmiennej = zawartoscWiersza[0];
            if (zawartoscWiersza.length == 1) {
                switch (nazwaZmiennej) {
                    case "pocz_progr":
                        poczatkowyProgram = new Program("");
                        break;
                    case "spis_instr":
                        spisInstrukcji = new Instrukcja[0];
                        break;
                }
                zmienneUstawione.add(nazwaZmiennej);
                continue;
            }
            wartoscZmiennej = zawartoscWiersza[1];
            if (zmienneUstawione.contains(nazwaZmiennej)) {
                System.err.println(nazwaZmiennej + " podane więcej niż raz.");
                System.exit(1);
            }
            Scanner skanerWartosci = new Scanner(wartoscZmiennej);
            switch(nazwaZmiennej) {
                case "ile_tur":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    ileTur = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, ileTur);
                    break;
                case "co_ile_wypisz":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    coIleWypisz = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, coIleWypisz);
                    break;
                case "ile_rośnie_jedzenie":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    ileRosnieJedzenie = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, ileRosnieJedzenie);
                    break;
                case "pocz_ile_robów":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    poczatkowoIleRobow = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, poczatkowoIleRobow);
                    break;
                case "pocz_progr":
                    poczatkowyProgram = new Program(skanerWartosci.next());
                    break;
                case "spis_instr":
                    Program program = new Program(skanerWartosci.next());
                    spisInstrukcji = program.dajTabliceInstrukcji();
                    break;
                case "pocz_energia":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    poczatkowaEnergia = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, poczatkowaEnergia);
                    break;
                case "ile_daje_jedzenie":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    ileDajeJedzenie = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, ileDajeJedzenie);
                    break;
                case "koszt_tury":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    kosztTury = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, kosztTury);
                    break;
                case "pr_powielenia":
                    sprawdzCzyWartoscJestZmiennoprzecinkowa(wartoscZmiennej, nazwaZmiennej);
                    prawdopodobienstwoPowielenia = skanerWartosci.nextFloat();
                    sprawdzCzyOdZeraDoJeden(nazwaZmiennej, prawdopodobienstwoPowielenia);
                    break;
                case "ułamek_energii_rodzica":
                    sprawdzCzyWartoscJestZmiennoprzecinkowa(wartoscZmiennej, nazwaZmiennej);
                    ulamekEnergiiRodzica = skanerWartosci.nextFloat();
                    sprawdzCzyOdZeraDoJeden(nazwaZmiennej, ulamekEnergiiRodzica);
                    break;
                case "limit_powielania":
                    sprawdzCzyKolejneZWejsciaJestCalkowite(wartoscZmiennej, nazwaZmiennej);
                    limitPowielania = skanerWartosci.nextInt();
                    sprawdzCzyNieujemne(nazwaZmiennej, limitPowielania);
                    break;
                case "pr_usunięcia_instr":
                    sprawdzCzyWartoscJestZmiennoprzecinkowa(wartoscZmiennej, nazwaZmiennej);
                    prawdopodobienstwoUsunieciaInstrukcji = skanerWartosci.nextFloat();
                    sprawdzCzyOdZeraDoJeden(nazwaZmiennej, prawdopodobienstwoUsunieciaInstrukcji);
                    break;
                case "pr_dodania_instr":
                    sprawdzCzyWartoscJestZmiennoprzecinkowa(wartoscZmiennej, nazwaZmiennej);
                    prawdopodobienstwoDodaniaInstrukcji = skanerWartosci.nextFloat();
                    sprawdzCzyOdZeraDoJeden(nazwaZmiennej, prawdopodobienstwoDodaniaInstrukcji);
                    break;
                case "pr_zmiany_instr":
                    sprawdzCzyWartoscJestZmiennoprzecinkowa(wartoscZmiennej, nazwaZmiennej);
                    prawdopodobienstwoZmianyInstrukcji = skanerWartosci.nextFloat();
                    sprawdzCzyOdZeraDoJeden(nazwaZmiennej, prawdopodobienstwoZmianyInstrukcji);
                    break;
                default:
                    System.err.println("Niepoprawna nazwa zmiennej: " + nazwaZmiennej);
                    System.exit(1);
            }
            zmienneUstawione.add(nazwaZmiennej);
        }
        if (zmienneUstawione.size() != ileZmiennychDoWpisania) {
            System.err.println("Podano " + zmienneUstawione.size() + " wartości zmiennych. Wymagane " +
                    ileZmiennychDoWpisania);
            System.exit(1);
        }
    }

    public int dajIleTur() {
        return ileTur;
    }

    public int dajCoIleWypisz() {
        return coIleWypisz;
    }

    public int dajPoczatkowoIleRobow() {
        return poczatkowoIleRobow;
    }

    public int dajIleDajeJedzenie() {
        return ileDajeJedzenie;
    }

    public int dajPoczatkowaEnergia() {
        return poczatkowaEnergia;
    }

    public int dajKosztTury() {
        return kosztTury;
    }

    public int dajLimitPowielania() {
        return limitPowielania;
    }

    public float dajPrawdopodobienstwoPowielenia() {
        return prawdopodobienstwoPowielenia;
    }

    public float dajPrawdopodobienstwoDodaniaInstrukcji() {
        return prawdopodobienstwoDodaniaInstrukcji;
    }

    public float dajPrawdopodobienstwoUsunieciaInstrukcji() {
        return prawdopodobienstwoUsunieciaInstrukcji;
    }

    public float dajPrawdopodobienstwoZmianyInstrukcji() {
        return prawdopodobienstwoZmianyInstrukcji;
    }

    public float dajUlamekEnergiiRodzica() {
        return ulamekEnergiiRodzica;
    }

    public int dajIleRosnieJedzenie() {
        return ileRosnieJedzenie;
    }

    public Program dajPoczatkowyProgram() {
        return poczatkowyProgram;
    }

    public Instrukcja[] dajSpisInstrukcji() {
        return spisInstrukcji;
    }

    public Random dajLos() {
        return los;
    }

    public int dajNumerTury() {
        return numerTury;
    }

    public void kolejnaTura() {
        numerTury++;
    }
}
