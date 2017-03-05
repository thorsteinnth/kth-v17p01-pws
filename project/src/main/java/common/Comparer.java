package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Comparer
{
    private final static Logger LOG = LoggerFactory.getLogger(Comparer.class);
    private WSMatching wsMatching;

    public Comparer()
    {
        this.wsMatching = new WSMatching();
    }

    public WSMatching getWsMatching()
    {
        return wsMatching;
    }

    public abstract List<MatchedElement> compareElementContainers(
            List<MatchedElement> matchedElements,
            PartContainer outPutPartContainer,
            PartContainer inputPartContainer);

    /**
     * Compare outputs of operations of wsdl1 with inputs of operations of wsdl2
     * @param outputService
     * @param inputService
     */
    public void compare(ServiceContainer outputService, ServiceContainer inputService)
    {
        LOG.info("Comparing: " + outputService.name + " - " + inputService.name);

        Matching matching = null;
        List<MatchedOperation> matchedOperations = new ArrayList<>();

        for (PortContainer outputServicePortContainer : outputService.portContainers)
        {
            for (OperationContainer outputOC : outputServicePortContainer.portTypeContainer.operations)
            {
                // For each operation compare it to every operation for the input service,
                // and if there are matches add the Matched operation to the list of matched operations
                // in the Matched object for the two services

                matchedOperations = findMatchedOperations(outputOC, inputService.portContainers);

                if (matchedOperations.size() > 0)
                {
                    if (matching == null)
                    {
                        matching = new Matching();
                        matching.setOutputServiceName(outputService.name);
                        matching.setInputServiceName(inputService.name);
                    }

                    matching.setMatchedOperation(matchedOperations);
                    matching.setWsScore(calculateWsScore(matchedOperations));
                }
            }
        }

        if (matching != null)
        {
            List<Matching> matchingList = this.wsMatching.getMatching();
            matchingList.add(matching);
            this.wsMatching.setMatching(matchingList);
        }
    }

    private List<MatchedOperation> findMatchedOperations(
            OperationContainer outputOC,
            List<PortContainer> inputServicePortContainers)
    {
        List<MatchedOperation> matchedOperations = new ArrayList<>();
        List<MatchedElement> matchedElements = new ArrayList<>();

        for (PortContainer inputServicePortContainer : inputServicePortContainers)
        {
            for (OperationContainer inputOC : inputServicePortContainer.portTypeContainer.operations)
            {
                // Compare outputOC to every inputOC
                matchedElements = findMatchedElements(outputOC, inputOC);

                if (matchedElements.size() > 0)
                {
                    MatchedOperation matchedOperation = new MatchedOperation();
                    matchedOperation.setOutputOperationName(outputOC.name);
                    matchedOperation.setInputOperationName(inputOC.name);
                    matchedOperation.setMatchedElement(matchedElements);
                    matchedOperation.setOpScore(calculateOpScore(matchedElements));
                    matchedOperations.add(matchedOperation);
                }
            }
        }

        return matchedOperations;
    }

    private List<MatchedElement> findMatchedElements(OperationContainer outputOC, OperationContainer inputOC)
    {
        //LOG.debug("Finding matched elements for operations: " + outputOC.name + " - " + inputOC.name);

        List<MatchedElement> matchedElements = new ArrayList<>();

        for (PartContainer outputElements : outputOC.outputMessage.parts)
        {
            for (PartContainer inputElements : inputOC.inputMessage.parts)
            {
                matchedElements = compareElementContainers(matchedElements, outputElements, inputElements);
            }
        }

        return matchedElements;
    }

    private double calculateWsScore(List<MatchedOperation> matchedOperations)
    {
        double totalScore = 0;

        for (MatchedOperation mo : matchedOperations)
        {
            totalScore = totalScore + mo.getOpScore();
        }

        return totalScore / matchedOperations.size();
    }

    private double calculateOpScore(List<MatchedElement> matchedElements)
    {
        double totalScore = 0;

        for (MatchedElement me : matchedElements)
        {
            totalScore = totalScore + me.getScore();
        }

        return totalScore / matchedElements.size();
    }
}
