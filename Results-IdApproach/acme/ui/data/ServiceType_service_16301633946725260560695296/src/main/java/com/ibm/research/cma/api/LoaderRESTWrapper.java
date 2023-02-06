package com.ibm.research.cma.api;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.acmeair.loader.Loader;
import com.acmeair.config.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Path("/loader")
@Controller
@RequestMapping("/ms1/loaderrest/")
public class LoaderRESTWrapper {

    // private static Logger logger = Logger.getLogger(LoaderREST.class.getName());
    /*
 * Disabling to test out the new acmeair code frist
 */
    @GET
    @Path("/query")
    @Produces("text/plain")
    @RequestMapping(method = RequestMethod.POST, value = "queryloader")
    public @ResponseBody Integer queryLoader(@RequestParam int callerId) {
        LoaderRESTServer callerObj = (LoaderRESTServer) idObjMap.get(callerId);
        Response result = callerObj.queryLoader();
        return result.id;
    }

    @GET
    @Path("/load")
    @Produces("text/plain")
    @RequestMapping(method = RequestMethod.POST, value = "loaddb")
    public @ResponseBody Integer loadDB(@RequestParam int callerId, @DefaultValue("-1") @QueryParam("numCustomers") @RequestParam long numCustomers) {
        LoaderRESTServer callerObj = (LoaderRESTServer) idObjMap.get(callerId);
        Response result = callerObj.loadDB(numCustomers);
        return result.id;
    }

    private static int maxId = 0;

    public static HashMap<Integer, LoaderRESTServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "loaderrest")
    public  @ResponseBody int LoaderREST() {
        LoaderRESTServer newServerObj = new LoaderRESTServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

