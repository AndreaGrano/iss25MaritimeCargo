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
with Diagram('iss25maritimecargo_cargoserviceguiArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcargoservicegui', graph_attr=nodeattr):
          qakholdwsserver=Custom('qakholdwsserver','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='stop', **evattr, decorate='true', fontcolor='darkgreen') >> qakholdwsserver
     sys >> Edge( label='holdChanged', **evattr, decorate='true', fontcolor='darkgreen') >> qakholdwsserver
     sys >> Edge( label='resume', **evattr, decorate='true', fontcolor='darkgreen') >> qakholdwsserver
diag
