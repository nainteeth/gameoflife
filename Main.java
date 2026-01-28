import processing.core.PApplet;

public class Main extends PApplet {

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
    public int nachbarnZaehlen(int x,int y) {
        // Gehe alle Nachbarwerte ab und überprüfe ob sie 1 sind.
        // Wenn ja, dann addiere 1 zur Nachbaranzahl
        // Bei Randwerten wird auf die andere Seite gesprungen.
        int nachbarn = 0;
            for (int h=-1; h < 2; h++) {
                for (int v=-1; v < 2; v++) {
                    if (h == 0 && v == 0) {
                        continue;
                    }
                    int nachbarnX = (x + v + spalten) % spalten;
                    int nachbarnY = (y + h + reihen) % reihen;
                    if (this.aktuellerScreen[nachbarnX][nachbarnY] == 1) {
                        nachbarn = nachbarn + 1;
                    }
                }
            }
            return nachbarn;
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                if (random(1) > 0.8) {
                    this.aktuellerScreen[x][y] = 1;
                } else {
                    this.aktuellerScreen[x][y] = 0;
                }
            }
        }
        background(0);
        frameRate(4);
    }

    public void draw() {
        background(0);
        for (int x=0; x < aktuellerScreen.length; x++) {
            for (int y=0; y < aktuellerScreen.length; y++) {
                if (this.aktuellerScreen[x][y] == 1) {
                    fill(255);
                    rect(x*feldgroeße, y*feldgroeße, feldgroeße, feldgroeße);
                }
            }
        }
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                int nachbarn = nachbarnZaehlen(x,y);

        if (aktuellerScreen[x][y] == 1) {
            if (nachbarn == 2 || nachbarn == 3) {
                folgenderScreen[x][y] = 1;
            } else { folgenderScreen[x][y] = 0; }
        } else {
            if (nachbarn == 3) {
                folgenderScreen[x][y] = 1;
            } else {
                folgenderScreen[x][y] = 0;
            }
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
