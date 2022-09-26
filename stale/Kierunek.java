package zad1.stale;

/**
 * Typ enumeracyjny przechowujący kierunki świata i odpowiadające im wektory.
 */

public enum Kierunek {
    POLNOC(0, new Wspolrzedne(0, -1)),
    WSCHOD(1, new Wspolrzedne(1, 0)),
    POLUDNIE(2, new Wspolrzedne(0, 1)),
    ZACHOD(3, new Wspolrzedne(-1, 0));

    private final int wartosc;
    private final Wspolrzedne wektor;

    Kierunek(int wartosc, Wspolrzedne wektor) {
        this.wartosc = wartosc;
        this.wektor = wektor;
    }

    public Kierunek dajObroconyWPrawo() {
        int tymczasowaWartosc = wartosc;
        tymczasowaWartosc++;
        tymczasowaWartosc %= Kierunek.values().length;
        return dajKierunekOWartosci(tymczasowaWartosc);
    }

    public Kierunek dajObroconyWLewo() {
        int tymczasowaWartosc = wartosc;
        tymczasowaWartosc += 4;
        tymczasowaWartosc--;
        tymczasowaWartosc %= 4;
        return dajKierunekOWartosci(tymczasowaWartosc);
    }

    public Kierunek dajPrzeciwny() {
        int tymczasowaWartosc = wartosc;
        tymczasowaWartosc += 2;
        tymczasowaWartosc %= 4;
        return dajKierunekOWartosci(tymczasowaWartosc);
    }

    public static Kierunek dajKierunekOWartosci(int wartosc) {
        Kierunek[] kierunki = Kierunek.values();
        for (Kierunek kierunek : kierunki) {
            if (kierunek.wartosc == wartosc)
                return kierunek;
        }
        return null;
    }

    public Wspolrzedne dajWektor() {
        return wektor;
    }

}
