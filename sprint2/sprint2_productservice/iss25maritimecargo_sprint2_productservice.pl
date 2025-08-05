%====================================================================================
% iss25maritimecargo_sprint2_productservice description   
%====================================================================================
request( createProduct, createProduct(Name,Weight) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(Name,Weight) ).  %%for deleteProduct
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8001").
 qactor( dbwrapper, ctxcargoservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( productservice, ctxcargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
