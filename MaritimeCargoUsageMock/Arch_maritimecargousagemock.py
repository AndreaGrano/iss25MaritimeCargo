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
with Diagram('maritimecargousagemockArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxusagemock', graph_attr=nodeattr):
          usagemock=Custom('usagemock','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxcargoservice', graph_attr=nodeattr):
          cargoservice=Custom('cargoservice(ext)','./qakicons/externalQActor.png')
     usagemock >> Edge( label='stop', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     usagemock >> Edge( label='resume', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     usagemock >> Edge(color='magenta', style='solid', decorate='true', label='<loadProduct<font color="darkgreen"> loadAccepted loadRejected</font> &nbsp; containerWaiting<font color="darkgreen"> containerLoaded</font> &nbsp; >',  fontcolor='magenta') >> cargoservice
diag
