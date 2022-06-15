package com.ibm.research.cma.api;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.websphere.samples.pbw.bean.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Singleton
@Startup
@Controller
@RequestMapping("/ms2/populatedbbean/")
public class PopulateDBBeanWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, PopulateDBBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "populatedbbean")
    public  @ResponseBody int PopulateDBBean() {
        PopulateDBBeanServer newServerObj = new PopulateDBBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

