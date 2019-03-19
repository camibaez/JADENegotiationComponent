package neco.offer.evaluator;

import neco.offer.Dimension;
import neco.offer.Offer;
import neco.offer.generator.DimensionGenerator;
import neco.offer.generator.OfferGenerator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * Esta clase es la encargada de evaluar una Offer. 
 * Evalúa todas las dimensiones siguiendo un criterio y devuelve la utilidad de esa oferta.
 * @author Camilo Báez Aneiros
 */
public class OfferEvaluator implements Serializable {
    /**
     * Diccionario de dimensiones. Las claves representan los nombres de las dimensiones
     * y los valres las intansicas los evaluadores de estas.
     */
    protected HashMap<String, DimensionEvaluator> dimensionEvaluatorMap;
    
    public OfferEvaluator(){
        dimensionEvaluatorMap = new HashMap<String, DimensionEvaluator>();
    }
    
    public OfferEvaluator(HashMap<String, DimensionEvaluator> dimensionEvaluatorMap) {
        this.dimensionEvaluatorMap = dimensionEvaluatorMap;
    }
       
    /**
     * Este método calcula la utilidad de la oferta asignada. Devuelve el valor acumulado
     * de cada una de las dimensiones que se haya decidido evaluar utilizando los DimensionEvaluator
     * correspondientes y teniendo en cuenta la prioridad de las mismas.
     * 
     * @param offer Oferta a evaluar
     * @return Devuelve la utilidad de la oferta. 
     */
    public float evaluate(Offer offer) {
        float offerUtility = 0;

        for (Entry<String, DimensionEvaluator> evaluatorEntry : dimensionEvaluatorMap.entrySet()) {
            if(offer.getDimensionsMap().containsKey(evaluatorEntry.getKey())){
                Dimension dimension = offer.getDimensionsMap().get(evaluatorEntry.getKey());
                Object dimensionValue = dimension.getValue();
                float dimensionUtility = evaluatorEntry.getValue().evaluate(dimensionValue);
                float priority = evaluatorEntry.getValue().getPriority();
                
                offerUtility += dimensionUtility * priority;
            }
        }

        return offerUtility;
    }
    
    public HashMap<String, DimensionEvaluator> getDimensionEvaluatorMap() {
        return dimensionEvaluatorMap;
    }

    public void setDimensionEvaluatorMap(HashMap<String, DimensionEvaluator> dimensionEvaluatorMap) {
        this.dimensionEvaluatorMap = dimensionEvaluatorMap;
    }

    /**
     * Crea un generador de oferta a partir de este evaluador. El método itera por
     * cada una de las funciones de evalaución y crea funciones de generación de dimensiones
     * invocando a su metodo <code>createDimensionGenerator()</code>. Los generadores de 
     * dimensiones que se vallan creando se agregan al generador de oferta.
     * @return Devuelve el generador de ofertas creado
     */
    public OfferGenerator createOfferGenerator(){
        HashMap<String, DimensionGenerator> dimensionGeneratorMap = new HashMap<String, DimensionGenerator>();
         for (Entry<String, DimensionEvaluator> evaluatorEntry : dimensionEvaluatorMap.entrySet()) {
            dimensionGeneratorMap.put(evaluatorEntry.getKey(), evaluatorEntry.getValue().createDimensionGenerator());
        }
        return new OfferGenerator(dimensionGeneratorMap);
    }

}
