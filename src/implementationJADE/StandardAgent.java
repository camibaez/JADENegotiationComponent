package implementationJADE;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.util.leap.Properties;

import java.util.HashMap;

/**
 * Esta clase extiende las funcionalidades de la clase base Agent de JADE. Contiene las
 * funcionalidades necesarias para que se mantenga una gesti�n din�mica de comportamientos y carga
 * propiedades de un fichero .properties cuyo nombre debe ser igual al del agente para que sea
 * reconocido por la clase. A�ade de forma est�ndar una instancia del comportamiento
 * Beh_CommandBehaviour para procesar los comandos de gesti�n de comportamientos que llegan junto a
 * un mensaje ACL.
 * 
 * @author Ing. Alejandro Alonso Fern�ndez
 * @author Ing. Inf. Altern�n Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnol�gicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public class StandardAgent extends Agent
{
	private static final long serialVersionUID = 1L;
	private Properties properties;
	private HashMap<String, Behaviour> behaviours;

	public StandardAgent()
	{
		properties = null;
		// si existe un fichero llamado [nombre del agente].properties
		// se cargan las propiedades de ah�
		properties = getBootProperties();
		behaviours = new HashMap<String, Behaviour>();
	}

	@Override
	public void addBehaviour(Behaviour behaviour)
	{
		if (!behaviours.containsKey(behaviour.getBehaviourName()))
		{
			super.addBehaviour(behaviour);
			behaviours.put(behaviour.getBehaviourName(), behaviour);
		}
	}

	@Override
	public void removeBehaviour(Behaviour behaviour)
	{
		Behaviour temp = behaviours.remove(behaviour.getBehaviourName());
		super.removeBehaviour(temp);
	}

	public void removeBehaviour(String behaviourName)
	{
		Behaviour behaviour = getBehaviour(behaviourName);
		if (behaviour != null)
		{
			removeBehaviour(behaviour);
			behaviours.remove(behaviour);
			behaviour = null;
		}
	}

	public Behaviour getBehaviour(Behaviour behaviour)
	{
		return behaviours.get(behaviour.getBehaviourName());
	}

	public Behaviour getBehaviour(String behaviourName)
	{
		return behaviours.get(behaviourName);
	}
	
	public int getBehavioursSize()
	{
		return behaviours.size();
	}

	@Override
	protected void setup()
	{
		InitPlatform.putStandardAgent(this);
		JoinPlatform.putStandardAgent(this);
		// al registrar los servicios en el agente DF
		// se asumen en el properties de esta forma:
		// DFServices=web,desktop
		if (properties != null)
		{
			String dfServices = properties.getProperty("DFServices");
			if (dfServices != null)
			{
				dfServices = dfServices.trim().replace(" ", "");
				String[] arrServices = dfServices.split(",");
				WorkDF.registerByTypes(this, arrServices);
			}
		}
	}
}
