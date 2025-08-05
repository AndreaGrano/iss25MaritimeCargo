%====================================================================================
% test_load description   
%====================================================================================
request( createProduct, createProduct(Name) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(Name) ).  %%for deleteProduct
request( getProduct, getProduct(PID) ).
reply( getProductAnswer, product(Name) ).  %%for getProduct
request( loadProduct, loadProduct(PID,Weigth) ).
reply( loadAccepted, loadAccepted(V) ).  %%for loadProduct
reply( loadRefused, loadRefused(Reason) ).  %%for loadProduct
dispatch( goToWait, goToWait(V) ).
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
context(ctxtestcargo, "localhost",  "TCP", "8004").
 qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
