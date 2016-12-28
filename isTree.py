import sys

nodes = {}
G = {}

def make_link(node1, node2):
    nodes[node1] = True
    nodes[node2] = True
    if node1 not in G:
        G[node1] = {}
    (G[node1])[node2] = 'red'
    if node2 not in G:
        G[node2] = {}
    (G[node2])[node1] = 'red'
    return G

def create_rooted_spanning_tree(root):
    nodelist = [root]
    marked = {}
    marked[root] = True
    del nodes[root]
    while nodelist != []:
        u = nodelist[0]
        del nodelist[0]
        for neighbor in G[u]:
            if neighbor not in marked:
                marked[neighbor] = True
                del nodes[neighbor]
                nodelist.append(neighbor)
                (G[u])[neighbor] = 'green'
                (G[neighbor])[u] = 'green'
            elif (G[u])[neighbor] == 'red':
                return False
    if len(nodes) != 0:
        return False
    return True

N,M = map(int, sys.stdin.readline().strip().split())

while M > 0:
    M -= 1
    a,b = map(int, sys.stdin.readline().strip().split())
    make_link(a, b)

if create_rooted_spanning_tree(1):
    print("YES")
else:
    print("NO")
