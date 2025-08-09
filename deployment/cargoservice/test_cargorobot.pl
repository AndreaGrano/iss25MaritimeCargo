%====================================================================================
% test_cargorobot description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
dispatch( cmd, cmd(MOVE) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
dispatch( goon, goon(V) ).
dispatch( go, go(Slot) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8003").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( test, ctxcargoservice, "it.unibo.test.Test").
 static(test).
