%====================================================================================
% maritimecargousagemock description   
%====================================================================================
request( loadProduct, loadProduct(PID) ).
reply( loadAccepted, loadAccepted(SLOT,PID,WEIGHT) ).  %%for loadProduct
reply( loadRejected, loadRejected(Reason) ).  %%for loadProduct
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
event( stop, stop(V) ).
event( resume, resume(V) ).
%====================================================================================
context(ctxusagemock, "localhost",  "TCP", "8120").
context(ctxcargoservice, "127.0.0.1",  "TCP", "8003").
context(ctxioport, "192.168.0.12",  "TCP", "8007").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( usagemock, ctxusagemock, "it.unibo.usagemock.Usagemock").
 static(usagemock).
