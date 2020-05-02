package com.site.blog.domain;


import com.site.blog.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Scope("prototype")
public class CompileExecutor implements Runnable{
    @Value("${upload.path}")
    private String uploadPath;
    @Value("${home.path}")
    private String path;
    @Autowired
    private PostRepo postRepo;

    private String compiler;
    private String input;
    private Post post;
    private String folder;

    public CompileExecutor(){

    }

    public CompileExecutor(Post post, String compiler, String input) {
        this.compiler = compiler;
        this.input = input;
        this.post = post;
        this.folder = "temp/" + Integer.toString(0 + (int) (Math.random() * 10)) + "/";
    }

    @Override
    public void run() {
        createDirectory();

        String permissionCommand = "sudo chmod 777 -R " + path + folder;
        String execCommand = "sudo bash " + path + "dockerFiles/DockerTimeout.sh 20s -u mysql -e \\'NODE_PATH=/usr/local/lib/node_modules\\' -i -t -v \""
                + path + folder + "\":/usercode virtual_machine /usercode/script.sh " + getCompilerPart();
        String [] commands={permissionCommand, execCommand};

        try {
            for(String command : commands) {
                execute(command);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        String output = getOutput();
        //System.out.println(output);
        Post originalPost = postRepo.findById(this.post.getId()).get();
        originalPost.changeCompileVersion();
        originalPost.setOutput(output);
        postRepo.save(originalPost);

        deleteDirectory();
        /*try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String output = "change it again";
        //System.out.println(output);
        output += "\n Errors: " + "after error text";
        Post originalPost = postRepo.findById(this.post.getId()).get();
        originalPost.changeCompileVersion();
        originalPost.setOutput(output);
        postRepo.save(originalPost);*/
    }

    public static void execute(String command) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("bash", "-c" ,command);
        Process process = builder.inheritIO().start();

        process.waitFor(20, TimeUnit.SECONDS);
        //process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String readline;
        while ((readline = reader.readLine()) != null) {
            System.out.println(readline);
        }
    }

    private String getOutput() {
        File completeFile = new File(path + folder + "completed");
        String output = "";
        if(completeFile.exists()){
                String[] data = readFileByLines(path + folder + "completed").split("\\*-COMPILEBOX::ENDOFOUTPUT-\\*");
                String error = readFileByLines(path + folder + "errors");
                output+= data[0] + " \n time: " + data[1];
                if(!error.isEmpty()){
                    output += "\n Errors: " + error;
                }
        } else {
                String logFile = readFileByLines(path + folder + "logfile.txt");
                String error = readFileByLines(path + folder + "errors");
                output += "Execution timed out. " + logFile;
                if(!error.isEmpty()){
                    output+= "\n Errors: " + error;
                }
        }
        return output;
    }

    private static String readFileByLines(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private String getCompilerPart() {
        String filename = post.getFilename().split("\\.", 2)[1];
        switch (Integer.parseInt(compiler)) {
            case 1: return "python " + filename;
            case 2: return "/bin/bash " + filename;
            case 3: return "ruby " + filename;
            case 4: return "clojure " + filename;
            case 5: return "node " + filename;
            case 6: return "php " + filename;
            case 7: return "\'scala -nc\' " + filename;
            case 8: return "\'go run\' " + filename;
            case 9: return "javac " + filename + " \'./usercode/javaRunner.sh\'";
            case 10: return "\'vbnc -nologo -quiet\' " + filename + " \'mono /usercode/" + filename.split("\\.", 2)[0] + "\'";
            case 11: return "\'g++ -o /usercode/a.out\' " + filename + " /usercode/a.out";
            case 12: return "mcs " + filename + " \'mono /usercode/" + filename.split("\\.", 2)[0] + "\'";
            case 13: return "perl " + filename;
            case 14: return "\'env HOME=/opt/rust /opt/rust/.cargo/bin/rustc\' " + filename + " \'-o /usercode/a.out\'";
            default:
                break;
        }
        return null;
    }

    private void createDirectory(){
        File newDir = new File( path + folder);
        File src = new File(path + "forCompile/");
        newDir.mkdirs();

        try {
            FileSystemUtils.copyRecursively(src, newDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

            File inputFile = new File(path + folder + "inputFile");

        try {
            inputFile.createNewFile();
            if (input != null && !input.isEmpty()) {
                FileWriter myWriter = new FileWriter(inputFile);
                myWriter.write(input);
                myWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File uploadFile = new File(uploadPath + post.getFilename());
        File newFile = new File(path + folder + post.getFilename().split("\\.", 2)[1]);
         try {
             Files.copy(uploadFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
         } catch (IOException e){
             e.printStackTrace();
         }
    }

    private void deleteDirectory(){
        File newDir = new File( path + "temp/");
        FileSystemUtils.deleteRecursively(newDir);
    }
}
