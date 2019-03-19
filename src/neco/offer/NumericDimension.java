package neco.offer;

import neco.onto.concepts.DimensionConcept;

/**
 * Representa una dimension numerica. Esto quiere decir que su atributo value 
 * es un numero.
 * @author Camilo Báez Aneiros
 */
public class NumericDimension extends Dimension<Float>{

    /**
     * Genera una dimension a partir de un nombre y un valor
     * @param name Nombre de la dimension
     * @param value Valor de la dimension
     */
    public NumericDimension(String name, Float value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * Genera una dimension a partir de un objeto DimensionConcept
     * @param dimension 
     */
    public NumericDimension(DimensionConcept dimension){
        this.name = dimension.getName();
        this.value = Float.parseFloat(dimension.getValue());
    }
      
    
      
}
