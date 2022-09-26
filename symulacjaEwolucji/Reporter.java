package zad1.symulacjaEwolucji;

import zad1.roby.Rob;

import java.util.ArrayList;

/**
 * Typ enumeracyjny odpowiadający za wypisywanie danych dotyczących zmiennych, które wymagają przejścia po wszystkich
 * robach.
 */

public enum Reporter implements InterfejsReportera {
    REPORTER_DLUGOSCI_PROGRAMU("prg") {
        @Override
        public int dajWartosc(Rob rob) {
            return rob.dajDlugoscProgramu();
        }
    },
    REPORTER_ENERGII("energ") {
        @Override
        public int dajWartosc(Rob rob) {
            return rob.dajEnergie();
        }
    },
    REPORTER_WIEKU("wiek") {
        @Override
        public int dajWartosc(Rob rob) {
            return rob.dajWiek();
        }
    };

    private final String skrot;

    Reporter(String skrot) {
        this.skrot = skrot;
    }

    void reportuj(ArrayList<Rob> roby) {
        int minimalnaWartosc = roby.size() == 0 ? 0 : dajWartosc(roby.get(0));
        int maksymalnaWartosc = minimalnaWartosc;
        float sredniaWartosc = 0;
        for (Rob rob : roby) {
            minimalnaWartosc = Math.min(minimalnaWartosc, rob.dajDlugoscProgramu());
            maksymalnaWartosc = Math.max(maksymalnaWartosc, rob.dajDlugoscProgramu());
            sredniaWartosc += rob.dajDlugoscProgramu();
        }
        if (roby.size() != 0) {
            sredniaWartosc /= roby.size();
        }

        System.out.print(skrot + ": " + minimalnaWartosc + "/" + sredniaWartosc + "/" +
                maksymalnaWartosc);
    }
}
