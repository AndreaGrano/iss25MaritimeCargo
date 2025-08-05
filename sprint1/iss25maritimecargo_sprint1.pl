%====================================================================================
% iss25maritimecargo_sprint1 description   
%====================================================================================
request( createProduct, createProduct(Name) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(Name) ).  %%for deleteProduct
request( getProduct, getProduct(PID) ).
reply( getProductAnswer, product(Name) ).  %%for getProduct
request( loadProduct, loadProduct(PID,Weigth) ).
reply( loadAccepted, loadAccepted(V) ).  %%for loadProduct
reply( loadRejected, loadRejected(Reason) ).  %%for loadProduct
dispatch( goToWait, goToWait(V) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
context(ctxcargorobot, "localhost",  "TCP", "8003").
context(ctxclient, "localhost",  "TCP", "8005").
 qactor( basicrobot, ctxcargorobot, "external").
  qactor( planner, ctxcargorobot, "external").
  qactor( productservice, ctxcargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctxcargorobot, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( client, ctxclient, "it.unibo.client.Client").
 static(client).
