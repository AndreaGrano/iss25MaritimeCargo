%====================================================================================
% iss25maritimecargo description   
%====================================================================================
request( createProduct, createProduct(String) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(String) ).  %%for deleteProduct
request( getProduct, getProduct(PID) ).
reply( getProductAnswer, product(String) ).  %%for getProduct
request( loadProduct, loadProduct(String) ).
reply( loadAccepted, loadAccepted(V) ).  %%for loadProduct
reply( loadRefused, loadRefused(V) ).  %%for loadProduct
event( alarm, alarm(V) ).
dispatch( stop, stop(V) ).
dispatch( resume, resume(V) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
context(ctxioport, "localhost",  "TCP", "8002").
context(ctxcargorobot, "localhost",  "TCP", "8003").
 qactor( productservice, ctxcargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( holdmanager, ctxcargoservice, "it.unibo.holdmanager.Holdmanager").
 static(holdmanager).
  qactor( holdstatusgui, ctxcargoservice, "it.unibo.holdstatusgui.Holdstatusgui").
 static(holdstatusgui).
  qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
  qactor( alarmdevice, ctxioport, "it.unibo.alarmdevice.Alarmdevice").
 static(alarmdevice).
  qactor( cargorobot, ctxcargorobot, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
