%====================================================================================
% iss25maritimecargo_sprint2_ioport description   
%====================================================================================
mqttBroker("localhost", "1883", "alarmevents").
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
event( distance, distance(D) ).
event( stop, stop(V) ).
event( resume, resume(V) ).
%====================================================================================
context(ctxioport, "localhost",  "TCP", "8007").
context(ctxcargoservice, "localhost",  "TCP", "8003").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
  qactor( containermanager, ctxioport, "it.unibo.containermanager.Containermanager").
 static(containermanager).
  qactor( alarmmanager, ctxioport, "it.unibo.alarmmanager.Alarmmanager").
 static(alarmmanager).
  qactor( alarmdevice, ctxioport, "it.unibo.alarmdevice.Alarmdevice").
 static(alarmdevice).
