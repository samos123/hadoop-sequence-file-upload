package sam;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.google.common.io.Files;


public class UploadToSequenceFile {

    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> files = new ArrayList<File>();
        
        File file = new File(path);
        if (file.isDirectory()) {
            files.addAll(Arrays.asList(file.listFiles()));
        } else if (file.isFile()) {
            files.add(file);
        }

        return files;
    }

    public static void convertToSequenceFile(String path, String sequenceFilePath, Configuration conf) throws IOException {
        ArrayList<File> files = UploadToSequenceFile.getFiles(path);


        SequenceFile.Writer writer = null;
        try{

            Path seqFilePath = new Path(sequenceFilePath);
            writer = SequenceFile.createWriter(conf, SequenceFile.Writer.file(seqFilePath), 
                        SequenceFile.Writer.keyClass(Text.class),
                        SequenceFile.Writer.valueClass(BytesWritable.class));
            for (File file : files) {
                byte buffer[] = Files.toByteArray(file);
                writer.append(new Text(file.getName()), new BytesWritable(buffer));
            }
            writer.close();
            
        }catch (Exception e) {
            System.out.println("Exception MESSAGES = "+e.getMessage());
        } finally {
            IOUtils.closeStream(writer);
        }
        
    }


    public static void main(String args[]) throws IOException {
        if (args.length != 3) {
            System.err.println(
              "Usage: UploadToSequenceFile <local_folder_or_file> <hdfs_sequence_file_path> <hadoop_conf>");
            System.exit(1);
          }

        String path = args[0];
        String sequenceFilePath = args[1];
        String hadoopConfPath = args[2];

        Configuration conf = new Configuration(true);
        conf.addResource(new Path("file://" + hadoopConfPath));
        UploadToSequenceFile.convertToSequenceFile(path, sequenceFilePath, conf);

    }
}
