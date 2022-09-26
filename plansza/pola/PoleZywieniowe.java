package zad1.plansza.pola;

import zad1.stale.Parametry;

public class PoleZywieniowe extends Pole {
    private static Parametry parametry;
    private int momentZjedzenia = -1;

    public static void ustawParametry(Parametry parametry) {
        PoleZywieniowe.parametry = parametry;
    }

    public boolean czyMaJedzenie() {
        return momentZjedzenia == -1 || momentZjedzenia + parametry.dajIleRosnieJedzenie() <= parametry.dajNumerTury();
    }

    public void zabierzJedzenieZPola() {
        momentZjedzenia = parametry.dajNumerTury();
    }
}
