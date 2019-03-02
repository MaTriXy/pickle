package targetWithHooks;

import android.support.test.runner.AndroidJUnit4;
import java.lang.Throwable;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.OtherStepsWithHooks;
import steps.Steps;

@RunWith(AndroidJUnit4.class)
public class AFeatureWithBackgroundTest {
    private final Steps steps_Steps = new Steps();

    private final OtherStepsWithHooks steps_OtherStepsWithHooks = new OtherStepsWithHooks();

    @Test
    public void scenarioWithOneStepAndBackground() throws Throwable {
        steps_Steps.aStepWithoutParameters();
        steps_OtherStepsWithHooks.aStepFromAnotherDefinitionFile();
    }
}
