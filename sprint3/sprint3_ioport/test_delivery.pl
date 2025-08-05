%====================================================================================
% test_delivery description   
%====================================================================================
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
event( distance, distance(D) ).
%====================================================================================
context(ctxioport, "localhost",  "TCP", "8007").
context(ctxcargoservice, "127.0.0.1",  "TCP", "8003").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
  qactor( containermanager, ctxioport, "it.unibo.containermanager.Containermanager").
 static(containermanager).
