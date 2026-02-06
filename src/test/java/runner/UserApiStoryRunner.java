package runner;

import java.util.*;

import org.jbehave.core.configuration.*;
import org.jbehave.core.embedder.*;
import org.jbehave.core.io.*;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.*;
import org.jbehave.core.steps.*;
import steps.UserApiSteps;

public class UserApiStoryRunner extends JUnitStories {
    @Override
    public Configuration configuration() {
    	return new MostUsefulConfiguration()
    	        .useStoryLoader(new LoadFromClasspath(this.getClass()))
    	        .useStoryReporterBuilder(
    	                new StoryReporterBuilder()
    	                        .withDefaultFormats()
    	                        .withFormats(Format.CONSOLE, Format.TXT));

    }
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new UserApiSteps());
    }
    
//    @Override
//    public Embedder configuredEmbedder() {
//        Embedder embedder = super.configuredEmbedder();
////        embedder.useMetaFilters(Arrays.asList("+type delete"));
//        return embedder;
//    }
    
    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();

        String metaFilters = System.getProperty("jbehave.metaFilters");
        if (metaFilters != null) {
            embedder.useMetaFilters(Arrays.asList(metaFilters));
            System.out.println("Filter with = " + metaFilters);
        }

        return embedder;
    }

    @Override
    public List<String> storyPaths() {
        return Arrays.asList("user_api.story");
    }
}
