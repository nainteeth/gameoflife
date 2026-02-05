{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk17
    processing
  ];

  shellHook = ''
    # Findet automatisch den Pfad zur core.jar im Nix-Store
    export CLASSPATH="$(find ${pkgs.processing} -name "core.jar" | head -n 1):."
    echo "Umgebung bereit. core.jar geladen."
  '';
}
