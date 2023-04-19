/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.process;

import engine.mobile.Plane;

public class ThreadAvion extends Thread {
    private Plane plane;  // l'avion associé à ce thread
    private double priorityflight;  // priorité de vol de l'avion
    private MobileElementManager manager;  // le gestionnaire d'éléments mobiles utilisé pour déplacer l'avion sur l'interface graphique

    public ThreadAvion(Plane plane, MobileElementManager manager) {
        this.plane = plane;
        this.manager = manager;
        this.priorityflight = (Math.random() * 50);  // initialiser la priorité de vol avec une valeur aléatoire entre 0 et 50
    }

    public double getPriorityflight() {
        return priorityflight;
    }

    public void setPriorityflight(double priorityflight) {
        this.priorityflight = priorityflight;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(plane.getSpeed());  // mettre le thread en pause pendant une durée équivalente à la vitesse de l'avion
                if (plane.isLanded()) {  // si l'avion a atterri, attendre 3 secondes avant de continuer
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  // afficher la trace de la pile d'erreur en cas d'interruption du thread
            }
            manager.movePlane(plane);  // déplacer l'avion sur l'interface graphique en utilisant le gestionnaire d'éléments mobiles
        }
    }
}

