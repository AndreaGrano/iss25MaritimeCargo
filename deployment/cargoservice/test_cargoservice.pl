%====================================================================================
% test_cargoservice description   
%====================================================================================
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
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
request( doDelivery, doDelivery(Slot) ).
reply( deliveryDone, deliveryDone(V) ).  %%for doDelivery
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
 qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( holdmanager, ctxcargoservice, "it.unibo.holdmanager.Holdmanager").
 static(holdmanager).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( test, ctxcargoservice, "it.unibo.test.Test").
 static(test).
