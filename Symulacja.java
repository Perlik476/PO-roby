package zad1;

import zad1.plansza.pola.PoleZywieniowe;
import zad1.roby.Mutacja;
import zad1.roby.Rob;
import zad1.stale.Parametry;
import zad1.stale.Wspolrzedne;
import zad1.symulacjaEwolucji.EwolucjaRobow;
import zad1.plansza.Plansza;
import zad1.roby.Program;
import zad1.stale.Kierunek;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Autor: Mateusz Perlik
 *
 * Jest to główna klasa programu, która wczytuje dane z danych plików, a następnie wywołuje symulację w obiekcie klasy
 * EwolucjaRobów.
 */



public class Symulacja {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Błędna liczba argumentów.");
            System.exit(1);
        }
        int numerArgumentu = 0;
        try {
            File plik = new File(args[0]);
            Scanner skaner = new Scanner(plik);
            Plansza plansza = new Plansza(plik, skaner);
            numerArgumentu++;

            plik = new File(args[1]);
            skaner = new Scanner(plik);
            Parametry parametry = new Parametry(plik, skaner);

            ustawParametry(parametry, plansza);
            ArrayList<Rob> roby = wygenerujRoby(parametry, plansza);

            EwolucjaRobow ewolucjaRobow = new EwolucjaRobow(parametry, plansza, roby);
            ewolucjaRobow.symuluj();
        }
        catch (FileNotFoundException exception) {
            System.err.println("Nie znaleziono pliku " + args[numerArgumentu]);
            System.exit(1);
        }
    }

    private static void ustawParametry(Parametry parametry, Plansza plansza) {
        Rob.ustawParametry(parametry);
        PoleZywieniowe.ustawParametry(parametry);
        Program.ustawParametry(parametry);
        Wspolrzedne.ustawPlansze(plansza);
        Mutacja.ustawParametry(parametry);
        Mutacja.DODAJ_INSTRUKCJE_NA_KONIEC.ustawPrawdopodobienstwo(
                parametry.dajPrawdopodobienstwoDodaniaInstrukcji());
        Mutacja.USUN_INSTRUKCJE_Z_KONCA.ustawPrawdopodobienstwo(
                parametry.dajPrawdopodobienstwoUsunieciaInstrukcji());
        Mutacja.ZMIEN_INSTRUKCJE.ustawPrawdopodobienstwo(parametry.dajPrawdopodobienstwoZmianyInstrukcji());
    }

    private static ArrayList<Rob> wygenerujRoby(Parametry parametry, Plansza plansza) {
        ArrayList<Rob> roby = new ArrayList<>();
        for (int i = 0; i < parametry.dajPoczatkowoIleRobow(); i++) {
            int x = parametry.dajLos().nextInt(plansza.dajRozmiarPlanszyX());
            int y = parametry.dajLos().nextInt(plansza.dajRozmiarPlanszyY());
            int kierunek = parametry.dajLos().nextInt(Kierunek.values().length);
            assert Kierunek.dajKierunekOWartosci(kierunek) != null;
            roby.add(new Rob(new Wspolrzedne(x, y), Kierunek.dajKierunekOWartosci(kierunek),
                    parametry.dajPoczatkowaEnergia(), new Program(parametry.dajPoczatkowyProgram())));
        }
        return roby;
    }
}