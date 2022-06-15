package com.ibm.research.cma.api;

import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;
import com.acmeair.mongo.MongoConstants;
import com.acmeair.service.BookingService;
import com.acmeair.service.FlightService;
import com.acmeair.service.KeyGenerator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.async.client.*;
import com.acmeair.mongo.ConnectionManager;
import com.acmeair.mongo.services.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@ApplicationScoped
@Controller
@RequestMapping("/ms2/bookingserviceimpl/")
public class BookingServiceImplWrapper {

    // private final static Logger logger = Logger.getLogger(BookingService.class.getName());
    private static int maxId = 0;

    public static HashMap<Integer, BookingServiceImplServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "bookingserviceimpl")
    public  @ResponseBody int BookingServiceImpl() {
        BookingServiceImplServer newServerObj = new BookingServiceImplServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
    // private final static Logger logger = Logger.getLogger(BookingService.class.getName());
}

