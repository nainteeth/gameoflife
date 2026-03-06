public class Feld {
  boolean lebt;
  boolean infiziert;
  boolean immun;

  public Feld(boolean lebt, boolean zombievirus, boolean immun) {
    this.lebt = lebt;
    this.infiziert = zombievirus;
    this.immun = immun;
  }
}
