import sys

G = {}

def make_link(node1, node2):
    if node1 not in G:
        G[node1] = {}
    (G[node1])[node2] = 1
    if node2 not in G:
        G[node2] = {}
    (G[node2])[node1] = 1

def bfs(root):
    marked = {}
    marked[root] = True
    level = {}
    level[root] = 0
    nodelist = [root]
    aux = 0
    tmp = 0
    tmp2 = 0
    while nodelist != []:
        u = nodelist[0]
        del nodelist[0]
        for neighbor in G[u]:
            if neighbor not in marked:
                marked[neighbor] = True
                aux = level[u] + 1
                level[neighbor] = aux
                if aux > tmp:
                    tmp = aux
                    tmp2 = neighbor
                nodelist.append(neighbor)
    return (tmp2, level[tmp2])

T = (int)(sys.stdin.readline())
while T > 1:
    T -= 1
    u, v = map(int, sys.stdin.readline().split())
    make_link(u, v)

node = bfs(1)[0]
print(bfs(node)[1])
