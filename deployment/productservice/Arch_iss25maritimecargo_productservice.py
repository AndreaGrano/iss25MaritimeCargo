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
with Diagram('iss25maritimecargo_productserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxproductservice', graph_attr=nodeattr):
          dbwrapper=Custom('dbwrapper','./qakicons/symActorWithobjSmall.png')
          productservice=Custom('productservice','./qakicons/symActorWithobjSmall.png')
     productservice >> Edge(color='magenta', style='solid', decorate='true', label='<createProduct<font color="darkgreen"> createdProduct</font> &nbsp; deleteProduct<font color="darkgreen"> deletedProduct</font> &nbsp; >',  fontcolor='magenta') >> dbwrapper
diag
