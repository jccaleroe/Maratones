if [ $1 -gt $2 ]
then
	exit 0
fi

s="if [ \$1 -gt $2 ]\n
then\n
	exit 0\n
fi\n
for i in {1..10}\n do \n \t echo \"Welcom \$i times\"\n done
\n
echo -e \$2 > \$((\$1+1))\n 
bash \$((\$1+1)) \$((\$1+1)) \"\$2\"\n
"
echo -e $s > $(($1+1)) 
bash $(($1+1)) $(($1+1)) "$s"
