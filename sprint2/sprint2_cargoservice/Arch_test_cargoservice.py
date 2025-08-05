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
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          dbwrapper=Custom('dbwrapper','./qakicons/symActorWithobjSmall.png')
          holdmanager=Custom('holdmanager','./qakicons/symActorWithobjSmall.png')
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getFreeSlot<font color="darkgreen"> freeSlot</font> &nbsp; >',  fontcolor='magenta') >> holdmanager
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getLoadedweight<font color="darkgreen"> loadedweight</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<updateHold &nbsp; >',  fontcolor='blue') >> holdmanager
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<updateLoadedweight &nbsp; >',  fontcolor='blue') >> dbwrapper
diag
