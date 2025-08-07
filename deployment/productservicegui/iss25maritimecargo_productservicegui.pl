%====================================================================================
% iss25maritimecargo_productservicegui description   
%====================================================================================
%====================================================================================
context(ctxproductws, "localhost",  "TCP", "8012").
 qactor( qakproductwsserver, ctxproductws, "it.unibo.qakproductwsserver.Qakproductwsserver").
 static(qakproductwsserver).
