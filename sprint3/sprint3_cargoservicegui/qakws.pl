%====================================================================================
% qakws description   
%====================================================================================
request( loadRequest, loadRequest(PID) ).
reply( loadReply, loadReply(SLOT,PID,WEIGHT) ).  %%for loadRequest
%====================================================================================
context(ctxws, "localhost",  "TCP", "8010").
 qactor( wsprova, ctxws, "it.unibo.wsprova.Wsprova").
 static(wsprova).
