package semantic;

import ontology.MyOntManager;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class SemanticMatcher {

    private final static Logger LOG = LoggerFactory.getLogger(SemanticMatcher.class);

    private OWLOntologyManager manager = null;
    private OWLOntology ontology = null;
    private Reasoner reasoner = null;
    private String ontLocation = "file:data/SUMO.owl";
    private MyOntManager ontsum = null;
    private HashMap<String, OWLClass> mapName_OWLClass;

    public static void main(String[] args) {
        LOG.debug("Starting Semantic Matcher...");
        new SemanticMatcher();
    }

    public SemanticMatcher() {

        this.ontsum = new  MyOntManager();
        this.manager = ontsum.initializeOntologyManager();
        this.ontology = ontsum.initializeOntology(manager, ontLocation);
        this.reasoner = ontsum.initializeReasoner(ontology, manager);
        this.mapName_OWLClass = ontsum.loadClasses(reasoner);
    }


    private double getMatchingDegree(OWLClass output, OWLClass input) {

        if (output.equals(input)){ //TODO : Not sure how to find out if output "is same as" input
            return 1; // Exact
        }
        else if (reasoner.isSubClassOf(input, output)) {
            return 0.8; // Subsumption
        }
        else if (reasoner.isSubClassOf(output, input)) {
            return 0.6; // Plug-in
        }
        else if (false) { // TODO : Find out how to check if output "has relation with" input
            return 0.5; // Structural
        }
        else {
            return 0;
        }
    }
}
