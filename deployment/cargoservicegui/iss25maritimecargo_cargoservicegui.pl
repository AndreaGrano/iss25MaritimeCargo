%====================================================================================
% iss25maritimecargo_cargoservicegui description   
%====================================================================================
mqttBroker("localhost", "1883", "holdUpdated").
event( stop, stop(V) ).
event( resume, resume(V) ).
event( holdChanged, holdChanged(Slot,PID,Weight) ).
%====================================================================================
context(ctxcargoservicegui, "localhost",  "TCP", "8002").
 qactor( qakholdwsserver, ctxcargoservicegui, "it.unibo.qakholdwsserver.Qakholdwsserver").
 static(qakholdwsserver).
