/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.map.Block;
import engine.map.TestMap;
import engine.mobile.Airport;
import engine.mobile.Plane;

public class MobileElementManager {
    private TestMap map;

    private List<Plane> planes = new ArrayList<Plane>(); // Liste des avions
    private List<Airport> airports = new ArrayList<Airport>(); // Liste des aéroports

    private Lock lock; // Verrou de synchronisation pour éviter les conflits d'accès concurrents
    HashMap<Plane, ThreadAvion> threadAvions = new HashMap<Plane, ThreadAvion>(); // Liste des threads d'avions en vol

    public MobileElementManager() {
        airports = new ArrayList<Airport>(); // Initialisation de la liste des aéroports
        planes = new CopyOnWriteArrayList<Plane>(); // Initialisation de la liste des avions avec une version thread-safe
    }

    public MobileElementManager(TestMap map) {
        this.map = map;
        lock = new ReentrantLock(); // Initialisation du verrou de synchronisation
    }

    // Ajouter un avion à la liste
    public void addPlane(Plane plane) {
        lock.lock();
        try {
            planes.add(plane);
        } finally {
            lock.unlock();
        }
    }

    // Déclencher le vol d'un avion
    public void flight(Plane plane) {
        lock.lock();
        try {
            ThreadAvion thread = new ThreadAvion(plane, this); // Créer un thread pour l'avion donné
            threadAvions.put(plane, thread); // Ajouter le thread à la liste des threads en vol
            thread.start(); // Démarrer le thread
        } finally {
            lock.unlock();
        }
    }

    // Vérifier si deux avions sont proches l'un de l'autre et augmenter l'altitude du premier avion s'ils le sont
    public boolean isCloseAndRaiseAltitude(Plane plane1, Plane plane2) {
        // Calculer la distance entre les deux avions
        int distance = Math.abs(plane1.getPosition().getX() - plane2.getPosition().getX())
                + Math.abs(plane1.getPosition().getY() - plane2.getPosition().getY());

        // Si la distance est inférieure ou égale à 100 blocs, augmenter l'altitude du premier avion
        if (distance <= 100) {
            int currentAltitude = plane1.getAltitude();
            plane1.setAltitude(currentAltitude + 100);
            return true; // Retourner vrai pour indiquer que les avions sont proches
        }

        return false; // Retourner faux pour indiquer que les avions ne sont pas proches
    }

    public void movePlane(Plane plane) {
        lock.lock();
        int distance;
        String temp="vide";
        boolean prio = false;
        int altitude = 300;
        try {
            Airport airDest = plane.getDestAirport();
            // The plane do a looping
            if (plane.isBoucle()) {
                int iter = plane.getIterBoucle();
                if (iter == 120) {
                    plane.setBoucle(false);
                    plane.setIterBoucle(0);
                } else {
                    // Get the current position of the plane
                    Block currentPosition = plane.getPosition();
                    Block newPosition = currentPosition;
                    TrajectBoucle trajectBoucle = new TrajectBoucle(currentPosition, map, iter);
                    if (plane.getTrajBlc() == 1) {
                        newPosition = trajectBoucle.T1();
                    } else if (plane.getTrajBlc() == 2) {
                        newPosition = trajectBoucle.T2();
                    } else if (plane.getTrajBlc() == 3) {
                        newPosition = trajectBoucle.T3();
                    } else if (plane.getTrajBlc() == 4) {
                        newPosition = trajectBoucle.T4();
                    }
                    // Set the new position of the plane
                    plane.setPosition(newPosition);
                    // Set the new altitude of the plane
                    int altRelief = newPosition.getAltrelief();
                    altitude = altitude + altRelief;
                    plane.setAltitude(altitude);
                    // Set iter of the Boucle
                    plane.setIterBoucle(plane.getIterBoucle() + 1);
                }
            }
            // Si l'avion est à destination
            else if (plane.isLanded()) {
                plane.setPlanePic(plane.p1);
                // utiliser les methodes land et endLanding de la classe Airport
                try {
                    airDest.land();
                    System.out.println("L'avion " + plane.getName() + " a decole de l'aeroport "
                            + airDest.getName() + " à la position " + airDest.getPosition()
                            + " et il a notifié les autres qu'il est parti !");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                airDest.endLanding();
                // Si l'avion est à l'atterrissage
                airDest.removePlane(plane);
                plane.setAltitude(0);
                Airport rdmAprt = airports.get((int) (Math.random() * airports.size()));
                while (rdmAprt == plane.getDestAirport()) {
                    rdmAprt = airports.get((int) (Math.random() * airports.size()));
                }
                plane.setDestAirport(rdmAprt);
                plane.setLanded(false);
            } else if (!plane.isLanded() && !airDest.isFull()) {
                // Get the current position of the plane
                Block currentPosition = plane.getPosition();
                // Get the destination airport of the plane
                Block destinationAirport = plane.getDestination();
                // Get the line and column of the current position
                int currentLine = currentPosition.getX();
                int currentColumn = currentPosition.getY();
                // Get the line and column of the destination airport
                int destinationLine = destinationAirport.getX();
                int destinationColumn = destinationAirport.getY();
                // Calculate the new line and column of the plane
                int newLine = currentLine;
                int newColumn = currentColumn;
                if (currentLine < destinationLine) {
                    newLine = currentLine + 1;
                } else if (currentLine > destinationLine) {
                    newLine = currentLine - 1;
                }
                if (currentColumn < destinationColumn) {
                    newColumn = currentColumn + 1;
                } else if (currentColumn > destinationColumn) {
                    newColumn = currentColumn - 1;
                }
                // Get the new position of the plane
                Block newPosition = map.getBlock(newLine, newColumn);
                // Set the new position of the plane
                plane.setPosition(newPosition);
                // Set the new altitude of the plane
                int altRelief = newPosition.getAltrelief();
                altitude = altitude + altRelief;
                plane.setAltitude(altitude);
                // If the plane is on the destination airport, set the plane
                // landed
                if (plane.isOnPosition(destinationAirport)) {
                    plane.setLanded(true);
                    plane.setAltitude(0);
                    airDest.addPlane(plane);
                }
            }
            // Traject of the plane if the airport is full
            else if (!plane.isLanded() && plane.getDestAirport().isFull()) {
                // Get the current position of the plane
                Block currentPosition = plane.getPosition();
                // Get the destination airport of the plane
                Block destinationAirport = plane.getDestination();
                //
                if (currentPosition.x <= destinationAirport.x && currentPosition.y <= destinationAirport.y) {
                    plane.setTrajBlc(1);
                } else if (currentPosition.x > destinationAirport.x && currentPosition.y >= destinationAirport.y) {
                    plane.setTrajBlc(2);
                } else if (currentPosition.x < destinationAirport.x && currentPosition.y > destinationAirport.y) {
                    plane.setTrajBlc(3);
                } else if (currentPosition.x >= destinationAirport.x && currentPosition.y < destinationAirport.y) {
                    plane.setTrajBlc(4);
                }
                plane.setIterBoucle(0);
                //
                plane.setBoucle(true);
            }

            // Verifier si il ya un croisement entre avion
            for (Plane plane1 : planes) {
                if (plane1 == plane) {
                    continue;
                }
                int dX = plane.getPosition().x - plane1.getPosition().x;
                int dY = plane.getPosition().y - plane1.getPosition().y;
                double dist = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
                // changement d'altitude en cas de proximité dangereuse
                if (dist < 50) {
                    while (true) {

                        if (threadAvions.get(plane).getPriorityflight() > threadAvions.get(plane1)
                                .getPriorityflight()) {
                            plane.setAltitude(plane.getAltitude() + 50);
                            plane1.setPosCollision(-1);
                            break;
                        } else if (threadAvions.get(plane).getPriorityflight() < threadAvions.get(plane1)
                                .getPriorityflight()) {
                            plane.setAltitude(plane.getAltitude() - 50);
                            plane1.setPosCollision(1);
                            break;
                        } else if (threadAvions.get(plane).getPriorityflight() == threadAvions.get(plane1)
                                .getPriorityflight()) {
                            threadAvions.get(plane).setPriorityflight((Math.random() * 50));
                            continue;
                        }
                    }
                } else if (dist >= 50) {
                    plane1.setPosCollision(0);
                }
            }
            //
            if (plane.isBoucle()) {
                plane.setPlanePic(plane.p3);
            }
            else {
                distance = Math.abs(plane.getPosition().getX() - airDest.getPosition().getX())
                        + Math.abs(plane.getPosition().getY() - airDest.getPosition().getY());
                if (distance < 50) {
                    plane.setPlanePic(plane.p4);
                } else {
                    for (int i = 0; i < planes.size(); i++) {
                        Plane plane1 = planes.get(i);
                        distance = Math.abs(plane1.getPosition().getX() - plane.getPosition().getX())
                                + Math.abs(plane1.getPosition().getY() - plane.getPosition().getY());
                        // If the distance is less than or equal to 100 blocks, raise the altitude of
                        // the first plane
                        if (distance <= 30 && plane1.close == 0 && plane != plane1 && plane.close == 0) {
                            plane.close = 1;
                            temp = plane1.getName();
                            plane.setPlanePic(plane.p2);
                            //  plane1.setAltitude(plane1.getAltitude() + 100);
                            // Return true to indicate that the planes are close
                        }
                        if (distance > 30) {
                            if (temp != "vide") {
                                if (plane1.getName() == temp) {
                                    plane.setPlanePic(plane.p1);
                                    plane.close = 0;
                                    temp = "vide";
                                }
                            } else {
                                plane.setPlanePic(plane1.p1);
                                plane.close = 0;

                            }


                        }


                    }

                }
            }


        } finally {
            lock.unlock();
        }

    }

    public void addAirport(Airport airport) {
        airports.add(airport);
    }


    public List<Plane> getPlanes() {
        return planes;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public TestMap getMap() {
        return map;
    }

    public void setMap(TestMap map) {
        this.map = map;
    }

    public Plane getPlanebyName(String planeName) {
        for (Plane plane : planes) {
            if (plane.getName().equals(planeName)) {
                return plane;
            }
        }
        return null;
    }

}
