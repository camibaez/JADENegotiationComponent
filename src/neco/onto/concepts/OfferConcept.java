package neco.onto.concepts;

import neco.offer.Dimension;
import neco.offer.Offer;
import jade.content.Concept;
import jade.util.leap.ArrayList;
import jade.util.leap.List;


/**
 * Concepto de Oferta.
 * @author Camilo Báez Aneiros
 * @see Offer
 */
public class OfferConcept implements Concept{
    
    protected List dimensions;
    
    public OfferConcept(){
        dimensions = new ArrayList();
       
    }
    
    
    
    //@AggregateSlot(cardMin = 1, cardMax = -1)
    public List getDimensions(){
        return dimensions;
    }
    
    public void setDimensions(List dimensions){
        this.dimensions = dimensions;
    }

    /**
     * Genera un OfferConcept a partir de una Offer. Esto es para obetener una
     * Offer ontologica.
     * @param offer
     * @return 
     */
   public static OfferConcept wrap(Offer offer){
        OfferConcept concept = new OfferConcept();
        for (Dimension dimension : offer.getDimensionsMap().values()) {
           DimensionConcept dimensionConcept = DimensionConcept.wrap(dimension);
           concept.dimensions.add(dimensionConcept);
       }
        return concept;
    }

}
