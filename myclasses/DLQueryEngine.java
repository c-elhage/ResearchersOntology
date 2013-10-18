package myclasses;
//Authors of this document: Cyril El Hage - Marc Nasrallah
//École supérieure d'ingénieurs de Beyrouth - USJ - Lebanon
//You may redistribute it in the hope that it will be useful
//We have modified the file from the OWL API in order to adapt it to our
//project
//Thank you for mentioning our names.
/** Author: Matthew Horridge
 *The University of Manchester
 * Bio-Health Informatics Group
 * Date: 13-May-2010 
*/
/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
//DLQueryEngine works on a partial query and returns all individuals that verify this query.
class DLQueryEngine  {
    private OWLReasoner reasoner;
    private DLQueryParser parser;
    private SimpleShortFormProvider sfp;
    
    /** Constructs a DLQueryEngine. This will answer "DL queries" using the
     * specified reasoner. A short form provider specifies how entities are
     * rendered.
     * 
     * @param reasoner
     *            The reasoner to be used for answering the queries.
     * @param shortFormProvider
     *            A short form provider. */
    
    public DLQueryEngine(OWLReasoner reasoner)
    {
        this.reasoner = reasoner;
        parser = new DLQueryParser(reasoner.getRootOntology());
        sfp = new  SimpleShortFormProvider();
    }
    /** Gets the superclasses of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *            The string from which the class expression will be parsed.
     * @param direct
     *            Specifies whether direct superclasses should be returned or
     *            not.
     * @return The superclasses of the specified class expression
     * @throws ParserException
     *             If there was a problem parsing the class expression. */
    
    public Set<OWLClass> getSuperClasses(String classExpressionString, boolean direct)
            throws ParserException {
        if (classExpressionString.trim().length() == 0) {
                    return Collections.emptySet();
                }
        
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLClass> superClasses = reasoner
                .getSuperClasses(classExpression, direct);
        return superClasses.getFlattened();
        
    }
      /** Gets the equivalent classes of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *            The string from which the class expression will be parsed.
     * @return The equivalent classes of the specified class expression
     * @throws ParserException
     *             If there was a problem parsing the class expression. */
   

       public Set<OWLClass> getEquivalentClasses(String classExpressionString)
            throws ParserException {
        if (classExpressionString.trim().length() == 0) {
                    return Collections.emptySet();
                }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        Node<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(classExpression);
        Set<OWLClass> result;
        
        if (classExpression.isAnonymous())
        {
                    result = equivalentClasses.getEntities();
        }
        else
        {
                    result = equivalentClasses.getEntitiesMinus(classExpression.asOWLClass());
        }
        return result;
    }

 
    /** Gets the subclasses of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *            The string from which the class expression will be parsed.
     * @param direct
     *            Specifies whether direct subclasses should be returned or not.
     * @return The subclasses of the specified class expression
     * @throws ParserException
     *             If there was a problem parsing the class expression. */
    public Set<OWLClass> getSubClasses(String classExpressionString, boolean direct)
            throws ParserException
    {
        if (classExpressionString.trim().length() == 0)
        {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLClass> subClasses = reasoner.getSubClasses(classExpression, direct);
        return subClasses.getFlattened();
    }

     /** Gets the instances of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *            The string from which the class expression will be parsed.
     * @param direct
     *            Specifies whether direct instances should be returned or not.
     * @return The instances of the specified class expression
     * @throws ParserException
     *             If there was a problem parsing the class expression. */
    public Set<OWLNamedIndividual> getInstances(String classExpressionString,
            boolean direct) throws ParserException
    {
        if (classExpressionString.trim().length() == 0)
        {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(classExpression,
                false);
        return individuals.getFlattened();
    }
    
     /** Gets the Object Properties of a given individual
     * 
     * @param individual
     *            The individual we will get the properties from
    
     * @return Map<OWLObjectPropertyExpression,Set<OWLIndividual>>*/
    public  Map<OWLObjectPropertyExpression,Set<OWLIndividual>>  getIndividualObjectProperties(OWLNamedIndividual individual)
    {
         return  individual.getObjectPropertyValues(reasoner.getRootOntology());
    }
    /** Gets the Data Properties of a given individual
     * 
     * @param individual
     *            The individual we will get the properties from
    
     * @return Map<OWLObjectPropertyExpression,Set<OWLIndividual>>*/
    public  Map<OWLDataPropertyExpression, Set<OWLLiteral>> getIndividualDataProperties(OWLNamedIndividual individual)
    {
         return  individual.getDataPropertyValues(reasoner.getRootOntology());
    }
    
   /** Gets the Ranges of a given object property
     * 
     * @param objprop
     *            The property we will get the range from
    
     * @return Set<OWLClass> the classes in the range of this property */
    public Set<OWLClass> getObjPropertyRanges(OWLObjectPropertyExpression objprop)
    {
        
           return reasoner.getObjectPropertyRanges(objprop, true).getFlattened();
      
        //return Collections.emptySet();
        
    }
    /** Gets the individuals for a given object property that contain "value" in their name
     * 
     * @param property
     *            The property 
     * @param value
     *            The substring query that the returned individuals must contain
    
     * @return Set<OWLNamedIndividual> a set of named individuals that contain each the string value in their name*/
    
      public Set<OWLNamedIndividual> getIndividualsforObjectProperty(OWLObjectPropertyExpression property,String value)
    {
        Set<OWLNamedIndividual> a = new HashSet<OWLNamedIndividual>();
         Set<OWLClass> set = getObjPropertyRanges(property);
        try
        {
            Iterator<OWLClass> it = set.iterator();
            while(it.hasNext())
            {
                Set<OWLNamedIndividual> temp = getInstances(sfp.getShortForm(it.next()),false);

                Iterator<OWLNamedIndividual> ittemp = temp.iterator();
                while(ittemp.hasNext())
                {
                  OWLNamedIndividual i = ittemp.next();
                 
                   if (sfp.getShortForm(i).toLowerCase().contains(value.toLowerCase()))
                   {
                     a.add(i);
                   }
                }
            }
        }
        catch (ParserException ex)
        {
            System.out.println("Parser exception while parsing individuals for object property");
        }
        return a;
    }
      
      /** Gets the OwlLiterals for a given OWLDataPropertyExpression, already parsed, that contain "value" in their name
     * 
     * @param set 
     *            The given set of classes that have the data property 
     * @param value
     *            The substring query that the returned literals must contain
    
     * @return Set<OWLLiteral> a set of literals that contain each the string value*/
    public Set<OWLLiteral> getIndividualsforDataProperty(OWLDataPropertyExpression data,  Set<OWLClass> set, String value)
    {
            Set<OWLLiteral> a = new HashSet<OWLLiteral>();
            Iterator<OWLClass> it = set.iterator();
            while(it.hasNext())
            {
                OWLClass clss = it.next();
                Set<OWLIndividual> owlind = clss.getIndividuals(reasoner.getRootOntology());
                Iterator<OWLIndividual> it2= owlind.iterator();
                while(it2.hasNext())
                {
                    Set<OWLLiteral> lit= it2.next().getDataPropertyValues(data,reasoner.getRootOntology());
                    Iterator<OWLLiteral> it3 = lit.iterator();
                    while(it3.hasNext())
                    {
                        OWLLiteral literal = it3.next();
                        if(literal.getLiteral().toLowerCase().contains(value.toLowerCase()))
                        {
                            a.add(literal);
                        }
                        
                    }
                    
                }
            }
         
        return a;

    }      
    
    OWLDataPropertyExpression isDataProperty(String property)
    {
        
        try
        {
            OWLDataPropertyExpression data = parser.parseDataExpression(property);
            return data;
        }
        catch (ParserException ex)
        {
            return null;
        }
    }
    
    OWLObjectPropertyExpression isObjectProperty(String property)
    {
        try
        {
            OWLObjectPropertyExpression obj = parser.parseObjectExpression(property);
            return obj;
        }
        catch (ParserException ex)
        {
            return null;
        }
    }
    
    /** Gets the OwlLiterals for a given data property that contain "value" in their name, does the query for the subclasses of the domains too
     * 
     * @param property
     *            The property 
     * @param value
     *            The substring query that the returned literals must contain
     * calls getIndividualsforDataProperty1 if the domains have no subclasses.
     * @return Set<OWLLiteral> a set of literals that contain each the string value*/
    
    public Set<OWLLiteral> getIndividualsforDataPropertyAndSubclasses(OWLDataPropertyExpression data,String value)
    {
        Set<OWLLiteral> a = new HashSet<OWLLiteral>();
            
        try
        {
          //  OWLDataPropertyExpression data = parser.parseDataExpression(property);
            Set<OWLClass> set = reasoner.getDataPropertyDomains(data.asOWLDataProperty(), false).getFlattened();
            Iterator<OWLClass> it = set.iterator();
                while(it.hasNext())
                {
                   OWLClass clss = it.next();
                   Set<OWLClass> subclasses =  reasoner.getSubClasses(parser.parseClassExpression(sfp.getShortForm(clss)),true).getFlattened();
                    Iterator<OWLClass> it2 = subclasses.iterator();
                    while(it2.hasNext())
                    {
                        OWLClass noth = it2.next();
                        if (noth.isOWLNothing())
                        {
                            return getIndividualsforDataProperty(data,set,value);
                        }
                        Set<OWLIndividual> ind = noth.getIndividuals(reasoner.getRootOntology());
                        Iterator<OWLIndividual> it3 = ind.iterator();
                        while(it3.hasNext())
                        {
                            OWLIndividual indiv = it3.next();
                           
                            Set<OWLLiteral> lit =   indiv.getDataPropertyValues(data, reasoner.getRootOntology());
                            Iterator<OWLLiteral> it4 = lit.iterator();
                            while(it4.hasNext())
                            {
                                OWLLiteral literal = ((OWLLiteral)it4.next());
                              
                                if(literal.getLiteral().toLowerCase().contains(value.toLowerCase()))
                                {
                                    a.add(literal);    
                                } 
                            }
                        }
                    }
                            
                }

        }
        catch(ParserException ex)
        {
            System.out.println("NO DATA PROPERTY FOUND ERROR");
        }
        return a;
    }
    
    
}