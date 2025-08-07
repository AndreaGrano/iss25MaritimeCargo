%====================================================================================
% test_stopresume description   
%====================================================================================
mqttBroker("localhost", "1883", "alarmevents").
event( distance, distance(D) ).
event( stop, stop(V) ).
event( resume, resume(V) ).
%====================================================================================
context(ctxioport, "localhost",  "TCP", "8007").
context(ctxcargoservice, "127.0.0.1",  "TCP", "8003").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
  qactor( alarmdevice, ctxioport, "it.unibo.alarmdevice.Alarmdevice").
 static(alarmdevice).
