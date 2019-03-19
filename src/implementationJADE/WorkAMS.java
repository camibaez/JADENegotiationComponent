package implementationJADE;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;

/**
 * Esta clase contiene todo el trabajo que se realiza en coordinación con el AMS.
 * 
 * @author Ing. Inf. Alternán Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnológicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public class WorkAMS
{
	/**
	 * Devuelve un arreglo de AMSAgentDescription de los agentes según el nombre que poseen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el AMS.
	 * @param name
	 *            Nombre de los agentes que se quieren buscar.
	 * @return Devuelve un arreglo con los AMSAgentDescription de los agentes que cumplen con el
	 *         nombre pasado por parámetro. El arreglo puede tener "length == 0".
	 */
	public static AMSAgentDescription[] get_AgentDescription_ByName(Agent agent, String name)
	{
		AMSAgentDescription[] foundedAgents = null;

		AMSAgentDescription agenteDescription = new AMSAgentDescription();
		if (name != null && !name.isEmpty())
		{
			AID id = new AID();
			id.setLocalName(name);
			agenteDescription.setName(id);
		}
		try
		{
			SearchConstraints restricciones = new SearchConstraints();
			restricciones.setMaxResults(-1l);
			foundedAgents = AMSService.search(agent, agenteDescription, restricciones);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return foundedAgents;
	}
	
	/**
	 * Devuelve un arreglo de AID con los nombres de los agentes según el nombre que poseen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el AMS.
	 * @param name
	 *            Nombre de los agentes que se quieren buscar.
	 * @return Devuelve un arreglo con los nombres de los agentes que cumplen con el
	 *         nombre pasado por parámetro. El arreglo puede tener "length == 0".
	 */
	public static String[] get_AgentNames_ByName(Agent agent, String name)
	{
		AMSAgentDescription[] foundedAgents = get_AgentDescription_ByName(agent, name);
		String[] arrToReturn = new String[foundedAgents.length];
		for (int i = 0; i < foundedAgents.length; i++)
		{
			arrToReturn[i] = foundedAgents[i].getName().getLocalName();
		}
		return arrToReturn;
	}

	/**
	 * Toma el AID de un agente según el nombre que posee.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el AMS.
	 * @param name
	 *            Nombre del agente que se quiere buscar.
	 * @return Devuelve el AID del agente que cumple con el nombre pasado por parámetro.
	 */
	public static AID get_AgentAID_ByName(Agent agent, String name)
	{
		AMSAgentDescription[] foundedAgents = get_AgentDescription_ByName(agent, name);
		if (foundedAgents.length > 0)
		{
			return foundedAgents[0].getName();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Busca si existe un agente con el nombre pasado por parámetro y devuelve el resultado.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el AMS.
	 * @param name
	 *            Nombre del agente que se quiere buscar.
	 * @return Devuelve true si existe un agente con el nombre pasado por parámetro.
	 */
	public static boolean exist_AgentAID_ByName(Agent agent, String name)
	{
		AMSAgentDescription[] foundedAgents = get_AgentDescription_ByName(agent, name);
		if (foundedAgents.length == 1)
			return true;
		else
			return false;
	}
}
