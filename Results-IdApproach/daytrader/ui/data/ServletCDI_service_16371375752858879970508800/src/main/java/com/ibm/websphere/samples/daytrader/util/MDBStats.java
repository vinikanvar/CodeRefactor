/**
 * (C) Copyright IBM Corporation 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.daytrader.util;

import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MDBStats extends java.util.HashMap<String, TimerStat> {

    private MDBStats() {
        String uri = MicroserviceApplication.projectUri + "/ms7/mdbstats/mdbstatscallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public static synchronized MDBStats getInstance() {
        String uri = MicroserviceApplication.projectUri + "/ms7/mdbstats/getinstance";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        MDBStats result = MDBStats.getObject(resultTemp);
        return result;
    }

    public TimerStat addTiming(String type, long sendTime, long recvTime) {
        String uri = MicroserviceApplication.projectUri + "/ms7/mdbstats/addtiming?callerId=" + this.id + "&type=type&sendTime=sendTime&recvTime=recvTime";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        TimerStat result = TimerStat.getObject(resultTemp);
        return result;
    }

    public synchronized void reset() {
        String uri = MicroserviceApplication.projectUri + "/ms7/mdbstats/resetcallerId=" + this.id + "";
        new RestTemplate().getForObject(uri, void.class);
    }

    public int id = 0;

    public static MDBStats getObject(int id) {
        MDBStats obj = (MDBStats) new Object();
        obj.id = id;
        return obj;
    }
}

