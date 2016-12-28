def subsets(l,inv):
    if inv == []:
        if len(l) == 3:
            print(l)
            return
    else:
        aux = inv[0]
        del inv[0]
        subsets(l+[aux], list(inv))
        subsets(l, list(inv))
subsets([],["1","2", "3", "4"])
