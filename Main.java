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
    Feld[][] aktuellerScreen = new Feld[spalten][reihen];
    Feld[][] folgenderScreen = new Feld[spalten][reihen];
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
                    if (aktuellerScreen[nachbarnX][nachbarnY].lebt) {
                        nachbarn++;
                    }
                }
            }
            return nachbarn;
    }

    public void settings() {
        size(fensterbreite, fensterhoehe);
    }

    public void setup() {
        // Initialisiere aktuellerScreen
        for (int x = 0; x < aktuellerScreen.length; x++) {
            for (int y = 0; y < aktuellerScreen[x].length; y++) {
                aktuellerScreen[x][y] = new Feld(false, false);
            }
        }
        // Initialisiere folgenderScreen
        for (int x = 0; x < folgenderScreen.length; x++) {
            for (int y = 0; y < folgenderScreen[x].length; y++) {
                folgenderScreen[x][y] = new Feld(false, false);
            }
        }
        // Weise aktuellerScreen zufällige Werte zu
        zufallswerte();
        // Hintergrundfarbe (weiß)
        background(0);
        // Wiederholrate für draw()
        frameRate(6);
    }

    public void draw() {
        background(0);
        // Zeichne die Werte aus aktuellerScreen
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                if (aktuellerScreen[x][y].lebt) {
                    fill(255);
                    rect(x*feldgroeße, y*feldgroeße, feldgroeße, feldgroeße);
                }
            }
        }
        if (!pausiert) {
            regelnAnwenden();
        }
    }
    public void zufallswerte() {
        for (int x=0; x < spalten; x++) {
            for (int y=0; y < reihen; y++) {
                if (random(1) > (1-dichte)) {
                    this.aktuellerScreen[x][y].lebt = true;
                } else {
                    this.aktuellerScreen[x][y].lebt = false;
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
                if (this.aktuellerScreen[x][y].lebt) {
                    if (nachbarn == 2 || nachbarn == 3) {
                        this.folgenderScreen[x][y].lebt = true;
                    } else { folgenderScreen[x][y].lebt = false; }
                } else {
                    if (nachbarn == 3) {
                        this.folgenderScreen[x][y].lebt = true;
                    } else {
                        this.folgenderScreen[x][y].lebt = false;
                    }
                }
            }
        }
        aktualisiereScreen();
    }

    // Überschreibe alle Werte von aktuellerScreen mit den von folgenderScreen
    public void aktualisiereScreen() {
        for (int x = 0; x < spalten; x++) {
            for (int y = 0; y < reihen; y++) {
                aktuellerScreen[x][y].lebt = folgenderScreen[x][y].lebt;
                aktuellerScreen[x][y].zombievirus = folgenderScreen[x][y].zombievirus;
            }
        }
    }

    public void mousePressed() {
        int x = mouseX/feldgroeße;
        int y = mouseY/feldgroeße;
        if (aktuellerScreen[x][y].lebt) {
            aktuellerScreen[x][y].lebt = false;
        } else {
            aktuellerScreen[x][y].lebt = true;
        }
    }
    public void keyPressed() {
        if (key == 'p') {
            if (pausiert) {
                pausiert = false;
            } else {
                pausiert = true;
            }
        }
        if (key == 'l') {
            for (int x=0; x < spalten; x++) {
                for (int y=0; y < reihen; y++) {
                    aktuellerScreen[x][y].lebt = false;
                }
            }
        }
        if (key == 'z') {
            zufallswerte();
        }
    }
}
