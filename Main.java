import processing.core.PApplet;

public class Main extends PApplet {
    // Attribute
    int fensterhoehe = 1080;
    int fensterbreite = 1920;
    int feldgroeße = 16;
    // Dichte für die Zufallswerte
    double dichte = 0.15;

    int reihen = fensterhoehe/feldgroeße;
    int spalten = fensterbreite/feldgroeße;
    int[][] aktuellerScreen = new int[spalten][reihen];
    int[][] folgenderScreen = new int[spalten][reihen];
    boolean pausiert = false;

    // Main Methode
    public static void main(String[] args) {
        PApplet.main("Main"); // Führt settings, setup und draw aus.
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
        size(fensterbreite, fensterhoehe);
    }

    public void setup() {
        // Weise aktuellerScreen zufällige Werte zu
        zufallswerte();
        // Hintergrundfarbe (weiß)
        background(0);
        // Wiederholrate für draw()
        frameRate(4);
    }

    public void draw() {
        background(0);
        // Zeichne die Werte aus aktuellerScreen
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                if (this.aktuellerScreen[x][y] == 1) {
                    fill(255);
                    rect(x*feldgroeße, y*feldgroeße, feldgroeße, feldgroeße);
                }
            }
        }
        if (pausiert == false) {
            regelnAnwenden();
        }
    }
    public void zufallswerte() {
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                if (random(1) > (1-dichte)) {
                    this.aktuellerScreen[x][y] = 1;
                } else {
                    this.aktuellerScreen[x][y] = 0;
                }
            }
        }
    }

    public void regelnAnwenden() {
        // Zähle die Nachbarn für alle Kästen
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                int nachbarn = nachbarnZaehlen(x,y);

        // Wende die Regeln an
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
        // Überschreibe alle Werte von aktuellerScreen mit den von folgenderScreen
        for (int x = 0; x < spalten; x++) {
            for (int y = 0; y < reihen; y++) {
                aktuellerScreen[x][y] = folgenderScreen[x][y];
            }
        }
    }

    public void mousePressed() {
        int x = mouseX/feldgroeße;
        int y = mouseY/feldgroeße;
        if (aktuellerScreen[x][y] == 1) {
            aktuellerScreen[x][y] = 0;
        } else {
            aktuellerScreen[x][y] = 1;
        }
    }
    public void keyPressed() {
        if (key == 'p') {
            if (pausiert == true) {
                pausiert = false;
            } else {
                pausiert = true;
            }
        }
        if (key == 'l') {
            for (int x=0; x < spalten; x++) {
                for (int y=0; y < reihen; y++) {
                    aktuellerScreen[x][y] = 0;
                }
            }
        }
        if (key == 'z') {
            zufallswerte();
        }
    }
}
