package zad1.roby;

import zad1.plansza.pola.Pole;
import zad1.stale.Kierunek;
import zad1.stale.Wspolrzedne;

/**
 * Typ enumeracyjny który implementuje poszczególne instrukcje z programów robów.
 */

public enum Instrukcja implements InterfejsInstrukcji {
    LEWO('l') {
        @Override
        public void wykonajInstrukcje(Rob rob) {
            rob.ustawKierunek(rob.dajKierunek().dajObroconyWLewo());
        }
    },
    PRAWO('p') {
        @Override
        public void wykonajInstrukcje(Rob rob) {
            rob.ustawKierunek(rob.dajKierunek().dajObroconyWPrawo());
        }
    },
    IDZ('i') {
        @Override
        public void wykonajInstrukcje(Rob rob) {
            rob.wykonajRuch();
            rob.sprobujZjescJedzenieZPola(rob.dajWspolrzedne().dajPole());
        }
    },
    WACHAJ('w') {
        @Override
        public void wykonajInstrukcje(Rob rob) {
            for (Kierunek kierunek : Kierunek.values()) {
                Pole pole = rob.dajWspolrzedne().dajPrzemieszczenieOWektor(kierunek.dajWektor()).dajPole();
                if (pole.czyMaJedzenie()) {
                    rob.ustawKierunek(kierunek);
                    return;
                }
            }
        }
    },
    JEDZ('j') {
        @Override
        public void wykonajInstrukcje(Rob rob) {
            Wspolrzedne[] wektory = Wspolrzedne.dajWektoryOJednostkowychWspolrzednych();
            for (Wspolrzedne wektor : wektory) {
                if (rob.sprobujZjescJedzenieZPola(rob.dajWspolrzedne().dajPrzemieszczenieOWektor(wektor).dajPole())) {
                    return;
                }
            }
        }
    };

    private final char litera;

    Instrukcja(char litera) {
        this.litera = litera;
    }

    public static Instrukcja dajInstrukcje(char litera) {
        for (Instrukcja instrukcja : Instrukcja.values()) {
            if (instrukcja.litera == litera) {
                return instrukcja;
            }
        }
        System.err.println("Podana litera nie odpowiada żadnej instrukcji.");
        System.exit(1);
        return LEWO;
    }

    @Override
    public String toString() {
        return "" + litera;
    }
}
