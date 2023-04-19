/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.process;

import javax.swing.JPanel;
import java.io.IOException;
import java.util.List;

import engine.map.Block;
import engine.map.TestMap;
import engine.mobile.Plane;
import engine.mobile.Airport;

public class GameBuilder extends JPanel {

    public static TestMap buildMap() {
        return new TestMap(400, 400);
    }

    // Méthode statique pour construire un gestionnaire d'éléments mobiles
    public static MobileElementManager buildMobileElementManager(TestMap map, int nbPlanes, int nbAirports)
            throws IOException {
        MobileElementManager manager = new MobileElementManager(map);

        // Initialiser les aéroports dans le gestionnaire
        initializeAirports(manager);

        // Initialiser les avions dans le gestionnaire
        initializePlanes(manager);

        return manager;
    }

    private static void initializePlanes(MobileElementManager manager) throws IOException {

        List<Airport> airports = manager.getAirports();
        manager.addPlane(new Plane("Plane n°1", airports.get(0), airports.get(2), 15));
        manager.addPlane(new Plane("Plane n°2", airports.get(2), airports.get(1), 20));
        manager.addPlane(new Plane("Plane n°3", airports.get(3), airports.get(2), 40));
        manager.addPlane(new Plane("Plane n°4", airports.get(1), airports.get(2), 25));
        manager.addPlane(new Plane("Plane n°5", airports.get(0), airports.get(1), 35));
        manager.addPlane(new Plane("Plane n°6", airports.get(2), airports.get(3), 22));
        manager.addPlane(new Plane("Plane n°7", airports.get(1), airports.get(4), 28));
        manager.addPlane(new Plane("Plane n°8", airports.get(4), airports.get(1), 18));


    }

    private static void initializeAirports(MobileElementManager manager) {

        manager.addAirport(new Airport(new Block(345, 345), 2, "Airport 1"));
        manager.addAirport(new Airport(new Block(0, 345), 2, "Airport 2"));
        manager.addAirport(new Airport(new Block(345, 0), 2, "Airport 3"));
        manager.addAirport(new Airport(new Block(13, 11), 2, "Airport 4"));
        // add airport at the midde of the map
        manager.addAirport(new Airport(new Block(250, 150), 10, "Airport 5"));
        manager.addAirport(new Airport(new Block(100, 150), 2, "Airport 6"));


    }

}
