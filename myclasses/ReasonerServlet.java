package myclasses;
//Authors of this document: Cyril El Hage - Marc Nasrallah
//École supérieure d'ingénieurs de Beyrouth - USJ - Lebanon
//You may redistribute it in the hope that it will be useful
//Thank you for mentioning our names.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


@WebServlet(name = "ReasonerServlet", urlPatterns = {"/ReasonerServlet"})
//@WebServlet("/ReasonerServlet")
public class ReasonerServlet extends HttpServlet
{
    private OntologyManager ont = new OntologyManager();
    public  String reqstring="0";
	/**
         * initializes the servlet
         * @throws ServletException 
         */
    @Override
	public void init() throws ServletException
        {
            super.init();
            URL url = getClass().getResource("Ontology1353580422992.owl");
            String a = url.toString();
            ont.loadOntologyfromPath(a.replaceAll("file:", ""));     
	}
    
    /**
     * Processes the request from the type of HTML form form2
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequestfromform2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
                response.setContentType("text/html;charset=UTF-8");
                DLQueryPrinter qp = new DLQueryPrinter(new DLQueryEngine(ont.reasoner), new SimpleShortFormProvider());//,"0");
                reqstring = request.getParameter("form2textfield");
                qp.out = response.getWriter();
                if(!reqstring.equals("0"))
                {
                  qp.askQuery(reqstring, ont.ontology, false);
                }
                qp.out.close();

    }
/**
 * Processes the request from the main HTML form
 * Creates the arrays to build a dl query in manchester syntax
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException 
 */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<String> ObjectProperties = new ArrayList<String>();
    	ArrayList<String> Values = new ArrayList<String>();
    	ArrayList<String> AndOr = new ArrayList<String>();
    	
    	String[] allValues = request.getParameterValues("Hiddentxt");
    	String[] valuesTrimmed = allValues[0].split("-");
    	for(int i=0;i<valuesTrimmed.length;i++){
    		if(valuesTrimmed[i].contains("n_")){
    			String[] nameTrimmed = valuesTrimmed[i].split(",");
    			String[] dropDown = request.getParameterValues(nameTrimmed[0]);
    			String[] dropDownTrimmed = dropDown[0].split("-");
    			String[] textField = request.getParameterValues(nameTrimmed[1]);
    			
    			for(int j=0;j<dropDownTrimmed.length;j++){
    				ObjectProperties.add(dropDownTrimmed[j]);
    				if(j<dropDownTrimmed.length-1){
    					AndOr.add("or");
        				AndOr.add("end or");
    				}
    				Values.add(textField[0]);
    			}
    			
    		}
    		
    		else{
    			AndOr.add(valuesTrimmed[i]);
    		}
    	}
        
        String ObjectPropertiesArray[] = (String[]) ObjectProperties.toArray(new String[ObjectProperties.size()]);
    	String ValuesArray[] = (String[])Values.toArray(new String[Values.size()]);
    	String AndOrArray[] = (String[])AndOr.toArray(new String[AndOr.size()]);
  
//        String fbuname;
//        try
//        {
//            fbuname=request.getParameter("fbusername");
//            if(fbuname.equals("0"))
//            {
//                fbuname="blankuname";
//            }
//        }
//        catch(NullPointerException e)
//        {
//            fbuname="blankuname";
//        }
        DLQueryPrinter qp = new DLQueryPrinter(new DLQueryEngine(ont.reasoner), new SimpleShortFormProvider());//,fbuname);
        qp.out = response.getWriter();
            long startTime = System.nanoTime();
            HTMLRequestConverter h = new HTMLRequestConverter(new DLQueryEngine(ont.reasoner));
       
            String q = h.Sender(ObjectPropertiesArray,AndOrArray,ValuesArray);
            qp.askQuery(q,ont.ontology,true);
            long endTime = System.nanoTime();
            System.out.println(endTime - startTime);
        qp.out.close();
    }
    
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            processRequest(request, response);
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("formname").equals(
            "form1"))
        {
 
            processRequest(request, response);
        }
        else
        {
            processRequestfromform2(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Reasoning Servlet";
    }
}
