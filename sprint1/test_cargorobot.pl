%====================================================================================
% test_cargorobot description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
request( doplan, doplan(Path,StepTime) ).
reply( doplandone, doplandone(V) ).  %%for doplan
reply( doplanfailed, doplanfailed(V) ).  %%for doplan
dispatch( go, go(V) ).
%====================================================================================
context(ctxcargorobot, "localhost",  "TCP", "8003").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( cargorobot, ctxcargorobot, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( test, ctxcargorobot, "it.unibo.test.Test").
 static(test).
