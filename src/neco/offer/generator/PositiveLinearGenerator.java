package neco.offer.generator;

import neco.offer.evaluator.PositiveLinearEvaluator;

/**
 * Generador de dimension a partir de la funcion inversa de una funcion de evaluacion 
 * de tipo Lineal Positiva.
 * @author Camilo Báez Aneiros
 * @see PositiveLinearEvaluator
 */
public class PositiveLinearGenerator extends LinearGenerator{

    public PositiveLinearGenerator(float desiredValue, float priority) {
        super(1, 100 - desiredValue, priority);
    }
    
}
