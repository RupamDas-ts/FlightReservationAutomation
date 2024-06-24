package Listeners;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ConcurrencyListeners implements IAlterSuiteListener {

  @Override
  public void alter(List<XmlSuite> suites) {
    String parallelCount = System.getProperty("PARALLEL");
    if (parallelCount != null) {
      try {
        int count = Integer.parseInt(parallelCount);
        XmlSuite suite = suites.getFirst(); // Assuming there's only one suite
        suite.setDataProviderThreadCount(count);
      } catch (NumberFormatException e) {
        System.err.println("Invalid format for parallelCount: " + parallelCount);
      }
    }
  }
}