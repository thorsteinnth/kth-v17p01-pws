package syntactic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

public class SyntacticMatcher
{
    private final static Logger LOG = LoggerFactory.getLogger(SyntacticMatcher.class);

    public static void main(String[] args)
    {
        LOG.debug("Starting ...");
        new SyntacticMatcher();
    }

    public SyntacticMatcher()
    {
        File[] WSDLs = getWSDLs();

        for (int i=0; i<WSDLs.length; i++)
        {
            for (int y=0; y<WSDLs.length; y++)
            {
                if (i == y)
                {
                    // Don't want to compare a file to itself
                    continue;
                }

                LOG.debug("Should compare WSDLs: " + WSDLs[i].toString() + " - " + WSDLs[y].toString());
            }
        }
    }

    private File[] getWSDLs()
    {
        try
        {
            File[] WSDLs = (new File(getClass().getResource("/WSDLs").toURI())).listFiles();
            return WSDLs;
        }
        catch (URISyntaxException ex)
        {
            LOG.error(ex.toString());
            return new File[0];
        }
    }
}
