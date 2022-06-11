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
package com.ibm.research.cma.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ibm.websphere.samples.daytrader.util.*;
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
@RequestMapping("/ms7/log/")
public class LogWrapper {

    // A general purpose, high performance logging, tracing, statistic service
    @RequestMapping(method = RequestMethod.POST, value = "log_log_string")
    public @ResponseBody ResponseEntity<Void> log(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.log(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "log_log_string_string")
    public @ResponseBody ResponseEntity<Void> log(@RequestParam int callerId, @RequestParam String msg1, @RequestParam String msg2) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.log(msg1, msg2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "log_log_string_string_string")
    public @ResponseBody ResponseEntity<Void> log(@RequestParam int callerId, @RequestParam String msg1, @RequestParam String msg2, @RequestParam String msg3) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.log(msg1, msg2, msg3);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_string")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_string_throwable")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam String message, @RequestParam int eId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(message, Throwable.getObject(eId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_string_string_throwable")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam String msg1, @RequestParam String msg2, @RequestParam int eId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(msg1, msg2, Throwable.getObject(eId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_string_string_string_throwable")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam String msg1, @RequestParam String msg2, @RequestParam String msg3, @RequestParam int eId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(msg1, msg2, msg3, Throwable.getObject(eId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_throwable_string")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam int eId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(Throwable.getObject(eId), message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_throwable_string_string")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam int eId, @RequestParam String msg1, @RequestParam String msg2) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(Throwable.getObject(eId), msg1, msg2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "error_throwable_string_string_string")
    public @ResponseBody ResponseEntity<Void> error(@RequestParam int callerId, @RequestParam int eId, @RequestParam String msg1, @RequestParam String msg2, @RequestParam String msg3) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.error(Throwable.getObject(eId), msg1, msg2, msg3);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id, @RequestParam int parm3Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id), Object.getObject(parm3Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id, @RequestParam int parm3Id, @RequestParam int parm4Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id), Object.getObject(parm3Id), Object.getObject(parm4Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object_object_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id, @RequestParam int parm3Id, @RequestParam int parm4Id, @RequestParam int parm5Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id), Object.getObject(parm3Id), Object.getObject(parm4Id), Object.getObject(parm5Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object_object_object_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id, @RequestParam int parm3Id, @RequestParam int parm4Id, @RequestParam int parm5Id, @RequestParam int parm6Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id), Object.getObject(parm3Id), Object.getObject(parm4Id), Object.getObject(parm5Id), Object.getObject(parm6Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "trace_string_object_object_object_object_object_object_object")
    public @ResponseBody ResponseEntity<Void> trace(@RequestParam int callerId, @RequestParam String message, @RequestParam int parm1Id, @RequestParam int parm2Id, @RequestParam int parm3Id, @RequestParam int parm4Id, @RequestParam int parm5Id, @RequestParam int parm6Id, @RequestParam int parm7Id) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.trace(message, Object.getObject(parm1Id), Object.getObject(parm2Id), Object.getObject(parm3Id), Object.getObject(parm4Id), Object.getObject(parm5Id), Object.getObject(parm6Id), Object.getObject(parm7Id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "traceenter")
    public @ResponseBody ResponseEntity<Void> traceEnter(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.traceEnter(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "traceexit")
    public @ResponseBody ResponseEntity<Void> traceExit(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.traceExit(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "debug")
    public @ResponseBody ResponseEntity<Void> debug(@RequestParam int callerId, @RequestParam String message) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.debug(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "printcollection_collection<?>")
    public @ResponseBody ResponseEntity<Void> printCollection(@RequestParam int callerId, @RequestParam int cId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.printCollection((Collection<?>) new Object());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "printcollection_string_collection<?>")
    public @ResponseBody ResponseEntity<Void> printCollection(@RequestParam int callerId, @RequestParam String message, @RequestParam int cId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.printCollection(message, (Collection<?>) new Object());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "doactiontrace")
    public  @ResponseBody boolean doActionTrace(@RequestParam int callerId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        boolean result = callerObj.doActionTrace();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "dotrace")
    public  @ResponseBody boolean doTrace(@RequestParam int callerId) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        boolean result = callerObj.doTrace();
        return result;
    }

    /**
     * Sets the trace
     *
     * @param trace
     *            The trace to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "log_settrace")
    public @ResponseBody ResponseEntity<Void> setTrace(@RequestParam int callerId, @RequestParam boolean traceValue) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.setTrace(traceValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sets the trace value for Trade actions only
     *
     * @param trace
     *            The trace to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "log_setactiontrace")
    public @ResponseBody ResponseEntity<Void> setActionTrace(@RequestParam int callerId, @RequestParam boolean traceValue) {
        LogServer callerObj = (LogServer) idObjMap.get(callerId);
        callerObj.setActionTrace(traceValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, LogServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "log")
    public  @ResponseBody int Log() {
        LogServer newServerObj = new LogServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

