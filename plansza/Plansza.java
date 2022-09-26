package zad1.plansza;

import zad1.plansza.pola.Pole;
import zad1.plansza.pola.PolePuste;
import zad1.plansza.pola.PoleZywieniowe;
import zad1.stale.Wspolrzedne;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Trzyma wszystkie pola z symulacji i służy do dostawania się do nich.
 */

public class Plansza {
    private final Pole[][] pola;
    private final int rozmiarPlanszyX;
    private final int rozmiarPlanszyY;

    public Plansza(Pole[][] pola) {
        this.pola = pola;
        rozmiarPlanszyY = pola.length;
        rozmiarPlanszyX = pola[0].length;
    }

    public int dajRozmiarPlanszyX() {
        return rozmiarPlanszyX;
    }

    public int dajRozmiarPlanszyY() {
        return rozmiarPlanszyY;
    }

    public Pole dajPole(Wspolrzedne wspolrzedne) {
        assert wspolrzedne.dajX() >= 0 && wspolrzedne.dajX() < rozmiarPlanszyX &&
                wspolrzedne.dajY() >= 0 && wspolrzedne.dajY() < rozmiarPlanszyY;
        return pola[wspolrzedne.dajY()][wspolrzedne.dajX()];
    }

    public int dajLiczbePolZJedzeniem() {
        int wynik = 0;
        for (Pole[] wierszPol : pola) {
            for (Pole pole : wierszPol) {
                wynik += pole.czyMaJedzenie() ? 1 : 0;
            }
        }
        return wynik;
    }

    public Plansza(File plik, Scanner skaner) {
        ArrayList<ArrayList<Pole>> polaTymczasowe = new ArrayList<>();
        int dlugoscWiersza = -1;
        if (!skaner.hasNextLine()) {
            System.err.println("Plik " + plik + " jest pusty.");
            System.exit(1);
        }
        while (skaner.hasNextLine()) {
            polaTymczasowe.add(new ArrayList<>());
            String wiersz = skaner.nextLine();
            if (wiersz.length() == 0) {
                System.err.println("W pliku " + plik + " jest wiersz o długości 0.");
                System.exit(1);
            }
            if (dlugoscWiersza == -1) {
                dlugoscWiersza = wiersz.length();
            }
            else if (dlugoscWiersza != wiersz.length()) {
                System.err.println("W pliku " + plik + " nie wszystkie wiersze mają równą długość.");
                System.exit(1);
            }
            for (int i = 0; i < wiersz.length(); i++) {
                if (wiersz.charAt(i) != 'x' && wiersz.charAt(i) != ' ') {
                    System.err.println("W pliku " + plik + " występują znaki inne niż 'x' i spacja.");
                    System.exit(1);
                }
                if (wiersz.charAt(i) == 'x') {
                    PoleZywieniowe poleZywieniowe = new PoleZywieniowe();
                    polaTymczasowe.get(polaTymczasowe.size() - 1).add(poleZywieniowe);
                }
                else {
                    polaTymczasowe.get(polaTymczasowe.size() - 1).add(new PolePuste());
                }
            }
        }
        Pole[][] pola = new Pole[polaTymczasowe.size()][polaTymczasowe.get(0).size()];
        for (int i = 0; i < polaTymczasowe.size(); i++) {
            for (int j = 0; j < polaTymczasowe.get(0).size(); j++) {
                pola[i][j] = polaTymczasowe.get(i).get(j);
            }
        }

        this.pola = pola;
        rozmiarPlanszyY = pola.length;
        rozmiarPlanszyX = pola[0].length;
    }
}
