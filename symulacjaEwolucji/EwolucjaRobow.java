package zad1.symulacjaEwolucji;

import zad1.plansza.Plansza;
import zad1.roby.Rob;
import zad1.stale.Parametry;
import zad1.stale.Wspolrzedne;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Klasa odpowiada za właściwe przeprowadzanie symulacji.
 */

public class EwolucjaRobow {
    private int tura = 1;
    private final Parametry parametry;
    private final ArrayList<Rob> roby;
    private final Plansza plansza;

    public EwolucjaRobow(Parametry parametry, Plansza plansza, ArrayList<Rob> roby) {
        this.parametry = parametry;
        this.plansza = plansza;
        this.roby = roby;
    }

    public void symuluj() {
        wypiszSzczegoloweDane();
        for (tura = 1; tura <= parametry.dajIleTur(); tura++) {
            parametry.kolejnaTura();
            kolejnaTura();
            wypiszPodstawoweDane();
            if (tura % parametry.dajCoIleWypisz() == 0) {
                wypiszSzczegoloweDane();
            }
        }
        wypiszSzczegoloweDane();
    }

    private void kolejnaTura() {
        ArrayList<Rob> robyDoDodania = new ArrayList<>();
        ArrayList<Rob> robyDoUsuniecia = new ArrayList<>();
        for (Rob rob : roby) {
            rob.kolejnaTura();
            if (rob.czyMaPotomstwo()) {
                robyDoDodania.add(rob.dajPotomstwo());
            }
            if (!rob.czyZyje()) {
                robyDoUsuniecia.add(rob);
            }
        }
        roby.addAll(robyDoDodania);
        roby.removeAll(robyDoUsuniecia);
        Collections.shuffle(roby);
    }

    private void wypiszPodstawoweDane() {
        System.out.printf("%d, rob: %d, żyw: %d, ", tura, roby.size(), plansza.dajLiczbePolZJedzeniem());
        for (int i = 0; i < Reporter.values().length; i++) {
            Reporter.values()[i].reportuj(roby);
            if (i != Reporter.values().length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private void wypiszSzczegoloweDane() {
        System.out.print("\n================================================\n\n");
        System.out.println("Informacje o robach: ");
        if (roby.size() == 0) {
            System.out.println("Wszystkie roby są martwe.");
        }
        for (Rob rob : roby) {
            System.out.println(rob);
        }
        wypiszPlansze();
        System.out.print("\n================================================\n\n");
    }

    private void wypiszPlansze() {
        boolean[][] czyJestRob = new boolean[plansza.dajRozmiarPlanszyY()][plansza.dajRozmiarPlanszyX()];
        for (Rob rob : roby) {
            czyJestRob[rob.dajWspolrzedne().dajY()][rob.dajWspolrzedne().dajX()] = true;
        }
        System.out.println("\nPlansza: (R - jest rob i jedzenie, r - jest rob i nie ma jedzenia)");
        for (int y = 0; y < plansza.dajRozmiarPlanszyY(); y++) {
            for (int x = 0; x < plansza.dajRozmiarPlanszyX(); x++) {
                if (plansza.dajPole(new Wspolrzedne(x, y)).czyMaJedzenie() && czyJestRob[y][x]) {
                    System.out.print("R");
                }
                else if (!plansza.dajPole(new Wspolrzedne(x, y)).czyMaJedzenie() && czyJestRob[y][x]) {
                    System.out.print("r");
                }
                else if (plansza.dajPole(new Wspolrzedne(x, y)).czyMaJedzenie()) {
                    System.out.print("X");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }
}
