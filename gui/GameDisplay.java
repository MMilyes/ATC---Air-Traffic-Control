/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.map.TestMap;
import engine.mobile.Plane;
import engine.process.MobileElementManager;
import engine.mobile.Airport;

public class GameDisplay extends JPanel {

    // Chargement de l'image de fond
    public static BufferedImage bgImage;
    static {
        try {
            bgImage = ImageIO.read(new File("images/map1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PaintStrategy paintStrategy = new PaintStrategy(this);
    private MobileElementManager manager;

    public GameDisplay(TestMap map, MobileElementManager manager) {
        this.manager = manager;
    }

    // Méthode appelée lorsque le panneau doit être redessiné
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessin de l'image de fond
        g.drawImage(bgImage, 0, 0, null);

        // Copie de la liste des avions pour éviter une ConcurrentModificationException
        List<Plane> planesCopy = new ArrayList<>(manager.getPlanes());
        List<Airport> airportsCopy = new ArrayList<>(manager.getAirports());

        // Itération sur la copie de la liste des avions
        for (Plane plane : planesCopy) {
            // Si l'avion est en état d'urgence
            if (plane.isEmergency()) {
                // Dessin de l'avion en état d'urgence
                paintStrategy.paintEmergency(g, plane);
                // Redirection de l'avion vers l'aéroport d'urgence
                plane.setDestAirport(airportsCopy.get(4));
            } else {
                // Dessin de l'avion normal
                paintStrategy.paint(g, plane);
            }
            // Si l'avion a atterri, il n'est plus en état d'urgence
            if (plane.isLanded()) {
                plane.setEmergency(false);
            }
        }

        // Itération sur la liste des aéroports
        for (Airport airport : manager.getAirports()) {
            // Dessin de l'aéroport
            paintStrategy.paint(g, airport);
        }
    }
}
