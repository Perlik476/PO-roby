package zad1.stale;

import zad1.plansza.pola.Pole;
import zad1.plansza.Plansza;

import java.util.ArrayList;

/**
 * Klasa reprezentująca współrzędne, które reprezentują pewne pole z planszy. Przyjmujemy, że współrzędne x-owe rosną
 * w prawo, a współrzędne y-owe rosną w dół. Ponadto nie rozróżniamy współrzędnych od wektora - obie uznajemy za
 * izomorficzne i są zawarte w tejże klasie.
 */

public class Wspolrzedne {
    private int x;
    private int y;
    private static Plansza plansza;

    public Wspolrzedne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Wspolrzedne(Wspolrzedne wspolrzedne) {
        this.x = wspolrzedne.dajX();
        this.y = wspolrzedne.dajY();
    }

    public Wspolrzedne ruszOWektor(Wspolrzedne v) {
        x += v.dajX();
        x += plansza.dajRozmiarPlanszyX();
        x %= plansza.dajRozmiarPlanszyX();
        y += v.dajY();
        y += plansza.dajRozmiarPlanszyY();
        y %= plansza.dajRozmiarPlanszyY();
        return this;
    }

    public Wspolrzedne dajPrzemieszczenieOWektor(Wspolrzedne v) {
        Wspolrzedne wspolrzedne = new Wspolrzedne(x, y);
        return wspolrzedne.ruszOWektor(v);
    }

    public int dajX() {
        return x;
    }

    public int dajY() {
        return y;
    }

    public Pole dajPole() {
        return plansza.dajPole(this);
    }

    public static Wspolrzedne[] dajWektoryOJednostkowychWspolrzednych() {
        ArrayList<Wspolrzedne> wektory = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 && j != 0) {
                    wektory.add(new Wspolrzedne(i, j));
                }
            }
        }
        return wektory.toArray(new Wspolrzedne[wektory.size()]);
    }

    public static void ustawPlansze(Plansza plansza) {
        Wspolrzedne.plansza = plansza;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
