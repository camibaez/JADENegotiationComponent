package neco.offer;

import neco.onto.concepts.DimensionConcept;
import java.io.Serializable;

/**
 * Esta clase representa una dimension de una oferta.
 * Es una clase generica donde T represta el tipo de valor de la dimension.
 * 
 * @author Camilo Baez
 */
public abstract class Dimension <T> implements Serializable{
    /**
     * Nombre de la dimension
     */
    protected String name;
    /**
     * Valor de la dimension
     */
    protected T value;
    
    public static String PRICE = "Price",
                         DELIVERY_TIME = "DeliveryTime";
    
    
    /**
     * Metodo factory que genera una dimension a partir de un nombre y un valor.
     * Los tipo de dimensiones que genera son:
     *  - NumericDimension
     *  
     * @param name
     * @param value
     * @return 
     */
    public static Dimension generate(String name, Object value){
        if(value instanceof Number)
            return new NumericDimension(name, (Float) value);
        return null;
    }
    
     /**
     * Metodo factory que crea una dimension (NumericDimension, StringDimension) 
     * a partir del atributo type de un objeto DimensionConcept
     * @param dimensionConcept
     * @return 
     */
    public static Dimension generate(DimensionConcept dimensionConcep){
        if(dimensionConcep.getType().equals(DimensionConcept.NUMBER_TYPE)){
            return new NumericDimension(dimensionConcep);
        }
        return null;
    }

   
    
    public boolean equals(Object o){
        //Son iguales si el objeto es Dimension<T> t si el valor de cada una es igual
        try{
            Dimension<T> d = (Dimension<T>) o;
            if(!d.name.equals(name))
                return false;
            if(!d.getValue().equals(value))
                return false;
        }catch (ClassCastException unused) {
            return false;
        }
        return true;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
    
    
    
    
    
   
}
