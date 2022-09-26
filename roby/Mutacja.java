package zad1.roby;

import zad1.stale.Parametry;

import java.util.ArrayList;

/**
 * Typ enumeracyjny implementujący poszczególne mutacje mogące wystąpić przy powielaniu roba.
 */

public enum Mutacja implements InterfejsMutacji {
    USUN_INSTRUKCJE_Z_KONCA {
        @Override
        public Program wykonajMutacje(Program program) {
            if (!this.czyWykonacMutacje() || program.czyPusty()) {
                return program;
            }
            ArrayList<Instrukcja> instrukcje = program.dajTabliceListeInstrukcji();
            instrukcje.remove(instrukcje.size() - 1);
            return program;
        }
    },
    DODAJ_INSTRUKCJE_NA_KONIEC {
        @Override
        public Program wykonajMutacje(Program program) {
            if (!this.czyWykonacMutacje() || Program.dajDlugoscSpisuInstrukcji() == 0) {
                return program;
            }
            ArrayList<Instrukcja> instrukcje = program.dajTabliceListeInstrukcji();
            int indeksInstrukcjiZeSpisu = parametry.dajLos().nextInt(parametry.dajSpisInstrukcji().length);
            instrukcje.add(parametry.dajSpisInstrukcji()[indeksInstrukcjiZeSpisu]);
            return program;
        }
    },
    ZMIEN_INSTRUKCJE {
        @Override
        public Program wykonajMutacje(Program program) {
            if (!this.czyWykonacMutacje() || program.czyPusty() || Program.dajDlugoscSpisuInstrukcji() == 0) {
                return program;
            }
            ArrayList<Instrukcja> instrukcje = program.dajTabliceListeInstrukcji();
            int indeksInstrukcjiDoZmiany = parametry.dajLos().nextInt(instrukcje.size());
            int indeksInstrukcjiZeSpisu = parametry.dajLos().nextInt(parametry.dajSpisInstrukcji().length);
            instrukcje.set(indeksInstrukcjiDoZmiany, parametry.dajSpisInstrukcji()[indeksInstrukcjiZeSpisu]);
            return program;
        }
    };

    private float prawdopodobienstwo;
    private static Parametry parametry;

    boolean czyWykonacMutacje() {
        return parametry.dajLos().nextFloat() < prawdopodobienstwo;
    }

    public void ustawPrawdopodobienstwo(float prawdopodobienstwo) {
        this.prawdopodobienstwo = prawdopodobienstwo;
    }

    public static void ustawParametry(Parametry parametry) {
        Mutacja.parametry = parametry;
    }
}
