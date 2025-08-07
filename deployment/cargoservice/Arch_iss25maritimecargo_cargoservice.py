### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('iss25maritimecargo_cargoserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcargoservice', graph_attr=nodeattr):
          dbwrapper=Custom('dbwrapper','./qakicons/symActorWithobjSmall.png')
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          holdmanager=Custom('holdmanager','./qakicons/symActorWithobjSmall.png')
          cargorobot=Custom('cargorobot','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     dbwrapper >> Edge( label='holdChanged', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='stop', **evattr, decorate='true', fontcolor='darkgreen') >> cargoservice
     sys >> Edge( label='resume', **evattr, decorate='true', fontcolor='darkgreen') >> cargoservice
     sys >> Edge( label='stop', **evattr, decorate='true', fontcolor='darkgreen') >> cargorobot
     sys >> Edge( label='resume', **evattr, decorate='true', fontcolor='darkgreen') >> cargorobot
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getFreeSlot<font color="darkgreen"> freeSlot</font> &nbsp; getLoadedWeight<font color="darkgreen"> loadedWeight</font> &nbsp; >',  fontcolor='magenta') >> holdmanager
     cargorobot >> Edge(color='magenta', style='solid', decorate='true', label='<engage<font color="darkgreen"> engagedone engagerefused</font> &nbsp; step<font color="darkgreen"> stepdone stepfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getProductWeight<font color="darkgreen"> productWeight</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
     holdmanager >> Edge(color='magenta', style='solid', decorate='true', label='<getLoadedWeight<font color="darkgreen"> loadedWeight</font> &nbsp; getFreeSlot<font color="darkgreen"> freeSlot</font> &nbsp; getHoldStatus<font color="darkgreen"> holdStatus</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<doDelivery<font color="darkgreen"> deliveryDone</font> &nbsp; >',  fontcolor='magenta') >> cargorobot
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<updateHold &nbsp; >',  fontcolor='blue') >> holdmanager
     cargorobot >> Edge(color='blue', style='solid',  decorate='true', label='<cmd &nbsp; >',  fontcolor='blue') >> basicrobot
     holdmanager >> Edge(color='blue', style='solid',  decorate='true', label='<updateHold &nbsp; >',  fontcolor='blue') >> dbwrapper
diag
