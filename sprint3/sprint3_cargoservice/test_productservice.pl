%====================================================================================
% test_productservice description   
%====================================================================================
request( createProduct, createProduct(Name) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(Name) ).  %%for deleteProduct
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
 qactor( productservice, ctxcargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
