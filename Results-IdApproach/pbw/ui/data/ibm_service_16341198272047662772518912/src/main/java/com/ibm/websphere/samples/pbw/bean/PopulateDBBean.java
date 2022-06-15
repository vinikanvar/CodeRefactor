package com.ibm.websphere.samples.pbw.bean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Singleton
@Startup
public class PopulateDBBean {

    @PostConstruct
    public void initDB() {
        return;
    }

    public int id = 0;

    public static PopulateDBBean getObject(int id) {
        PopulateDBBean obj = (PopulateDBBean) new Object();
        obj.id = id;
        return obj;
    }
}

