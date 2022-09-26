package zad1.roby;

import zad1.stale.Parametry;
import java.util.ArrayList;

/**
 * Klasa odpowiadająca programowi roba.
 */

public class Program {
    private final ArrayList<Instrukcja> instrukcje;
    private int indeksAktualnejInstrukcji = 0;
    private static Parametry parametry;

    public Program(ArrayList<Instrukcja> instrukcje) {
        this.instrukcje = instrukcje;
    }

    public Program(String instrukcjeJakoCiagZnakow) {
        instrukcje = new ArrayList<>();
        for (int i = 0; i < instrukcjeJakoCiagZnakow.length(); i++) {
            instrukcje.add(Instrukcja.dajInstrukcje(instrukcjeJakoCiagZnakow.charAt(i)));
        }
    }

    public Program(Program program) {
        instrukcje = new ArrayList<>(program.dajListeInstrukcji());
        indeksAktualnejInstrukcji = 0;
    }

    Instrukcja dajKolejnaInstrukcje() {
        if (indeksAktualnejInstrukcji == instrukcje.size()) {
            return null;
        }
        return instrukcje.get(indeksAktualnejInstrukcji++);
    }

    void odPoczatku() {
        indeksAktualnejInstrukcji = 0;
    }

    public ArrayList<Instrukcja> dajListeInstrukcji() {
        return instrukcje;
    }

    public static void ustawParametry(Parametry parametry) {
        Program.parametry = parametry;
    }

    public static int dajDlugoscSpisuInstrukcji() {
        return parametry.dajSpisInstrukcji().length;
    }

    public int dajDlugosc() {
        return instrukcje.size();
    }

    public boolean czyPusty() {
        return dajDlugosc() == 0;
    }

    public Instrukcja[] dajTabliceInstrukcji() {
        return instrukcje.toArray(new Instrukcja[instrukcje.size()]);
    }

    ArrayList<Instrukcja> dajTabliceListeInstrukcji() {
        return instrukcje;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Instrukcja instrukcja : instrukcje) {
            str.append(instrukcja);
        }
        return str.toString();
    }
}
