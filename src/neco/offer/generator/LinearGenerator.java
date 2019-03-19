package neco.offer.generator;

/**
 * Generador de dimension a partir de la funcion inversa de una funcion de evaluacion 
 * de tipo Lineal.
 * @author Camilo Báez Aneiros
 */
public class LinearGenerator extends DimensionGenerator{
    protected float n;
    protected float slope;
    
     public LinearGenerator(float n, float slope, float priority) {
        this.priority = priority;
        this.n = n;
        this.slope = slope;
    }
    
    @Override
    public Object generate(float utility) {
        return (utility - n) / slope;
    }

}
