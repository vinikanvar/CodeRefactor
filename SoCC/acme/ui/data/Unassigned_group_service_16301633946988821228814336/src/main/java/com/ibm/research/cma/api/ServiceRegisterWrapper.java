package com.ibm.research.cma.api;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.acmeair.util.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ms6/serviceregister/")
public class ServiceRegisterWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, ServiceRegisterServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "serviceregister")
    public  @ResponseBody int ServiceRegister() {
        ServiceRegisterServer newServerObj = new ServiceRegisterServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

