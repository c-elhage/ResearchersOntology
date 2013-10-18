package myclasses;

//Authors of this document: Cyril El Hage - Marc Nasrallah
//École supérieure d'ingénieurs de Beyrouth - USJ - Lebanon
//You may redistribute it in the hope that it will be useful
//Thank you for mentioning our names.

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class HTMLRequestConverter {
	
	
	public ShortFormProvider sfp;
	public DLQueryEngine engine;
        /**
         * constructs an HTMLRequestConverter that will convert the data from arrays to
         * a DLQuery in a manchesterOwlSyntax
         * @param _engine 
         *              The dlquery engine
         */
	public HTMLRequestConverter(DLQueryEngine _engine)
        {
		
                sfp = new SimpleShortFormProvider();
                engine = _engine;
        }
        /**
         * Builds the dl query string from the 3 parameters
         * @param prop
         *          property array
         * @param cond
         *          and/or array
         * @param value
         *          partial values array
         * @return 
         */
	
	
	public String Sender(String[] prop, String[] cond, String[] value){
//		
                StringBuilder sb = new StringBuilder();
		sb.append("(");
                int j = 0;
		for(int i=0;i<prop.length;i++)
                {
                                OWLObjectPropertyExpression expr = engine.isObjectProperty(prop[i]);
                                if(expr!=null)
                                {
                                
				Set<OWLNamedIndividual> individuals= engine.getIndividualsforObjectProperty(expr,value[i]);
                                     if(!individuals.isEmpty())
                                     {
                                
                                        Iterator<OWLNamedIndividual> it = individuals.iterator();
                                         sb.append("(");
                                            while(it.hasNext())
                                              {
							sb.append("("+prop[i]+" value "+sfp.getShortForm(it.next())+")");
                                                        if(it.hasNext())
                                                        {sb.append(" or ");}
                                              }
                                     sb.append(")");
                                        
                                     }
                                     else
                                      {
                                           sb.append("(" + prop[i] + " value nullind" + ")");
                                      }
                                }
                                else
                                {
                                    OWLDataPropertyExpression data = engine.isDataProperty(prop[i]);
                                    Set<OWLLiteral> literals = engine.getIndividualsforDataPropertyAndSubclasses(data, value[i]);
                                    if(!literals.isEmpty())
                                    {
                                    Iterator<OWLLiteral> it = literals.iterator();
                                     sb.append("(");
					while(it.hasNext())
                                        {
							sb.append("("+prop[i]+" value \""+it.next().getLiteral()+"\")");
                                                        if(it.hasNext())
                                                        {sb.append(" or ");}
				        }
                                      sb.append(")");
                                      
                                        
                                  }
                                   else
                                   {
                                        sb.append("(" + prop[i] + " value \"NullValue\"" + ")");
                                   }
                                }
                                         while(j<cond.length && (cond[j].equals("end or") || cond[j].equals("end and")))
                                        {
                                            sb.append(")");
                                            j++;
                                        }
                                        if(j<cond.length && (cond[j].equals("or")||cond[j].equals("and"))) 
                                        {
                                            sb.append(" " + cond[j]+" (");
                                            j++;
                                        } 
				}
                sb.append(")");                
		return sb.toString();

	}

}
