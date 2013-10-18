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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.expression.ParserException;

//import myclasses.ReasonerServlet.DLQueryEngine;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.ShortFormProvider;

class DLQueryPrinter {
    private DLQueryEngine dlQueryEngine;
    private ShortFormProvider shortFormProvider;
//   private SocialConfig sconfig;
    public  PrintWriter out;
    private ArrayList<String> facebookarray;
 /** Constructs a DLQueryPrinter. This will print the results of the "DL queries"
     
     * @param engine
     *            the engine that will get the results for the dl queries
     * @param shortFormProvider
     *            A short form provider.
     * @param uname
     *            the Facebook username of the logged in user
     */
     
    public DLQueryPrinter(DLQueryEngine engine, ShortFormProvider shortFormProvider)//,String uname)
    {
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = engine;
        
//       sconfig = new SocialConfig(uname);
        facebookarray = new ArrayList<String>();

    }
    
     /**  
      * @params ontology
      *         the ontology
      * @params classExpression
      *         the class expression to use for intrerrogation
      * @params b
      *         a boolean to know if we want to print a single individual's details or 
      * if we want to print all results on the page without details
      */

    public void askQuery(String classExpression, OWLOntology ontology,boolean b)
    {
        if (classExpression.length() == 0)
        {
            System.out.println("No class expression specified");
        } else
        {
            try
            {
                printHTMLHeader();
                Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstances(
                        classExpression, true);
                if(individuals.isEmpty())
                {
                    out.println("<div class=\"noresult\">");
                    out.println("No result was found, here are some suggestions:<br>");
                    out.println("  - Make sure all words are spelled correctly.<br>");
                    out.println("  - Try different keywords.<br>");
                    out.println("  - Try more general keywords.<br>");
                    
                   
                    
                    out.println("</div>");
                }
               else
                {
                    
  
               if(!b)
                {
 
                 Iterator<OWLNamedIndividual> it = individuals.iterator();
                  while(it.hasNext())
                {
                      OWLNamedIndividual i =  it.next();  
                      printDataProperties(dlQueryEngine.getIndividualDataProperties(i),i);
                      
                }
                it = individuals.iterator();
                 while(it.hasNext())
                {
                   OWLNamedIndividual i =  it.next();  
                   printObjectproperties(dlQueryEngine.getIndividualObjectProperties(i));
                   
                }
               
                }
                else
                {
           
              Iterator<OWLNamedIndividual> it = individuals.iterator();
              out.println("<div class=\"wrap\">");
              out.println("<ul class = \"cols\">");
                  while(it.hasNext())
                {
                    OWLNamedIndividual i =  it.next();
                    out.println("<li>");
                    out.println("<h2>");  
                    String str = shortFormProvider.getShortForm(i).replaceAll("_"," ");
                    out.println("<a href=\"javascript:;\" onclick=\"button1(this)\" \">"+
                            str+"</a>");
                    out.println("</h2>");
                    printInstanceWithDataTypes(dlQueryEngine.getIndividualDataProperties(i),str);
                    out.println("</li>");

                }
                  
               Iterator<String> fbit = facebookarray.iterator(); 
                while(fbit.hasNext())
                  {
                      
                    out.println("<li>");
                    out.println("<h2>");  
                    out.println(fbit.next());           
                    out.println("</h2>");
                    out.println("<a href=\"http://www.facebook.com/"+fbit.next()+"\"> Link on Facebook </a>");
                    out.println("</li>");
                  }
                  
             out.println("</ul>");
             out.println("</div>");
                
                }
           printHTMLFooter();
         
            }
            }
            catch (ParserException e)
            {
               System.out.println(e.getMessage());
            }
        }
    }
    /** prints Instances with their given datatypes
     * 
     * @param entities 
     *          the map containing each datapropertyexpression with its results or literals
     * 
     */

    private void printInstanceWithDataTypes(Map<OWLDataPropertyExpression, Set<OWLLiteral>> entities,String shortstr)
    {
         Set<Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>> entitiesSet =  entities.entrySet();
         Iterator it = entitiesSet.iterator();
         int count = 0;
         boolean b = false;
    
             for(int i =0;i<entities.size();i++)
             {
             Map.Entry<OWLDataPropertyExpression,Set<OWLLiteral>> s = (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>)(it.next());
             OWLDataPropertyExpression obj = s.getKey();
             Set<OWLLiteral> owlind = s.getValue();
  
                if(count==3)
                {
                    b=true;
                    out.println("<ul class = \"menu\">");
                    out.println("<li> View More >>");
                    out.println("<ul>");
                    String str = shortFormProvider.getShortForm(obj.asOWLDataProperty());
                    out.println("<li>"+str.substring(0,str.length()-2).replaceAll("_", " ") +": ");
                    Iterator it2 = owlind.iterator();
                     while(it2.hasNext())
                   {
                      String ind = ((OWLLiteral)it2.next()).getLiteral();
                      out.println(" "+ind);
//                      if(shortFormProvider.getShortForm(obj.asOWLDataProperty()).toLowerCase().contains("first_name"))
//                      {
//                          ParseFromFacebook(shortstr);
//                        
//                      }
                   
                   }
                     out.println("</li>");
                   
                }
                else if (count>3)
                {
                    
                    String str = shortFormProvider.getShortForm(obj.asOWLDataProperty());
                    out.println("<li>"+str.substring(0,str.length()-2).replaceAll("_", " ") +": ");                    Iterator it2 = owlind.iterator();
                     while(it2.hasNext())
                   {
                      String ind = ((OWLLiteral)it2.next()).getLiteral();
                      out.println(" "+ind);
//                      if(shortFormProvider.getShortForm(obj.asOWLDataProperty()).toLowerCase().contains("first_name"))
//                      {
//                          ParseFromFacebook(shortstr);
//                        
//                      }
                      
                   }
                     out.println("</li>");
                }
                else
                {
                       String str = shortFormProvider.getShortForm(obj.asOWLDataProperty());
                    out.println("<p>"+str.substring(0,str.length()-2).replaceAll("_", " ") +": ");
                //out.println("<p>"+shortFormProvider.getShortForm(obj.asOWLDataProperty())+": ");
                Iterator it2 = owlind.iterator();
                 while(it2.hasNext())
                   {
                      String ind = ((OWLLiteral)it2.next()).getLiteral();
                      out.println(" "+ind);
//                      if(shortFormProvider.getShortForm(obj.asOWLDataProperty()).toLowerCase().contains("first_name"))
//                      {
//                         ParseFromFacebook(shortstr);
//                        
//                      }
                   }
                 out.println("</p>");
                }
                count++;
         
        }
                 if(b)
             {
              out.println("</ul>");
              out.println("</li>");
              out.println("</ul>");
            }  
       

    }
    /**
     * 
     * 
     * @param name 
     *        the value that we will compare to the logged in user's friendlist on facebook.
     * 
     */

//    private void ParseFromFacebook(String name)
//    {
//         ArrayList<String> parsed = sconfig.parseFriendsNames(name);
//         if(parsed!=null)
//         {
//            facebookarray.addAll(parsed);     
//         }
//
//    }
    
        /** prints object properties with their given results
     * 
     * @param entities 
     *          the map containing each objectpropertyexpression with its results or individuals
     * 
     */
 
      private void printObjectproperties(Map<OWLObjectPropertyExpression,Set<OWLIndividual>> entities
       )
        {

              out.println("<div class=\"wrap\">");
              out.println("<ul class = \"cols\">");
             Set<Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>>> entitiesSet =  entities.entrySet();
             Iterator it = entitiesSet.iterator();
             for(int i =0;i<entities.size();i++)
             {
              out.println("<li>");
              out.println("<h2>");
              Map.Entry<OWLObjectPropertyExpression,Set<OWLIndividual>> s = (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>>)(it.next());
             OWLObjectPropertyExpression obj = s.getKey();
             Set<OWLIndividual> owlind = s.getValue();
             
             String str = shortFormProvider.getShortForm(obj.asOWLObjectProperty());
             out.println(str.substring(0,str.length()-2).replaceAll("_", " "));
             
             out.println("</h2>");
             Iterator it2 = owlind.iterator();
             int count = 0;
             boolean b = false;
             while(it2.hasNext())
             {
                OWLNamedIndividual ind = ((OWLIndividual)it2.next()).asOWLNamedIndividual();
             //   sb.append(shortFormProvider.getShortForm(ind));
                if(count==3)
                {
                    b=true;
                    out.println("<ul class = \"menu\">");
                    out.println("<li> View More >>");
                    out.println("<ul>");
                    out.println("<li>"+"<a href=\"javascript:;\" onclick=\"button1(this)\" \">"+shortFormProvider.getShortForm(ind)+"</a>"+ "</li>");
                }
                else if(count>3)
                {
                    out.println("<li>"+"<a href=\"javascript:;\" onclick=\"button1(this)\" \">"+shortFormProvider.getShortForm(ind)+"</a>"+"</li>");
                }
               else
               {
                out.println("<p>"+"<a href=\"javascript:;\" onclick=\"button1(this)\" \">"+shortFormProvider.getShortForm(ind)+"</a>"+"</p>");
               }
               count++;
             }
             if(b)
             {
              out.println("</ul>");
              out.println("</li>");
              out.println("</ul>");
             }  
             out.println("</li>");
             
             }
             out.println("</ul>");
             out.println("</div>");

        }
      /**
       * prints data properties
       * 
       * @param entities 
       * 
       */
         private void printDataProperties(Map<OWLDataPropertyExpression, Set<OWLLiteral>> entities,OWLNamedIndividual indiv
            )
         {
            out.println("<div id=\"intro\">");
            out.println("<div class=\"wrap clearfix\">");
            out.println("<div class=\"floatRight\">");
            out.println("<h2>Info</h2>");
                   
             Set<Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>> entitiesSet =  entities.entrySet();
             Iterator it = entitiesSet.iterator();
             for(int i =0;i<entities.size();i++)
             {
                 
             Map.Entry<OWLDataPropertyExpression,Set<OWLLiteral>> s = (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>)(it.next());
             OWLDataPropertyExpression obj = s.getKey();
             Set<OWLLiteral> owlind = s.getValue();
             
             String str = shortFormProvider.getShortForm(obj.asOWLDataProperty());
             out.println("<p>"+str.substring(0,str.length()-2).replaceAll("_", " ") +": ");
             
             Iterator it2 = owlind.iterator();
             while(it2.hasNext())
             {
               String ind = ((OWLLiteral)it2.next()).getLiteral();
               out.println(" "+ind);
             }
             out.println("</p>");
             }
         out.println("</div>");
         out.println("<div class=\"photo\">");
         out.println("<img src=\"img/"+shortFormProvider.getShortForm(indiv)+".jpg\" width=165 height=170 alt=\"No picture available\" />");
         out.println("</div>");
         out.println("</div>");
         out.println("</div>");
             
        
         }
         /**
          * prints the HTML Header of the result page
          */
         public void printHTMLHeader()
         {
         out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
         out.println("<FORM NAME=\"form2\" METHOD=\"POST\" action=\"ReasonerServlet\">");
         out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
         out.println("function button1(sender)");
         out.println("{");
         out.println("var x = document.getElementById('tf');");
         out.println("var y = sender.innerHTML;");
         out.println("var z = y.split(\" \").join(\"_\");");
         out.println("x.value= \"{\"+z+\"}\";");
         out.println("form2.submit()");
         out.println("}");
         out.println("</SCRIPT>");
         out.println("<hr class=\"noscreen\" />");
         out.println("<div id=\"header\">");
         out.println("<div class=\"wrap\">");
         out.println("<a id=\"logo\" href=\"./\" title=\"Atomium\">Atom<strong>ium</strong></a>");
         out.println("</div>");
         out.println("</div>");
         out.println("<input type=\"hidden\" name = \"formname\" id=\"formname\" value=\"form2\"/>");
         out.println("<input type=\"text\" id =\"tf\" hidden=\"true\" name=\"form2textfield\" />");
      
         
         out.println("</FORM>");
        
        
        
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"cs\" lang=\"cs\">");
        out.println("<head>");
        out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
        out.println("<meta http-equiv=\"content-language\" content=\"en\" />");
        out.println("<meta name=\"author\" lang=\"en\" content=\"[www.url.com]; e-mail: info@url.com\" />");
        out.println("<meta name=\"copyright\" lang=\"en\" content=\"Webdesign: Tempixo.com [www.tempixo.com]; e-mail: hello@tempixo.com\" />");
        out.println("<meta name=\"description\" content=\"...\" />");
        out.println("<meta name=\"keywords\" content=\"...\" />");
        out.println("<meta name=\"robots\" content=\"all,follow\" />");
        
        out.println("<link href=\"css/screen.css\" type=\"text/css\" rel=\"stylesheet\" media=\"screen,projection\" />");
        out.println("<link href=\"css/print.css\" type=\"text/css\" rel=\"stylesheet\" media=\"print\" />");
        out.println("<link href=\"css/stylishstyle.css\" type=\"text/css\" rel=\"stylesheet\"media=\"screen,projection\"/>");
        
        out.println("<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"js/cufon.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"js/cufon-config.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"js/font.js\" type=\"text/javascript\"></script>");
        
        out.println("<title>Atomium</title>");
        out.println("</head>");
        out.println("<body class=\"otherbody\">");
         }
         /**
          * prints the html footer of the result page
          */
         public void printHTMLFooter()
         {
        
         out.println("<hr class=\"noscreen\" />");
         out.println("<p class=\"fpfooter\">");
         out.println("<span class=\"floatLeft\">&copy; 2012-2013 - <a href=\"/\">Marc Nasrallah - Cyril El Hage</a></span>");
         out.println("<span id=\"dont-delete-this\" class=\"floatRight noprint\">Our tip: <a href=\"http://www.grily-grilovanie.sk/grily-weber/\"title=\"Grily Weber\">Grily Weber</a> | Created by <a href=\"http://www.tempixo.com\" class=\"tempixo\">Tempixo.com<span></span></a></span>");
         out.println("</p>");
         out.println("</body>");
         out.println("</html>");

         }

    
}

