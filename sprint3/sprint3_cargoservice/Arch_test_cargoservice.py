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
with Diagram('test_cargoserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcargoservice', graph_attr=nodeattr):
          dbwrapper=Custom('dbwrapper','./qakicons/symActorWithobjSmall.png')
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          holdmanager=Custom('holdmanager','./qakicons/symActorWithobjSmall.png')
          cargorobot=Custom('cargorobot','./qakicons/symActorWithobjSmall.png')
          test=Custom('test','./qakicons/symActorWithobjSmall.png')
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getFreeSlot<font color="darkgreen"> freeSlot</font> &nbsp; getLoadedWeight<font color="darkgreen"> loadedWeight</font> &nbsp; >',  fontcolor='magenta') >> holdmanager
     test >> Edge(color='magenta', style='solid', decorate='true', label='<loadProduct<font color="darkgreen"> loadAccepted loadRejected</font> &nbsp; containerWaiting<font color="darkgreen"> containerLoaded</font> &nbsp; >',  fontcolor='magenta') >> cargoservice
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getProductWeight<font color="darkgreen"> productWeight</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
     holdmanager >> Edge(color='magenta', style='solid', decorate='true', label='<getLoadedWeight<font color="darkgreen"> loadedWeight</font> &nbsp; getFreeSlot<font color="darkgreen"> freeSlot</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<doDelivery<font color="darkgreen"> deliveryDone</font> &nbsp; >',  fontcolor='magenta') >> cargorobot
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<updateHold &nbsp; >',  fontcolor='blue') >> holdmanager
     holdmanager >> Edge(color='blue', style='solid',  decorate='true', label='<updateHold &nbsp; >',  fontcolor='blue') >> dbwrapper
diag
