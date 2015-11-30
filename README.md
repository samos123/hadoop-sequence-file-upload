# UploadToSequenceFile - Upload local folder or file to SequenceFile

Simple java executable that can be run to upload a folder of files
to any Hadoop compatible storage such as HDFS, Swift and others.

## Usage

First build the executable JAR with dependencies included:

    mvn clean compile assembly:single

Now you can upload a folder of images from your local folder with:

    # Upload local folder /documents/folder/images to swift as sequencefile
    java -cp target/UploadToSequenceFile-1.2-jar-with-dependencies.jar sam.UploadToSequenceFile \
            /documents/folder/images \
            swift://spark.swift1/321-images.hseq  \
            /etc/hadoop/conf/core-site.xml

    # Upload local folder /documents/folder/images to HDFS as sequencefile
    java -cp target/UploadToSequenceFile-1.2-jar-with-dependencies.jar sam.UploadToSequenceFile \
            /documents/folder/images \
            hdfs://namenode:9000/user/spark/321-images.hseq  \
            /etc/hadoop/conf/core-site.xml

## Warnings

Code has not been tested and is considered experimental. I have confirmed
it to be working by manual testing with Swift and HDFS.
