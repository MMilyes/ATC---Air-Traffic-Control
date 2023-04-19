/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.map;

import java.util.List;

public class TestMap {
    private Block[][] blocks; // tableau 2D d'objets Block représentant la carte
    private int width; // largeur de la carte
    private int height; // hauteur de la carte
    private int altrelief = 0; // valeur de relief d'altitude par défaut

    public TestMap(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new Block[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // définit la valeur de relief d'altitude en fonction de la position du bloc sur la carte
                if ((i < 280 && i > 240) && (j < 200 && j > 105)) {
                    altrelief = 300;
                } else if ((i < 330 && i > 225) && (j < 265 && j > 80)) {
                    altrelief = 200;
                } else if ((i < 210 && i > 105) && (j < 135 && j > 40)) {
                    altrelief = 200;
                } else if ((i < 55 && i > 15) && (j < 55 && j > 15)) {
                    altrelief = 100;
                } else if ((i < 160 && i > 25) && (j < 345 && j > 215)) {
                    altrelief = 100;
                } else {
                    altrelief = 0;
                }

                blocks[i][j] = new Block(i, j, altrelief); // crée un nouvel objet Block à la position i, j avec la valeur de relief d'altitude correspondante
            }
        }
    }

    public Block getBlock(int x, int y) {
        return blocks[x][y]; // renvoie l'objet Block à la position x, y
    }

    public int getWidth() {
        return width; // renvoie la largeur de la carte
    }

    public int getHeight() {
        return height; // renvoie la hauteur de la carte
    }


    public Block[][] getBlocks() {
        return blocks; // renvoie le tableau de blocs représentant la carte
    }


}