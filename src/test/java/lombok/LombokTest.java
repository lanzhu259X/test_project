package lombok;

import com.lanzhu.testwork.WorkApplication;
import com.lanzhu.testwork.lombok.LombokModel;
import com.lanzhu.testwork.lombok.LombokModelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WorkApplication.class)
public class LombokTest {

    @Autowired
    private LombokModelService modelService;

    @Test
    public void testModelSet() {
        LombokModel model = new LombokModel(1);
        model.setAge(18);
        model.setName("Model-1");
        model.setParent(new LombokModel(2));
        model.setDesc("Model-1 desc test");

        log.debug(model.getDesc());
        log.info(model.toString());
    }

    @Test
    public void testLombokService() {
        LombokModel model1 = modelService.getModel(1);


        log.info("model 1: {}", model1.toString());
        LombokModel model2 = modelService.getModel(20);

        log.info("model 2: {}", model2 == null ? "model 20 is null" : model2.toString());

        try {
            LombokModel model3 = modelService.getModel(null);
            log.info("model 3: {}", model3 == null ? "model 20 is null" : model2.toString());
        }catch (Exception e) {
            log.error("get an exception: {}", e.toString() ,e);
        }
    }



}
