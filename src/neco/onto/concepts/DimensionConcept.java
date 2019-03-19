package neco.onto.concepts;

import neco.offer.Dimension;
import jade.content.Concept;

/**
 * Concepto de Dimension
 * @author Camilo Báez Aneiros
 * @see Dimension
 */
public class DimensionConcept implements Concept{
    public static final String NUMBER_TYPE = "number",
                               STRING_TYPE = "string";
    
    protected String name;
    protected String type;
    protected String value;
    
    
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String toString(){
        return "" + value;
    }
    
    
    /**
     * Crea una DimensionConcept a partir de una Dimension. Esto es para obtener el equivalente ontologico
     * de la dimension.
     * @param dimension
     * @return 
     */
    public static DimensionConcept wrap(Dimension dimension){
        DimensionConcept concept = new DimensionConcept();
        concept.name = dimension.getName();
        concept.type = dimension.getValue() instanceof Number? NUMBER_TYPE : STRING_TYPE;
        concept.value = dimension.getValue().toString();
        return concept;
    }

}
