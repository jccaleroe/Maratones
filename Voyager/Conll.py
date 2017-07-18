from conllu.parser import parse, parse_tree

data = """
1	hola	_	ADJ	_	Number=Sing|fPOS=ADJ++	2	amod	_	_
2	mundo	_	NOUN	_	Gender=Masc|Number=Sing|fPOS=NOUN++	0	ROOT	_	_
3	java	_	ADJ	_	Number=Sing|fPOS=ADJ++	2	amod	_	_
4	.	_	PUNCT	_	fPOS=PUNCT++	2	punct	_	_

"""
import re

data = re.sub(r" +", r"\t", data)

print(parse(data))