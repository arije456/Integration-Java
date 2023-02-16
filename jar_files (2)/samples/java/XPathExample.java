import net.sf.saxon.xpath.XPathEvaluator;
import net.sf.saxon.xpath.XPathExpression;
import net.sf.saxon.xpath.Variable;
import net.sf.saxon.xpath.StandaloneContext;
import net.sf.saxon.om.NodeInfo;

import org.xml.sax.InputSource;
import javax.xml.transform.sax.SAXSource;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.List;
import java.util.Iterator;

/**
  * Class XPathExample:
  * This class illustrates the use of Saxon's XPath API. It is a simple command-line application,
  * which prompts the user for a word, and replies with a list of all the lines containing that
  * word within a Shakespeare play.
  *
  *
  * @author Michael H. Kay (Michael.H.Kay@ntlworld.com)
  * @version 10 February 2003
  */

public class XPathExample {

    /**
      * main()<BR>
      * Expects one argument, the input filename<BR>
      */

    public static void main (String args[])
    throws java.lang.Exception
    {
        // Check the command-line arguments

        if (args.length != 1) {
            System.err.println("Usage: java XPathExample input-file");
            System.exit(1);
        }
        XPathExample app = new XPathExample();
        app.go(args[0]);
    }

    /**
    * Run the application
    */

    public void go(String filename) throws Exception {

        // Create an XPathEvaluator and set the source document

        InputSource is = new InputSource(new File(filename).toURL().toString());
        SAXSource ss = new SAXSource(is);
        XPathEvaluator xpe = new XPathEvaluator(ss);

        // Declare a variable for use in XPath expressions

        StandaloneContext sc = (StandaloneContext)xpe.getStaticContext();
        Variable wordVar = sc.declareVariable("word", "");

        // Compile the XPath expressions used by the application

        XPathExpression findLine =
            xpe.createExpression("//LINE[contains(., $word)]");
        XPathExpression findLocation =
            xpe.createExpression("concat(ancestor::ACT/TITLE, ' ', ancestor::SCENE/TITLE)");
        XPathExpression findSpeaker =
            xpe.createExpression("string(ancestor::SPEECH/SPEAKER[1])");

        // Create a reader for reading input from the console

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // Loop until the user enters "." to end the application

        while (true) {

            // Prompt for input
            System.out.println("\n>>>> Enter a word to search for, or '.' to quit:\n");

            // Read the input
            String word = in.readLine().trim();
            if (word.equals(".")) {
                break;
            }
            if (!word.equals("")) {

                // Set the value of the XPath variable
                wordVar.setValue(word);

                // Find the lines containing the requested word
                List matchedLines = findLine.evaluate();

                // Process these lines
                boolean found = false;
                for (Iterator iter = matchedLines.iterator(); iter.hasNext();) {

                    // Note that we have found at least one line
                    found = true;

                    // Get the next matching line
                    NodeInfo line = (NodeInfo)iter.next();

                    // Find where it appears in the play
                    findLocation.setContextNode(line);
                    System.out.println("\n" + findLocation.evaluateSingle());

                    // Find out who the speaker of this line is
                    findSpeaker.setContextNode(line);

                    // Output the name of the speaker and the content of the line
                    System.out.println(findSpeaker.evaluateSingle() + ":  " + line.getStringValue());
                }

                // If no lines were found, say so
                if (!found) {
                    System.err.println("No lines were found containing the word '" + word + "'");
                }
            }
        }

        // Finish when the user enters "."
        System.out.println("Finished.");
    }

}
