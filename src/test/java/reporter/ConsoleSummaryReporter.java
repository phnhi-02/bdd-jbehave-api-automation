package reporter;

import org.jbehave.core.reporters.NullStoryReporter;

public class ConsoleSummaryReporter extends NullStoryReporter {

    private int totalScenarios = 0;
    private int failedScenarios = 0;

    public void beforeScenario(String scenarioTitle) {
        totalScenarios++;
    }

    @Override
    public void failed(String step, Throwable cause) {
        failedScenarios++;
    }

    public void afterStories() {
        int passedScenarios = totalScenarios - failedScenarios;

        System.out.println("\n========== TEST SUMMARY ==========");
        System.out.println("Total Scenarios : " + totalScenarios);
        System.out.println("Passed          : " + passedScenarios);
        System.out.println("Failed          : " + failedScenarios);
        System.out.println("=================================\n");
    }
}
