%====================================================================================
% iss25maritimecargo_cargoservice description   
%====================================================================================
mqttBroker("localhost", "1883", "holdUpdated").
request( getProductWeight, getProductWeight(PID) ).
reply( productWeight, productWeight(Weight) ).  %%for getProductWeight
request( getFreeSlot, getFreeSlot(V) ).
reply( freeSlot, freeSlot(FreeSlot) ).  %%for getFreeSlot
request( getLoadedWeight, getLoadedWeight(V) ).
reply( loadedWeight, loadedWeight(Weight) ).  %%for getLoadedWeight
dispatch( updateHold, updateHold(Slot,PID) ).
request( loadProduct, loadProduct(PID) ).
reply( loadAccepted, loadAccepted(SLOT,PID,WEIGHT) ).  %%for loadProduct
reply( loadRejected, loadRejected(Reason) ).  %%for loadProduct
dispatch( goToWait, goToWait(V) ).
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
dispatch( cmd, cmd(MOVE) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
dispatch( goon, goon(V) ).
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
request( doDelivery, doDelivery(Slot) ).
reply( deliveryDone, deliveryDone(V) ).  %%for doDelivery
event( stop, stop(V) ).
event( resume, resume(V) ).
request( getHoldStatus, getHoldStatus(V) ).
reply( holdStatus, holdStatus(P1,W1,P2,W2,P3,W3,P4,W4) ).  %%for getHoldStatus
event( holdChanged, holdChanged(Slot,PID,Weight) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8003").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( holdmanager, ctxcargoservice, "it.unibo.holdmanager.Holdmanager").
 static(holdmanager).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
