%====================================================================================
% iss25maritimecargo_sprint2_cargoservice description   
%====================================================================================
mqttBroker("localhost", "1883", "alarmevents").
request( getProductWeight, getProductWeight(PID) ).
reply( productWeight, productWeight(Weight) ).  %%for getProductWeight
request( getFreeSlot, getFreeSlot(V) ).
reply( freeSlot, freeSlot(FreeSlot) ).  %%for getFreeSlot
request( getLoadedWeight, getLoadedWeight(V) ).
reply( loadedWeight, loadedWeight(V) ).  %%for getLoadedWeight
dispatch( updateHold, updateHold(Slot,PID) ).
request( getAllSlots, getAllSlots(V) ).
reply( slotsList, slotsList(Slotlist) ).  %%for getAllSlots
request( getAllProducts, getAllProducts(V) ).
reply( allProducts, allProducts(ProductsList) ).  %%for getAllProducts
request( loadProduct, loadProduct(PID) ).
reply( loadAccepted, loadAccepted(V) ).  %%for loadProduct
reply( loadRejected, loadRejected(Reason) ).  %%for loadProduct
dispatch( goToWait, goToWait(V) ).
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
request( doplan, doplan(Path,StepTime) ).
reply( doplandone, doplandone(V) ).  %%for doplan
reply( doplanfailed, doplanfailed(V) ).  %%for doplan
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
request( doDelivery, doDelivery(Slot) ).
reply( deliveryDone, deliveryDone(V) ).  %%for doDelivery
event( stop, stop(V) ).
event( resume, resume(V) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8003").
context(ctxcargorobot, "localhost",  "TCP", "8005").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( holdmanager, ctxcargoservice, "it.unibo.holdmanager.Holdmanager").
 static(holdmanager).
  qactor( cargorobot, ctxcargorobot, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
