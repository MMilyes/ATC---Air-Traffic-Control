/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import engine.map.TestMap;
import engine.process.GameBuilder;
import engine.process.MobileElementManager;

public class MainGui extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private TestMap map;
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 850;
    private GameDisplay dashboard;
    private InformationBoard infoboard;
    private MobileElementManager manager;
    private int userinput;
    private int ui2;
    private boolean paused = false;

    public MainGui(String title, int userinput, int ui2) throws IOException {
        super(title);
        this.setSize(WIDTH, HEIGHT); // Définition de la taille de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Définition du comportement de la fenêtre à la fermeture
        this.setResizable(false); // Empêcher la redimension de la fenêtre
        this.setVisible(true); // Afficher la fenêtre
        this.setLocationRelativeTo(null); // Positionner la fenêtre au centre de l'écran
        this.userinput = userinput;
        this.ui2 = ui2;

        init(); // Initialisation de la fenêtre
    }

    private void init() throws IOException {
        Container contentPane = this.getContentPane();

        // Initialisation de la carte de jeu et du gestionnaire d'éléments mobiles
        map = GameBuilder.buildMap();
        manager = GameBuilder.buildMobileElementManager(map, userinput, ui2);

        // Création des différents panneaux de la fenêtre
        dashboard = new GameDisplay(map, manager);
        infoboard = new InformationBoard(manager);

        // Création du bouton pause/resume
        JButton pauseResumeButton = new JButton("Pause/Reprise");
        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = !paused; // Inversion de l'état de pause
            }
        });

        // Ajout des différents éléments à la fenêtre
        contentPane.add(pauseResumeButton, "North");
        contentPane.add(dashboard, "Center");
        contentPane.add(infoboard, "East");

        dashboard.setPreferredSize(new Dimension(800, 800)); // Définition de la taille du panneau de jeu
        infoboard.setPreferredSize(new Dimension(400, 800)); // Définition de la taille du panneau d'informations

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setResizable(true);

    }

    @Override
    public void run() {
        boolean start = true;
        infoboard.init(); // Initialisation du panneau d'informations
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Pause/Reprise de tous les threads
            while (paused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<engine.mobile.Plane> planes = manager.getPlanes(); // Récupération des avions du gestionnaire d'éléments mobiles
            if (start == true) {
                for (engine.mobile.Plane plane : planes) {
                    manager.flight(plane); // Lancement du vol des avions au début de la partie
                }
                start = false;
            }
            infoboard.updateInfos();
            infoboard.update();
            // manager.movePlanes();
            dashboard.repaint();
        }
    }
}
