/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import engine.map.Block;
import engine.map.TestMap;
import engine.mobile.Plane;
import engine.mobile.Airport;

public class PaintStrategy {

    public static BufferedImage planePic, AirpPic;
    public int taille = 2;
    public GameDisplay gd;

    public PaintStrategy(GameDisplay gameDisplay) {
        this.gd = gameDisplay;
    }


    public void paint(Graphics g, Plane plane) {
        if (plane == null) {
            return;
        }
        planePic = plane.getPlanePic();
        Block block = plane.getPosition();
        g.drawImage(planePic, block.getX() * taille, block.getY() * taille, gd);
        // add the altitude of the plane to the image
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(plane.getAltitude()), block.getX() * taille, block.getY() * taille);
    }

    public void paintEmergency(Graphics g, Plane plane) {
        if (plane == null) {
            return;
        }
        try {
            planePic = ImageIO.read(new File("images/emergencyPlane.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Block block = plane.getPosition();
        g.drawImage(planePic, block.getX() * taille, block.getY() * taille, gd);
        // add the altitude of the plane to the image
        g.setColor(Color.RED);
        g.drawString(Integer.toString(plane.getAltitude()), block.getX() * taille, block.getY() * taille);
        // add a red cross to the image
        g.setColor(Color.RED);
        g.drawLine(block.getX() * taille, block.getY() * taille, block.getX() * taille + taille,
                block.getY() * taille + taille);
        g.drawLine(block.getX() * taille, block.getY() * taille + taille, block.getX() * taille + taille,
                block.getY() * taille);

    }


    public void paint(Graphics g, Airport airport) {
        if (airport == null) {
            return;
        }
        AirpPic = airport.getAirpPic();
        Block block = airport.getPosition();
        g.drawImage(AirpPic, block.getX() * taille, block.getY() * taille, gd);
        // add the capacity of the airport to the image
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(airport.getCapacity() - airport.getCurrentCapacity()), block.getX() * taille,
                block.getY() * taille);
        // g.fillRect(block.getLine() * taille, block.getColumn() * taille, taille,
        // taille);
    }
}
