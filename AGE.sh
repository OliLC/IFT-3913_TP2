#!/bin/bash

for file in jfreechart
do
if [[($file == *.class]]
then
	echo $file
	git log --follow --format=%ad --date relative $file | tail -1
fi