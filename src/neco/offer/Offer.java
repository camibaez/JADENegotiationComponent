package neco.offer;

import neco.onto.concepts.DimensionConcept;
import neco.onto.concepts.OfferConcept;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Esta clase es la representacion de una oferta, la oferta esta representada por dimensiones.
 * Las dimensiones se guardan en un Map<String, Dimension> donde la clave es el nombre de la dimension
 * y el valor el objeto que la representa.
 * 
 * @author Camilo Báez Aneiros
 */
public class Offer implements Serializable{
    /**
     * Mapa de dimensiones.
     */
    protected HashMap<String, Dimension> dimensions = new HashMap<String, Dimension>();
    
    public Offer(){
        dimensions = new HashMap<String, Dimension>();
    }
    
    public Offer(HashMap<String, Dimension> dimensions){
        this.dimensions = dimensions;        
    }
    
    public Offer(OfferConcept offer){
        for (Object dimensionObj : offer.getDimensions().toArray()) {
            DimensionConcept dimension = (DimensionConcept) dimensionObj;
            dimensions.put(dimension.getName(), Dimension.generate(dimension));
        }       
    }  
    /**
     * Agrega un grupo de dimensiones a su lista interna.
     * @param dimensions Dimensiones a agregar
     */
     public void loadDimensions(List<Dimension> dimensions){
        for (Dimension dimension : dimensions) {
            String name = ((Dimension) dimension).getName();
            putDimension(dimension, name);
        }
    }
   
    public HashMap<String,Dimension> getDimensionsMap(){
        return dimensions;
    }
    public void setDimensionsMap(HashMap<String, Dimension> dimensions){
        this.dimensions = dimensions;

    }
    
   
    /**
     * Obtiene una dimension a partir de su nombre
     * @param dimensionName
     * @return 
     */
    public Dimension getDimension(String dimensionName){
        return dimensions.get(dimensionName);
    }
    
    /**
     * Agrega una dimension tomando como clave el nombre de la dimension
     * @param dimension  Dimension a agregar
     */
    public void putDimension(Dimension dimension){
        dimensions.put(dimension.getName(), dimension);
    }
    
    /**
     * Agrega una dimension a partir de una clave dada.
     * @param dimension Dimension a agregar
     * @param dimensionName  Nombre de la dimension
     */
    public void putDimension(Dimension dimension, String dimensionName){
        dimensions.put(dimensionName, dimension);
    }
    
    @Override
    public Offer clone(){
        Offer offer = new Offer();
        HashMap clonedDimensions = (HashMap) dimensions.clone();
        offer.setDimensionsMap(clonedDimensions);
        return offer;
    }
    
    public boolean equals(Offer offer){
        return dimensions.equals(offer.dimensions);
    }
    
    
    
}
