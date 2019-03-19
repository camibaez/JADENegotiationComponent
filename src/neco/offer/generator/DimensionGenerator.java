package neco.offer.generator;

import java.io.Serializable;

/**
 * Esta clase se encarga de generar el valor de una dimension a partir de la utilidad 
 * que se desea que aporte esa dimension.
 * @author Camilo Báez Aneiros
 */
public abstract class DimensionGenerator implements Serializable{
    protected float priority;
    /**
     * Genera el valor de una dimension a partir de la utilidad que debe aportar esta.
     * @param utility
     * @return Un objeto que representa el valor de la dimension.
     */
    public abstract Object generate(float utility);

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
}
