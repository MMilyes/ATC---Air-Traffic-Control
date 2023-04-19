/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.mobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import engine.map.Block;

public class Airport extends MobileElement {
    private String name; // Nom de l'aéroport
    private int capacity; // Capacité maximale de l'aéroport (nombre de places pour les avions)
    private int currentCapacity; // Nombre d'avions actuellement garés à l'aéroport
    private List<Plane> planes; // Liste des avions garés à l'aéroport
    private boolean isLanding; // Indique si un avion est en train d'atterrir
    private boolean planeHasLanded = false; // Indique si un avion a fini d'atterrir
    public static BufferedImage AirpPic; // Image représentant l'aéroport

    static {
        try {
            AirpPic = ImageIO.read(new File("images/airport.png")); // Chargement de l'image de l'aéroport
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Airport(Block position, int capacity, String name) {
        super(position);
        this.capacity = capacity;
        this.name = name;
        this.currentCapacity = 0;
        this.planes = new LinkedList<>(); // Initialisation de la liste d'avions garés à l'aéroport
        this.isLanding = false;
        new Object();
    }

    public BufferedImage getAirpPic() {
        return AirpPic;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public boolean isFull() {
        return currentCapacity == capacity; // L'aéroport est considéré comme plein si le nombre d'avions garés atteint la capacité maximale
    }

    public void addPlane(Plane plane) {
        currentCapacity++; // Incrémentation du nombre d'avions garés
        planes.add(plane); // Ajout de l'avion à la liste
    }

    public void removePlane(Plane plane) {
        currentCapacity--; // Décrémentation du nombre d'avions garés
        planes.remove(plane); // Suppression de l'avion de la liste
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized void land() throws InterruptedException {
        while (currentCapacity <= 0 || planeHasLanded) {
            wait(); // Attente tant qu'il n'y a pas de place libre à l'aéroport ou qu'un avion est en train d'atterrir
        }
        currentCapacity--; // Incrémentation du nombre d'avions garés
        planeHasLanded = true; // L'avion a fini d'atterrir
        notifyAll(); // Notification des threads en attente
    }

    public synchronized void endLanding() {
        currentCapacity++; // Décrémentation du nombre d'avions garés
        planeHasLanded = false; // L'avion n'a plus fini d'atterrir
        notifyAll(); // Notification des threads en attente
    }
}
