%====================================================================================
% iss25maritimecargo_sprint2_ioport description   
%====================================================================================
request( containerWaiting, containerWaiting(V) ).
reply( containerLoaded, containerLoaded(V) ).  %%for containerWaiting
event( distance, distance(D) ).
event( stop, stop(V) ).
event( resume, resume(V) ).
%====================================================================================
context(ctxioport, "localhost",  "TCP", "8007").
 qactor( sonarwrapper, ctxioport, "it.unibo.sonarwrapper.Sonarwrapper").
 static(sonarwrapper).
