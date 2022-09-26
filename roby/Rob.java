package zad1.roby;

import zad1.plansza.pola.Pole;
import zad1.stale.Kierunek;
import zad1.stale.Wspolrzedne;
import zad1.stale.Parametry;

public class Rob {
    private final Wspolrzedne wspolrzedne;
    private Kierunek kierunek;
    private int energia;
    private static Parametry parametry;
    private Rob potomstwo = null;
    private final Program program;
    private int wiek = 1;

    public Rob(Wspolrzedne wspolrzedne, Kierunek kierunek, int energia, Program program) {
        this.wspolrzedne = wspolrzedne;
        this.kierunek = kierunek;
        this.program = program;
        this.energia = energia;
    }

    public static void ustawParametry(Parametry parametry) {
        Rob.parametry = parametry;
    }

    void wykonajRuch() {
        wspolrzedne.ruszOWektor(kierunek.dajWektor());
    }

    public Wspolrzedne dajWspolrzedne() {
        return wspolrzedne;
    }

    public void kolejnaTura() {
        wiek++;
        energia -= parametry.dajKosztTury();
        if (energia < 0) { // "spadnie poniżej zera"
            return;
        }
        wykonajProgram();
        potomstwo = sprobujSiePowielic();
    }

    public boolean czyZyje() {
        return energia >= 0;
    }

    public boolean czyMaPotomstwo() {
        return potomstwo != null;
    }

    public Rob dajPotomstwo() {
        assert czyMaPotomstwo();
        Rob rob = potomstwo;
        potomstwo = null;
        return rob;
    }

    void ustawKierunek(Kierunek kierunek) {
        this.kierunek = kierunek;
    }

    public Kierunek dajKierunek() {
        return kierunek;
    }

    boolean sprobujZjescJedzenieZPola(Pole pole) {
        if (pole.czyMaJedzenie()) {
            pole.zabierzJedzenieZPola();
            energia += parametry.dajIleDajeJedzenie();
            return true;
        }
        return false;
    }

    private void wykonajProgram() {
        program.odPoczatku();
        Instrukcja instrukcja = program.dajKolejnaInstrukcje();
        while (instrukcja != null) {
            energia--;
            instrukcja.wykonajInstrukcje(this);
            instrukcja = program.dajKolejnaInstrukcje();
        }
    }

    private Rob sprobujSiePowielic() {
        if (energia >= parametry.dajLimitPowielania() && parametry.dajLos().nextFloat() <
                parametry.dajPrawdopodobienstwoPowielenia()) {
            Program nowyProgram = new Program(program);
            for (Mutacja mutacja : Mutacja.values()) {
                nowyProgram = mutacja.wykonajMutacje(nowyProgram);
            }
            int nowaEnergia = Math.round(parametry.dajUlamekEnergiiRodzica() * energia);
            energia -= nowaEnergia;
            return new Rob(new Wspolrzedne(wspolrzedne), kierunek.dajPrzeciwny(), nowaEnergia, nowyProgram);
        }
        return null;
    }


    public int dajWiek() {
        return wiek;
    }

    public int dajDlugoscProgramu() {
        return program.dajDlugosc();
    }

    public int dajEnergie() {
        return energia;
    }

    @Override
    public String toString() {
        return "wspolrzedne: " + wspolrzedne + ", kierunek: " + kierunek +", energia: " + energia +
                ", dlugosc_programu: " + dajDlugoscProgramu() + ", program: " + program + ", wiek: " + wiek;
    }
}
