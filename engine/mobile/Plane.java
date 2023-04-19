/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.mobile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import engine.map.Block;

public class Plane extends MobileElement {

    private Block airport; // L'aéroport de départ
    private Block destination; // La destination
    private int trajBlc; // L'indice de la trajectoire dans la liste des blocs de la carte
    private int iterBoucle; // Le nombre d'itérations dans la boucle de vol
    private boolean boucle; // Indique si l'avion est en train de faire une boucle de vol
    private int altitude; // L'altitude de l'avion

    public int close; // La distance de l'avion par rapport à la destination
    public BufferedImage p1 = ImageIO.read(new File("images/plane.png")); // L'image de l'avion
    public BufferedImage p2 = ImageIO.read(new File("images/planeup.png")); // L'image de l'avion montant
    public BufferedImage p3 = ImageIO.read(new File("images/planeb.png")); // L'image de l'avion descendant
    public BufferedImage p4 = ImageIO.read(new File("images/planedown.png")); // L'image de l'avion descendant

    private Airport destAirport; // L'aéroport de destination
    private int speed; // La vitesse de l'avion
    public String path; // Le chemin de l'image de l'avion
    private boolean isLanded; // Indique si l'avion a atterri
    private String name; // Le nom de l'avion
    private boolean emergency; // Indique si l'avion est en situation d'urgence
    private int posCollision; // La position de collision de l'avion
    private BufferedImage planePic; // L'image actuelle de l'avion
    public static BufferedImage AirpPic; // L'image de l'aéroport

    static {
        try {
            AirpPic = ImageIO.read(new File("images/airport.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Plane(String name, Airport airport, Airport destination, int speed) throws IOException {
        super(airport.getPosition(), destination.getPosition());
        this.destAirport = destination;
        this.name = name;
        this.airport = airport.getPosition();
        this.destination = destination.getPosition();
        this.speed = speed;
        this.boucle = false;
        this.iterBoucle = 0;
        this.path = "images/plane.png";
        this.planePic = p1;

        close = 0;
    }

    public BufferedImage getPlanePic() {
        return planePic;
    }

    public void setPlanePic(BufferedImage planePic) {
        this.planePic = planePic;
    }

    public void setLanded(boolean isLanded) {
        this.isLanded = isLanded;
    }

    public boolean isLanded() {
        return isLanded;
    }

    public Block getDestination() {
        return destination;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public boolean isOnPosition(Block block) {
        return block.getX() == position.getX()
                && block.getY() == position.getY();
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(Airport destAirport) {
        this.destAirport = destAirport;
        this.destination = destAirport.getPosition();
    }

    public String getName() {
        return name;
    }

    public int getTrajBlc() {
        return trajBlc;
    }

    public void setTrajBlc(int trajBlc) {
        this.trajBlc = trajBlc;
    }

    public int getIterBoucle() {
        return iterBoucle;
    }

    public void setIterBoucle(int iterBoucle) {
        this.iterBoucle = iterBoucle;
    }

    public void setBoucle(boolean boucle) {
        this.boucle = boucle;
    }

    public boolean isBoucle() {
        return boucle;
    }

    public void setPosCollision(int posCollision) {
        this.posCollision = posCollision;
    }

    public void setEmergency(boolean b) {
        this.emergency = b;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public boolean CloseTo(Block block) {
        return (block.getX() == position.getX() && block.getY() == position.getY() + 1)
                || (block.getX() == position.getX() && block.getY() == position.getY() - 1)
                || (block.getX() == position.getX() + 1 && block.getY() == position.getY())
                || (block.getX() == position.getX() - 1 && block.getY() == position.getY())
                || (block.getX() == position.getX() + 1 && block.getY() == position.getY() + 1)
                || (block.getX() == position.getX() - 1 && block.getY() == position.getY() - 1)
                || (block.getX() == position.getX() + 1 && block.getY() == position.getY() - 1)
                || (block.getX() == position.getX() - 1 && block.getY() == position.getY() + 1);
    }




}
