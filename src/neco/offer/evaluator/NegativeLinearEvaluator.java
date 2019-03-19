package neco.offer.evaluator;

/**
 * Representa un evaluador que se basa en una funcion lineal con pendiente negativa (m < 0). 
 * Los valores de n y slope se ajustan a partir de un valor de utilida deseado que se recibe en
 * el constructor. Este valor aporta 100 unidadades de utilidad
 * @author Camilo Báez Aneiros
 */
public class NegativeLinearEvaluator extends LinearEvaluator {

    public NegativeLinearEvaluator(float desiredValue, float priority) {
        super(-1, 100 + desiredValue, priority);
    }

}
