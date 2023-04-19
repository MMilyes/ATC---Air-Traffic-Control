/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.process;

import engine.map.Block;
import engine.map.TestMap;

public class TrajectBoucle {

    private Block currentpos; // position actuelle
    private TestMap map; // carte de test
    private int iter; // itération

    public TrajectBoucle(Block currentpos, TestMap map, int iter) {
        this.currentpos = currentpos;
        this.map = map;
        this.iter = iter;
    }

    public Block T1() {
        int x = currentpos.x;
        int y = currentpos.y;
        // si x est inférieur à la hauteur de la carte et que l'itération est entre 0 et 29 inclus
        if (x < map.getHeight() && iter >= 0 && iter < 30) {
            x = x + 1; // incrémente x de 1
        }
        // si y est inférieur à la hauteur de la carte et que l'itération est entre 30 et 59 inclus
        if (y < map.getHeight() && iter >= 30 && iter < 60) {
            y = y + 1; // incrémente y de 1
        }
        // si x est supérieur à 0 et que l'itération est entre 60 et 89 inclus
        if (x > 0 && iter >= 60 && iter < 90) {
            x = x - 1; // décrémente x de 1
        }
        // si y est supérieur à 0 et que l'itération est entre 90 et 119 inclus
        if (y > 0 && iter >= 90 && iter < 120) {
            y = y - 1; // décrémente y de 1
        }
        // obtenir la nouvelle position
        Block newPosition = map.getBlock(x, y);
        return newPosition;
    }

    public Block T2() {
        int x = currentpos.x;
        int y = currentpos.y;
        // si x est supérieur à 0 et que l'itération est entre 0 et 29 inclus
        if (x > 0 && iter >= 0 && iter < 30) {
            x = x - 1; // décrémente x de 1
        }
        // si y est supérieur à 0 et que l'itération est entre 30 et 59 inclus
        if (y > 0 && iter >= 30 && iter < 60) {
            y = y - 1; // décrémente y de 1
        }
        // si x est inférieur à la hauteur de la carte et que l'itération est entre 60 et 89 inclus
        if (x < map.getHeight() && iter >= 60 && iter < 90) {
            x = x + 1; // incrémente x de 1
        }
        // si y est inférieur à la hauteur de la carte et que l'itération est entre 90 et 119 inclus
        if (y < map.getHeight() && iter >= 90 && iter < 120) {
            y = y + 1; // incrémente y de 1
        }
        // obtenir la nouvelle position
        Block newPosition = map.getBlock(x, y);
        return newPosition;
    }
    public Block T3(){
        int x= currentpos.x;
        int y=currentpos.y;
        if(x< map.getHeight() && iter>=0 && iter <30){
            x=x+1;
        }
        if(y>0 && iter>=30 && iter <60){
            y=y-1;
        }
        if(x>0 && iter>=60 && iter <90){
            x=x-1;
        }
        if(y< map.getHeight() && iter>=90 && iter <120){
            y=y+1;
        }
        Block newPosition = map.getBlock(x, y);
        return newPosition;
    }
    public Block T4(){
        int x= currentpos.x;
        int y=currentpos.y;
        if(x>0 && iter>=0 && iter <30){
            x=x-1;
        }
        if(y< map.getHeight() && iter>=30 && iter <60){
            y=y+1;
        }
        if(x< map.getHeight() && iter>=60 && iter <90){
            x=x+1;
        }
        if(y>0 && iter>=90 && iter <120){
            y=y-1;
        }
        Block newPosition = map.getBlock(x, y);
        return newPosition;
    }
}
