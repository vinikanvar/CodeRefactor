package com.acmeair.web;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.WebApplicationException;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DateParam {

    public DateParam(String dateTime) throws WebApplicationException {
        String uri = MicroserviceApplication.projectUri + "/msnull/dateparam/dateparam?callerId=" + this.id + "&dateTime=dateTime";
        ResponseEntity<Integer> resultTemp = new RestTemplate().getForEntity(uri, int.class);
        this.id = resultTemp.getBody();
    }

    public Date getDate() {
        return (Date) null;
    }

    public int id = 0;

    public static DateParam getObject(int id) {
        DateParam obj = (DateParam) new Object();
        obj.id = id;
        return obj;
    }
}

