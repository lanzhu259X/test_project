package com.lanzhu.testwork.lombok;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LombokModelService {

    private Map<Integer, LombokModel> modelMap;

    @PostConstruct
    public void  initService() {
        if (modelMap == null) {
            modelMap = new HashMap<Integer, LombokModel>();
        }
        int i = 0;
        while (i < 10) {
            i++;
            var model = new LombokModel(i);
            model.setName("Model-" + i);
            model.setAge(RandomUtils.nextInt(18, 55));
            modelMap.put(model.getId(), model);
        }

        System.out.println("model map size: " + modelMap.size());

        log.info("log model map size: {}", modelMap.size());
    }

    public LombokModel getModel(@NonNull Integer id) {
        log.info("log get model by id:{}", id);
        return modelMap.get(id);
    }

}
