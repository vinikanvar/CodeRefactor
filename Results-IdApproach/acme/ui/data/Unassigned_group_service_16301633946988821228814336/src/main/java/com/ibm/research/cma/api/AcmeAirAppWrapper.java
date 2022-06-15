package com.ibm.research.cma.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import com.acmeair.web.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@ApplicationPath("/rest/api")
@Controller
@RequestMapping("/ms6/acmeairapp/")
public class AcmeAirAppWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, AcmeAirAppServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "acmeairapp")
    public  @ResponseBody int AcmeAirApp() {
        AcmeAirAppServer newServerObj = new AcmeAirAppServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

