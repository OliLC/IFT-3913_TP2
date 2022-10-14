#!/bin/bash
for file in jfreechart
do
if [[($file == *.class]]
then
	echo $file
	git log --oneline --$file --wc -l
fi