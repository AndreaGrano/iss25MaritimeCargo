%====================================================================================
% iss25maritimecargo_sprint3_productservice description   
%====================================================================================
request( createProduct, createProduct(Name,Weight) ).
reply( createdProduct, createdProduct(PID) ).  %%for createProduct
request( deleteProduct, deleteProduct(PID) ).
reply( deletedProduct, deletedProduct(Name,Weight) ).  %%for deleteProduct
%====================================================================================
context(ctxproductservice, "localhost",  "TCP", "8001").
 qactor( dbwrapper, ctxproductservice, "it.unibo.dbwrapper.Dbwrapper").
 static(dbwrapper).
  qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
