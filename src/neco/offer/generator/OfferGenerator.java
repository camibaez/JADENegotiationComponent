package neco.offer.generator;

import java.io.Serializable;
import neco.offer.Dimension;
import neco.offer.Offer;
import java.util.HashMap;
import java.util.Map;

/**
 * Genera una oferta a partir de la utilidad deseada. Va generando cada una de las
 * dimensiones de la oferta tomando en cuenta la utilidad que debe aportar cada una a
 * partir de la prioridad de la misma.
 * @author Camilo Báez Aneiros
 */
public class OfferGenerator implements Serializable{

    protected HashMap<String, DimensionGenerator> dimensionGeneratorMap;

    public OfferGenerator(HashMap<String, DimensionGenerator> dimensionEvaluatorMap) {
        this.dimensionGeneratorMap = dimensionEvaluatorMap;
    }

    public OfferGenerator() {
        
    }
   
    /**
     * Itera cada una de las funciones de generacion y calcula el valor de cada
     * dimension correspondiente a partir de la utilidad esperada. Va introduciendo
     * el valor de cada dimension en la nueva oferta y por ultimo la devuelve.
     * @param utility Utilidad esperada
     * @param offerTemplate Oferta que sirve como plantilla
     * @return Devuelve la nueva oferta.
     */
    public Offer generate(float utility, Offer offerTemplate){
        Offer newOffer = (Offer) offerTemplate.clone();
        for(Map.Entry<String, DimensionGenerator> generatorEntry : dimensionGeneratorMap.entrySet()){
            DimensionGenerator dimensionGenerator = generatorEntry.getValue();
            Object dimensionValue = dimensionGenerator.generate(utility);
            String dimensionName = generatorEntry.getKey();
            Dimension dimension = Dimension.generate(dimensionName, dimensionValue);
            newOffer.putDimension(dimension, dimensionName);
        }
        
        return newOffer;
    }
    

    
}
