package runner;

import java.util.Arrays;
import java.util.List;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import io.qameta.allure.jbehave5.AllureJbehave5;
import steps.UserApiSteps;
import steps.UserLoginSteps;

public class UserApiStoryRunner extends JUnitStories {

	@Override
	public Configuration configuration() {
	    return new MostUsefulConfiguration()
	            .useStoryLoader(new LoadFromClasspath(this.getClass()))
	            .useStoryReporterBuilder(
	                    new StoryReporterBuilder()
	                            .withDefaultFormats()
	                            .withFormats(Format.CONSOLE) 
	                            .withReporters(new AllureJbehave5())
	            );
	}

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new UserApiSteps(), new UserLoginSteps());
    }

    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.embedderControls()
		        .doIgnoreFailureInStories(false)
		        .doIgnoreFailureInView(false)
		        .doGenerateViewAfterStories(true)
		        .doFailOnStoryTimeout(true);
//                .useThreads(1);

        String metaFilters = System.getProperty("jbehave.metaFilters");
        if (metaFilters != null) {
            embedder.useMetaFilters(Arrays.asList(metaFilters));
            System.out.println("Filter with = " + metaFilters);
        }

        return embedder;
    }

    @Override
    public List<String> storyPaths() {
        return Arrays.asList("user_api.story", "user_login.story");
    }
}
