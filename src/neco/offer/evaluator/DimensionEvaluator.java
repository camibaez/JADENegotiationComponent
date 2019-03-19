package neco.offer.evaluator;

import neco.offer.generator.DimensionGenerator;
import java.io.Serializable;

/**
 * Esta clase representa un criterio para evaluar el valor de una dimension de una oferta.
 * La evaluacion se produce a trav�s del m�todo <code>evalaute</code>. El resultado
 * de la evaluacion es la utilidad de esa dimension.
 * Las dimensiones tiene asignada una prioridad dada por el usuario.
 * @author Camilo B�ez Aneiros
 */
public abstract class DimensionEvaluator implements Serializable {
    /**
     * Prioridad de la dimension
     */
    protected float priority;   
    
    public DimensionEvaluator(float priority){
        this.priority = priority;
    }
    
    /**
     * Devuelve la utilidad de una dimension a partir del valor de esta.
     * @param dimensionValue Valor de la dimensi�n
     * @return La utilidad de la dimensi�n
     */
    public abstract float evaluate(Object dimensionValue);

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
    
    
    /**
     * Crea un <code>DimensionGenerator</code> a partir de las propiedades de 
     * este objeto. Este m�todo debe ser reimplementado para definir como se
     * crea el generador de dimensiones.
     * @return La instancia del generador de ofera
     * @see DimensionGenerator
     */
    public DimensionGenerator createDimensionGenerator(){
        return null;
    }
}
