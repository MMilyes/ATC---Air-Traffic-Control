/**
 * @authors  Mohammed Ilyes MALKI , Djouhoudi SOILIHI , Celil YILMAZ , Billel MOUSSA
 */
package engine.mobile;

import engine.map.Block;

public class MobileElement {
    protected Block position; // Position actuelle de l'élément mobile
    protected int speed; // Vitesse de déplacement de l'élément mobile
    protected Block destination; // Destination vers laquelle se dirige l'élément mobile

    public MobileElement(Block position) {
        this.position = position;
    }

    public MobileElement(Block position, Block destination) {
        this.position = position;
        this.destination = destination;
    }

    public Block getPosition() {
        return position;
    }

    public void setPosition(Block position) {
        this.position = position;
    }
}
