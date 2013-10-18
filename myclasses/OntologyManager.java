package myclasses;

//Authors of this document: Cyril El Hage - Marc Nasrallah
//École supérieure d'ingénieurs de Beyrouth - USJ - Lebanon
//You may redistribute it in the hope that it will be useful
//Thank you for mentioning our names.
import java.io.File;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public class OntologyManager
{
	public  OWLReasoner reasoner;
        public  OWLOntology ontology;
        
        /**
         * creates the Hermit Reasoner
         * @param rootOntology
         *          the ontology
         * @return 
         */
        private  OWLReasoner createReasoner(OWLOntology rootOntology)
        {
            OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
            return reasonerFactory.createReasoner(rootOntology);
        }
       
         /**
          * Loads the ontology from local path
          * Precomputes Inferences so that queries will be answered faster
          * 
          * @param path 
          *         the local path
          */
	 public  void loadOntologyfromPath(String path)
         {
	        try
                {
	        	
	            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	            ontology = manager.loadOntologyFromOntologyDocument(new File(path));
                    System.out.println("Loaded ontology: " + ontology.getOntologyID());
	            reasoner = createReasoner(ontology);
                    reasoner.precomputeInferences();
	        } 
                catch (OWLOntologyCreationException e)
                {
	            System.out.println("Could not load ontology: " + e.getMessage());
	        }
	}
  
	
}
