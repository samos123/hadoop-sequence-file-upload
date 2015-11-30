#!/bin/bash


java -cp target/UploadToSequenceFile-1.2-jar-with-dependencies.jar sam.UploadToSequenceFile "$1" "$2" /etc/hadoop/conf/core-site.xml
