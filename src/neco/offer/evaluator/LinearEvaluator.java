package neco.offer.evaluator;

import neco.offer.generator.DimensionGenerator;
import neco.offer.generator.LinearGenerator;

/**
 * Representa una evaluador que utiliza una funcion lineal de tipo <code>y = mx + n</code>
 * para evaluar la utilidad del valor de una dimension.
 * En la inicializacion del objeto se reciben los valores de n y m (slope).
 * @author Camilo Báez Aneiros
 */
public class LinearEvaluator extends DimensionEvaluator{
    /**
     * Valor n de la funcion lineal
     */
    protected float n;
    /**
     * Pendiente de la funcion lineal
     */
    protected float slope;
    
    /**
     * @param slope Pendiente de la función lineal
     * @param n Valor n de la función lineal
     * @param priority Prioridad de la dimensión
     */
    public LinearEvaluator(float slope, float n, float priority){
        super(priority);
        this.slope = slope;
        this.n = n;
    }
    
    @Override
    public float evaluate(Object dimensionValue) {
        return ((float) dimensionValue) * slope + n;
    } 

    @Override
    public DimensionGenerator createDimensionGenerator() {
        return new LinearGenerator(n, slope, priority);
    }
    
    
    
    

}
