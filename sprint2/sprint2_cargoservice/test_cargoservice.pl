%====================================================================================
% test_cargoservice description   
%====================================================================================
request( getProduct, getProduct(PID) ).
reply( productName, productName(Name) ).  %%for getProduct
request( getLoadedweight, getLoadedweight(V) ).
reply( loadedweight, loadweight(weight) ).  %%for getLoadedweight
dispatch( updateLoadedweight, updateLoadedweight(Weight) ).
request( getFreeSlot, getFreeSlot(V) ).
reply( freeSlot, freeSlot(Slot) ).  %%for getFreeSlot
dispatch( updateHold, updateHold(Slot,PID) ).
request( loadProduct, loadProduct(PID,Weight) ).
reply( loadAccepted, loadAccepted(V) ).  %%for loadProduct
reply( loadRejected, loadRejected(Reason) ).  %%for loadProduct
dispatch( goToWait, goToWait(V) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
 qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( holdmanager, ctxcargoservice, "it.unibo.holdmanager.Holdmanager").
 static(holdmanager).
