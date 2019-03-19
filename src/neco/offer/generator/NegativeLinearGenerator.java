package neco.offer.generator;

import neco.offer.evaluator.NegativeLinearEvaluator;


/**
 * Generador de dimension a partir de la funcion inversa de una funcion de evaluacion 
 * de tipo Lineal Negativa.
 * @author Camilo Báez Aneiros
 * @see NegativeLinearEvaluator
 */
public class NegativeLinearGenerator extends LinearGenerator{

    public NegativeLinearGenerator(float desiredValue, float priority) {
        super(-1, 100 + desiredValue, priority);
    }

}
