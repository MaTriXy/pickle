package targetWithHooks;

import android.support.test.runner.AndroidJUnit4;
import java.lang.Throwable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.OtherStepsWithHooks;
import steps.Steps;

@RunWith(AndroidJUnit4.class)
public class AFeatureWithoutBackgroundTest {
    private final Steps steps_Steps = new Steps();

    private final OtherStepsWithHooks steps_OtherStepsWithHooks = new OtherStepsWithHooks();

    @Before
    public void setUp() throws Throwable {
        steps_OtherStepsWithHooks.beforeHook();
    }

    @After
    public void tearDown() throws Throwable {
        steps_OtherStepsWithHooks.afterHook();
    }

    @Test
    public void scenarioWithOneStepAlsoNonAlphanumericChars_1() throws Throwable {
        steps_Steps.aStepWithAsParameter("1");
    }

    @Test
    public void scenarioWithTwoSteps() throws Throwable {
        steps_Steps.aStepWithAsParameter("2");
        steps_Steps.aStepWithoutParameters();
    }

    @Test
    public void scenarioWithStepsFrom2DefinitionFiles() throws Throwable {
        steps_Steps.aStepWithoutParameters();
        steps_OtherStepsWithHooks.aStepFromAnotherDefinitionFile();
    }

    @Test
    public void scenarioWithExamples0() throws Throwable {
        steps_Steps.aStepWithAsParameter("1");
    }

    @Test
    public void scenarioWithExamples1() throws Throwable {
        steps_Steps.aStepWithAsParameter("a");
    }
}
