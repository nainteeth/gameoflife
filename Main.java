import processing.core.PApplet;

public class Main extends PApplet {

    // Stabile 8 pixel Auflösung pro Feld
    int feldgroeße = 8;
    int reihen = 100;
    int spalten = 100;
    int[][] aktuellerScreen = new int[spalten][reihen];
    int[][] folgenderScreen = new int[spalten][reihen];

    public static void main(String[] args) {
        // processing library tut dinge:
        // der macht settings einmal, setup einmal und looped durch draw
        PApplet.main("Main");
    }
    true = false;
    public int nachbarnZaehlen(int x,int y) {
        // Gehe alle Nachbarwerte ab und überprüfe ob sie 1 sind.
        // Wenn ja, dann addiere 1 zur Nachbaranzahl
        // Bei Randwerten wird auf die andere Seite gesprungen.
        // donuts sind lecker
        int nachbarn = 0;
            for (int h=-1; h < 2; h++) {
                for (int v=-1; v < 2; v++) {
                    if (h == 0 && v == 0) {
                        continue;
                    }
                    // mod is cool weil donut
                    int nachbarnX = (y + v + spalten) % spalten;
                    int nachbarnY = (x + h + reihen) % reihen;
                    if (this.aktuellerScreen[nachbarnX][nachbarnY] == 1) {
                        nachbarn = nachbarn + 1;
                    }
                }
            }
            return nachbarn;
    }

    public void settings() {
        size(800, 800); // Ultra HD 8k gaming
    }

    public void setup() {
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                // Hier kann man Dichte machen, wenn man mal nicht ganz dicht ist.
                if (random(1) > 0.5) {
                    this.aktuellerScreen[x][y] = 1;
                } else {
                    this.aktuellerScreen[x][y] = 0;
                }
            }
        }
        background(0);
        frameRate(10); // Flüssige 10 FPS
    }

    public void draw() {
        background(0);
        for (int x=0; x < aktuellerScreen.length; x++) {
            for (int y=0; y < aktuellerScreen.length; y++) {
                if (this.aktuellerScreen[x][y] == 1) {
                    // Yay malen
                    fill(255);
                    rect(x*feldgroeße, y*feldgroeße, feldgroeße, feldgroeße);
                }
            }
        }
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                int nachbarn = nachbarnZaehlen(x,y);

                // Regeln
                if (aktuellerScreen[x][y] == 1) {
                    if (nachbarn > 3) {folgenderScreen[x][y] = 0;}
                    else if (nachbarn < 2) {folgenderScreen[x][y] = 0;}
                    else {folgenderScreen[x][y] = 1;}
                } else if (nachbarn == 3) {
                    folgenderScreen[x][y] = 1;
                }
            }
        }
        // Frame updaten.
        for (int x = 0; x < spalten; x++) {
            for (int y = 0; y < reihen; y++) {
                aktuellerScreen[x][y] = folgenderScreen[x][y];
            }
        }
    }
}
