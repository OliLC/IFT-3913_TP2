Olivier Lemay-Caron 20162110
Derrick Chung 20188514
Repo: https://github.com/OliLC/IFT-3913_TP2/

Pour compiler le code, on a simplement utilisé en ligne de commande
javac *.java

Nous avons utilisé javac version 15.

Pour lancer dcomm:
java -jar dcomm.jar <path relatif du fichier>
exemple:
java -jar dcomm.jar ./jfree/jfreechart/tree/master/src

Pour ck:
ck-0.7.1-SNAPSHOT-jar-with-dependencies.jar
java -jar ck-x.x.x-SNAPSHOT-jar-with-dependencies.jar <project dir> <use jars:true|false> <max files per partition, 0=automatic selection> <variables and fields metrics? True|False> <output dir> [ignored directories...]

Lire le readme sur https://github.com/mauricioaniche/ck

Pour lancer LCSEC:
java lcsec <path relatif du fichier>
exemple:
java lcsec ./jfree/jfreechart/tree/master/src

Pour le script NCH.sh, suffit de le lancer a partir du fichier git qu'on veut analyser
