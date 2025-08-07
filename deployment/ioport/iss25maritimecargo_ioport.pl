%====================================================================================
% iss25maritimecargo_ioport description   
%====================================================================================
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
event( distance, distance(D) ).
event( stop, stop(V) ).
event( resume, resume(V) ).
dispatch( goToWait, goToWait(V) ).
%====================================================================================
context(ctxioport, "localhost",  "TCP", "8007").
context(ctxcargoservice, "192.168.0.6",  "TCP", "8003").
context(ctxcargoservicegui, "192.168.0.6",  "TCP", "8002").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
  qactor( containermanager, ctxioport, "it.unibo.containermanager.Containermanager").
 static(containermanager).
  qactor( alarmmanager, ctxioport, "it.unibo.alarmmanager.Alarmmanager").
 static(alarmmanager).
  qactor( alarmdevice, ctxioport, "it.unibo.alarmdevice.Alarmdevice").
 static(alarmdevice).
