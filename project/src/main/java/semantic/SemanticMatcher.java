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

    // TODO : We need to parse the SAWSDL files - and find the type of the semantic classes
    // TODO : for each operation and calculate their matching degrees

    /**
     * Calculates the matching degree for higher level types that can all be found in
     * the SUMO.owl ontology file
     * @param outputType
     * @param inputType
     * @return matching degree from 1 to 0
     */
    private double getMatchingDegree(String outputType, String inputType) {

        OWLClass outputClass = mapName_OWLClass.get(outputType.toLowerCase());
        OWLClass inputClass = mapName_OWLClass.get(inputType.toLowerCase());

        if (outputClass.equals(inputClass)){
            return 1; // Exact
        }
        else if (reasoner.isSubClassOf(inputClass, outputClass)) {
            return 0.8; // Subsumption
        }
        else if (reasoner.isSubClassOf(outputClass, inputClass)) {
            return 0.6; // Plug-in
        }
        else if (ontsum.findRelationship (outputClass, inputClass, reasoner).size() > 0) {
            return 0.5; // Structural
        }
        else {
            return 0;
        }
    }
}
